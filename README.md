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
