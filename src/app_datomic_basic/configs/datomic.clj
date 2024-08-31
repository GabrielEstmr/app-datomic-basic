(ns app-datomic-basic.configs.datomic
  (:require [datomic.api :as d]
            [app-datomic-basic.gateways.datomic.documents.product :as product-document]))


(def db-uri "datomic:sql://?jdbc:postgresql://localhost:5432/my-datomic?user=datomic-user&password=unsafe")

(defn get-db []
  (d/connect db-uri))

(defn apply-schemas []
  @(d/transact (get-db) [{:db/ident       :product/name
                                  :db/valueType   :db.type/string
                                  :db/cardinality :db.cardinality/one
                                  :db/doc         "The name of the product"}

                                 {:db/ident       :product/slug
                                  :db/valueType   :db.type/string
                                  :db/cardinality :db.cardinality/one
                                  :db/doc         "A description of the product"}

                                 {:db/ident       :product/price
                                  :db/valueType   :db.type/double
                                  :db/cardinality :db.cardinality/one
                                  :db/doc         "The price of the product"}]))

(defn init-datomic []
  (d/create-database db-uri)
  (apply-schemas))
