(ns app-datomic-basic.configs.datomic
  (:require [datomic.api :as d]
            [app-datomic-basic.gateways.datomic.documents.product :as product-document]
            [app-datomic-basic.gateways.datomic.documents.category :as category-document]))


(def db-uri "datomic:sql://?jdbc:postgresql://localhost:5432/my-datomic?user=datomic-user&password=unsafe")

(defn get-db []
  (d/connect db-uri))

(defn apply-schemas []
  (let [db (get-db)]
    @(d/transact db product-document/schema)
    @(d/transact db category-document/schema)))

(defn init-datomic []
  (d/create-database db-uri)
  (apply-schemas))
