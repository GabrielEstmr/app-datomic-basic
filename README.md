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


(IMPORTANT: NÂO TEM ORDEM)
