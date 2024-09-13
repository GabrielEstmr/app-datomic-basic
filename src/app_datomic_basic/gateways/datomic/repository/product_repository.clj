(ns app-datomic-basic.gateways.datomic.repository.product-repository
  (:require
   [datomic.api :as d]
   [app-datomic-basic.configs.datomic :as datomic-config]
   [app-datomic-basic.utils.uuid-utils :as uuid-utils]))

(defn save [product-document]
  (let [product-document-id (uuid-utils/assoc-uuid product-document :product/id)]
    @(d/transact (datomic-config/get-db) [product-document-id])
    product-document-id))

(defn find-by-product-id [id]
  (let [response (d/q '[:find (pull ?product [* {:product/category [*]}])
                        :in $ ?id
                        :where [?product :product/id ?id]] (d/db (datomic-config/get-db)) id)]
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
