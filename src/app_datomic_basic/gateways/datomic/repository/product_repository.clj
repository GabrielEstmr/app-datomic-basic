(ns app-datomic-basic.gateways.datomic.repository.product-repository
  (:require
   [datomic.api :as d]
   [app-datomic-basic.configs.datomic :as datomic-config]
   [app-datomic-basic.gateways.datomic.documents.product :as product-document]))


(defn save [product-document]
  (let [result @(d/transact datomic-config/conn
                            [{:db/id         #db/id[:db.part/user]
                              :product/name  (product-document/get-name product-document)
                              :product/slug  (product-document/get-slug product-document)
                              :product/price (product-document/get-price product-document)}])]
    result
    ))

(defn find-product-by-name [name]
  (d/q '[:find ?e
         :in $ ?name
         :where
         [?e :product/name ?name]]
       (d/db datomic-config/conn) name))
