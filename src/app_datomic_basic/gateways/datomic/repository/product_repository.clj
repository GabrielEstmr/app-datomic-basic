(ns app-datomic-basic.gateways.datomic.repository.product-repository
  (:require
   [datomic.api :as d]
   [app-datomic-basic.configs.datomic :as datomic-config]
   [app-datomic-basic.utils.uuid-utils :as uuid-utils]))

(defn save [product-document]
  (let [product-document-id (uuid-utils/assoc-uuid product-document :product/id)]
    @(d/transact (datomic-config/get-db) [product-document-id])
    product-document-id))

; [{},{},{}]
; [{},{},{}]
(defn find-by-product-id [id]
  (let [
        response0 (d/q '[:find [?product ?name]
                         :where
                                [?product :product/name "TestProduct"]
                                [?product :product/name ?name]
                                [?product :product/id ?product]] (d/db (datomic-config/get-db)))

        ; [[{}] [{}] [{}]]
        response (d/q '[:find (pull ?product [* {:product/category [*]}])
                        :in $ ?id
                        :where [?product :product/id ?id]] (d/db (datomic-config/get-db)) id)

        ; [{}]
        response2 (d/q '[:find [(pull ?product [* {:product/category [*]}])]
                         :in $ ?id
                         :where [?product :product/id ?id]] (d/db (datomic-config/get-db)) id)

        ; [{} {} {} {}]
        response3 (d/q '[:find [(pull ?product [* {:product/category [*]}]) ...]
                         :in $ ?id
                         :where [?product :product/name "TestProduct"]] (d/db (datomic-config/get-db)) id)

        ; [123123 "name1" 123123 "name2" 123123 "name3"]
        response6 (d/q '[:find [?product ...]
                         :in $ ?id
                         :where [?product :product/id ?id]] (d/db (datomic-config/get-db)) id)

        ]
    (ffirst response)))

(defn update [product-document]
  @(d/transact (datomic-config/get-db) [product-document])
  product-document)

(defn find-product-by-name [name]
  (d/q '[:find ?name ?slug ?price
         :in $ ?name
         :where
         [?e :product/name ?name]
         [?e :product/slug ?slug]
         [?e :product/price ?price]]
       (d/db (datomic-config/get-db)) name))




(defn find-by-product-id-v2 [id]
  (let [response (d/q '[:find [(pull ?product [* {:product/category [*]}])]
                        :in $ ?id
                        :where [?product :product/id ?id]] (d/db (datomic-config/get-db)) id)]
    (ffirst response)))
