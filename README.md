;; IMPORTANT: Its possible to add only one datom (e. g.: only :product/name)
;(defn save-product-only-name [product-document]
;  (let [result @(d/transact datomic-config/conn
;                            [{:db/id #db/id[:db.part/user]
;                              :product/name  (product-document/get-name product-document)}])]
; result))
;
;; In this case: error: if a datom is not part of an entity, its only not add it in the save operation
;(defn save-product-only-name-error [product-document]
;  (let [result @(d/transact datomic-config/conn
;                            [{:db/id #db/id[:db.part/user]
;                              :product/name  (product-document/get-name product-document)
;                              :product/slug nil}])]

; IMPORTANT: d/transact is an async operation in clojure
; WITH @: SYNC
; WITHOUT @: ASYNC (like all futures in clojure)

; getting IDS:
; considering partition, etc. other way: not considering partition ;temp-ids-v1 (val (first (:tempids result)))

## Transactions in Datomic:

Vamos dizer que temos o seguinte código:

```clojure

(let [computador     (model/novo-produto "Computador Novo", "/computador_novo", 2500.10M)
      celular        (model/novo-produto "Celular Caro", "/celular", 888888.10M)
      calculadora    {:produto/nome "Calculadora com 4 operações"}
      celular-barato (model/novo-produto "Celular Barato", "/celular-barato", nil)]
  (d/transact conn [computador celular calculadora celular-barato]))
```

Estamos transacionando para o Datomic 4 produtos ao mesmo tempo.

Note que for o celular-barato está com o preço nulo.

Ao executarmos o código anterior, obtemos o seguinte erro:

Execution error (Exceptions$IllegalArgumentExceptionInfo) at datomic.error/arg (error.clj:79).
:db.error/nil-value Nil is not a legal valueCopiar código
O Datomic não aceita valores nulos. Por isso obtivemos um erro.

OK, mas apenas o último produto, o celular-barato, teve um valor nulo. O que será que aconteceu com os outros produtos?
Foram inseridos ou não?

Considerando que o Datomic não tinha nenhum produto antes dessa transação, podemos verificar os produtos depois da
execução com a consulta:

(pprint (db/todos-os-produtos)(d/db conn))Copiar código
O resultado seria um conjunto vazio:

#{}Copiar código
Ou seja, ao transacionarmos 4 produtos ao mesmo tempo, se ocorrer algum erro no último, a transação toda é cancelada.

Essa característica transacional de fazer "ou tudo ou nada" é chamada de Atomicidade: se uma parte da transação falhar,
a transação toda falha e nenhuma mudança é feita no BD.

(IMPORTANT: NÂO TEM ORDEM no escopo de varias transacoes)

### Find Specs in Datomic

As default, the datomic returns values as tuples as following:

For:
```clojure
(defn find-product-by-name [name]
  (d/q '[:find ?name
         :in $ ?name
         :where
         [?e :product/name ?name]
         [?e :product/slug ?slug]
         [?e :product/price ?price]]
       (d/db (datomic-config/get-db)) name))

[["name"] ["name"] ["name"] ["name"]]
```

Pull: to return as `MAP`
```clojure
(defn find-product-by-name [name]
  (d/q '[:find (pull ?name [*])
         :in $ ?name
         :where
         [?e :product/name ?name]
         [?e :product/slug ?slug]
         [?e :product/price ?price]]
       (d/db (datomic-config/get-db)) name))

[[{:name "name"}] [{:name "name"}] [{:name "name"}] [{:name "name"}]]
```

To return without brakets: (put brakets/inverse)
```clojure
(defn find-product-by-name [name]
  (d/q '[:find [(pull ?name [*])]
         :in $ ?name
         :where
         [?e :product/name ?name]
         [?e :product/slug ?slug]
         [?e :product/price ?price]]
       (d/db (datomic-config/get-db)) name))

[{:name "name"} {:name "name"} {:name "name"} {:name "name"}]
```


### Optimization:

- Order: by where clause
- filter the more restrictive first (to limit data) (there is no action plan under a query as the SQL databases)

```clojure
(defn find-product-by-name [name]
  (d/q '[:find ?name ?slug ?price
         :in $ ?name
         :where
         [?e :product/name ?name]
         [?e :product/slug ?slug]
         [?e :product/price ?price]]
       (d/db (datomic-config/get-db)) name))
```

### Query with pagination

```clojure
(defn query-with-pagination
  [db {:keys [offset limit] :or {offset 0 limit 10}}]
  (let [query  '[:find ?e ?name ?price
                 :in $ ?offset ?limit
                 :where
                 [?e :product/name ?name]
                 [?e :product/price ?price]]
        result (->> (d/q query db offset limit)
                    (drop offset)
                    (take limit))]
    result))
```

### Updates:

Must use db/add by attributes

```clojure
(defn update [product-document]
  (let [id (get product-document :product/id)]
    @(d/transact
      (datomic-config/get-db)
      [[:db/add id :product/name (documents.product/get-name product-document)]
       [:db/add id :product/slug (documents.product/get-slug product-document)]
       [:db/add id :product/price (documents.product/get-price product-document)]
       ])
    product-document))
```

### IMPORTANT: Cardinality:many: add the different (dont remove others)

- to remove: db/retract only

### Pure Pull:

```clojure
(defn find-by-bd-id-v2 [product-id]
  (let [response (d/pull (datomic-config/get-db) '[*] product-id)]
    response))

; for other attributes as `:db/unique      :db.unique/identity`
(defn find-by-product-id [product-id]
  (let [response (d/pull (datomic-config/get-db) '[*] [:product/id product-id])]
    response))
```

### Important:

Using UUID as attribute of an entity, we can use this property to update the datons of a property since a db/transact
with the same UUID lead to retract+ add the values of the others attributes

### Nested Attributes

- Create category atomically with product

```clojure
(defn save [product-document]
  (let [product-document-id (add-product-id product-document)]
    @(d/transact (datomic-config/get-db) [{:product/id       "uuid"
                                           :product/name     "uuid"
                                           :product/slug     "uuid"
                                           :product/price    123
                                           :product/category {:category/id   "uuid"
                                                              :category/name "name"}
                                           }])
    product-document-id))
```

- Create product with reference to already created category
    - Note that is different to add uuid directly to :product/category

```clojure
(defn save [product-document]
  (let [product-document-id (add-product-id product-document)]
    @(d/transact (datomic-config/get-db) [{:product/id       "uuid"
                                           :product/name     "uuid"
                                           :product/slug     "uuid"
                                           :product/price    123
                                           :product/category {:category/id "uuid"}
                                           }])
    product-document-id))
```

### Nested QUERIES:

- Queries under queries to have access in database once
- Atomic find (two finds can lead in inconsistency)

```clojure
(defn todos-os-produtos-mais-caros [db]
  (d/q '[:find (pull ?produto [*])
         :where [(q '[:find (max ?preco)
                      :where [_ :produto/preco ?preco]]
                    $) [[?preco]]]
         [?produto :produto/preco ?preco]]
       db))
```

- Transactions:
    - Multiples maps in d/transact or multiple db/add for the same entity: same transaction
    - We can add attributes in the Transaction Entity

```clojure
(let [computador     (model/novo-produto "Computador Novo", "/computador-novo", 2500.10M)
      celular        (model/novo-produto "Celular Caro", "/celular", 888888.10M)
      calculadora    {:produto/nome "Calculadora com 4 operações"}
      celular-barato (model/novo-produto "Celular Barato", "/celular-barato", 0.1M)]
  (pprint @(d/transact conn [computador, celular, calculadora, celular-barato])))
```

```clojure
(defn build-update-adds [id product-document]
  (let [base-inputs [[:db/add id :product/id (documents.product/get-id product-document)]
                     [:db/add id :product/name (documents.product/get-name product-document)]
                     [:db/add id :product/slug (documents.product/get-slug product-document)]
                     [:db/add id :product/price (documents.product/get-price product-document)]
                     ]
        keywords    (map (fn-add-property-executor id :product/keyword) (documents.product/get-keywords product-document))
        inputs      (vec (concat base-inputs keywords))]
    inputs))
```

Adding attributes in Transaction Entity:

```clojure
(defn todos-os-produtos-do-ip [db ip]
  (d/q '[:find (pull ?produto [*])
         :in $ ?ip-buscado
         :where [?transacao :tx-data/ip ?ip-buscado]
         [?produto :produto/id _ ?transacao]]
       db ip))
```

### Partial Updates:

As in Mongodb: update only UUID + Prop que queremos fazer o update (não é thread safe)

#### Find Specs:

- retorna uma coleção de listas com 2 valores:  `:find ?nome ?preco`
- retorna uma coleção de mapas                  `:find (pull ?produto [*])`
- retorna diversos nomes em uma coleção         `:find [?nome ...]`
- retorna um único nome                         `:find ?nome .`

### Rules

Create rules to use it in multiples queries.

We can create more than one rule with the same name to made a OR logic

- [or-logic](https://cursos.alura.com.br/course/datomic-schemas-regras/task/63357)

- Before Rules:

```clojure
(defn find-product-by-name [name]
  (d/q '[:find ?name ?slug ?price
         :in $ ?name
         :where
         [?e :product/name ?name]
         [?e :product/slug ?slug]
         [?e :product/price ?price]]
       (d/db (datomic-config/get-db)) name))
```

- After rules:

```clojure
(def rules
  '[
    [(filter-product-by-name ?e ?name)
     [?e :product/name ?name]]
    ])

(defn find-product-by-name [name]
  (d/q '[:find ?name ?slug ?price
         :in $ % ?name
         :where
         (filter-product-by-name ?e ?name)                          ;não poe conchetes
         [?e :product/slug ?slug]
         [?e :product/price ?price]]
       (d/db (datomic-config/get-db)) rules name))
```

### Collection Bindings as Inputs:

To be able to filter by multiple values by using `[?property...]` (the same as pull returns)

```clojure
(defn find-product-by-name [name]
  (d/q '[:find ?name ?slug ?price
         :in $ [?name ...] ?other-parameter ; in can come after
         :where
         [?e :product/name ?name]
         [?e :product/slug ?slug]
         [?e :product/price ?price]]
       (d/db (datomic-config/get-db)) name))
```

### Atomic Transactions in Datomic

All that in inside the `d/transact` in atomic in clojure

By Versioning control

- Uses the compare and swap function

```clojure


```
