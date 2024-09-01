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

; Reference by key
(defn find-by-id [id]
  (as-> id $
        (d/q '[:find (pull ?e [:product/name :product/slug :product/price])
               :in $ ?id
               :where
               [?e :product/name]
               [(= ?e ?id)]]
             (d/db (datomic-config/get-db)) $)
        (first $)
        (first $)
        (assoc $ :product/id id)))

; Updates: we must use db/add
(defn update [product-document]
  (let [id (get product-document :product/id)]
    @(d/transact
      (datomic-config/get-db)
      [[:db/add id :product/name (documents.product/get-name product-document)]
       [:db/add id :product/slug (documents.product/get-slug product-document)]
       [:db/add id :product/price (documents.product/get-price product-document)]
       ])
    product-document))



;(defn query-with-pagination
;  [db {:keys [offset limit] :or {offset 0 limit 10}}]
;  (let [query '[:find ?e ?name ?price
;                :in $ ?offset ?limit
;                :where
;                [?e :product/name ?name]
;                [?e :product/price ?price]]
;        result (->> (d/q query db offset limit)
;                    (drop offset)
;                    (take limit))]
;    result))
