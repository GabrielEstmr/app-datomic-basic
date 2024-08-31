(ns app-datomic-basic.gateways.datomic.repository.product-repository
  (:require
   [datomic.api :as d]
   [app-datomic-basic.configs.datomic :as datomic-config]
   [app-datomic-basic.gateways.datomic.documents.product :as documents.product]))


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

; getting IDS:
; considering partition, etc. other way: not considering partition ;temp-ids-v1 (val (first (:tempids result)))


(defn save [product-document]
  (let [temp-id             #db/id[:db.part/user]
        product-document-id (assoc product-document :db/id temp-id)
        result              @(d/transact (datomic-config/get-db) [product-document-id])
        resolved-id         (d/resolve-tempid (d/db (datomic-config/get-db)) (:tempids result) temp-id)]
    (assoc product-document :product/id resolved-id)))

(defn find-product-by-name [name]
  (d/q '[:find ?name ?slug ?price
         :in $ ?name
         :where
         [?e :product/name ?name]
         [?e :product/slug ?slug]
         [?e :product/price ?price]]
       (d/db (datomic-config/get-db)) name))

(defn find-by-id [id]
  (d/q '[:find ?name ?slug ?price
         :in $ ?id
         :where
         [?e :product/name ?name]
         [?e :product/slug ?slug]
         [?e :product/price ?price]
         [(= ?e ?id)]]
       (d/db (datomic-config/get-db)) id))

;(defn find-by-id [id]
;  (let [db (d/db (datomic-config/get-db))
;        datoms (d/datoms db :eavt id)]
;    datoms))

(defn update [product-document]
  (let [temp-id             #db/id[:db.part/user]
        product-document-id (assoc product-document :db/id (documents.product/get-id product-document))
        result              @(d/transact (datomic-config/get-db) [product-document-id])
        resolved-id         (d/resolve-tempid (d/db (datomic-config/get-db)) (:tempids result) temp-id)]
    (assoc product-document :product/id resolved-id)))

(defn update-product-name [product-id product-name]
  (let [result @(d/transact (datomic-config/get-db) [ [:db/add product-id :product/name product-name]])]
    product-name))


;(defn update [product-id updated-data]
;  (let [product-document (assoc updated-data :db/id product-id)
;        result           @(d/transact (datomic-config/get-db) [product-document])]
;    result))
