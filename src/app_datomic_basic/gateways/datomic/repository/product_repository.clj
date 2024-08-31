(ns app-datomic-basic.gateways.datomic.repository.product-repository
  (:require
   [datomic.api :as d]
   [app-datomic-basic.configs.datomic :as datomic-config]
   [app-datomic-basic.gateways.datomic.documents.product :as product-document]))


;; IMPORTANT: Its possible to add only one datom (e. g.: only :product/name)
;(defn save-product-only-name [product-document]
;  (let [result @(d/transact datomic-config/conn
;                            [{:db/id         #db/id[:db.part/user]
;                              :product/name  (product-document/get-name product-document)}])]
;    result))
;
;; In this case: error: if a datom is not part of an entity, its only not add it in the save operation
;(defn save-product-only-name-error [product-document]
;  (let [result @(d/transact datomic-config/conn
;                            [{:db/id         #db/id[:db.part/user]
;                              :product/name  (product-document/get-name product-document)
;                              :product/slug  nil}])]


; IMPORTANT: d/transact is an async operation in clojure
; WITH @: SYNC
; WITHOUT @: ASYNC (like all futures in clojure)

(defn save [product-document]
  (let [temp-id #db/id[:db.part/user]
        result @(d/transact datomic-config/conn
                            [{:db/id         temp-id
                              :product/name  (product-document/get-name product-document)
                              :product/slug  (product-document/get-slug product-document)
                              :product/price (product-document/get-price product-document)}])
        resolved-id (d/resolve-tempid (d/db datomic-config/conn) (:tempids result) temp-id) ; considering partition, etc
        ;temp-ids-v1 (val (first (:tempids result)))
        ]

    (println "Transaction Result:" result)
    (println "Entity ID:" resolved-id)
    (println "Tempids Map:" (:tempids result))
    resolved-id))

(defn find-product-by-name [name]
  (d/q '[:find ?name ?slug ?price
         :in $ ?name
         :where
         [?e :product/name ?name]
         [?e :product/slug ?slug]
         [?e :product/price ?price]]
       (d/db datomic-config/conn) name))
