(ns app-datomic-basic.gateways.datomic.product-database-gateway
  (:require [app-datomic-basic.gateways.product-database-gateway :as product-database-gateway]
            [app-datomic-basic.gateways.datomic.repository.product-repository :as product-repository]
            [app-datomic-basic.gateways.datomic.documents.product :as product-document]))

(defn save-impl [product]
  (let [product-doc       (product-document/create-product-document product)
        saved-account-doc (product-repository/save product-doc)]
    (when saved-account-doc
      (product-document/to-domain saved-account-doc))))

(defn find-product-by-name-impl [id]
  (let [account-document (product-repository/find-product-by-name id)]
    (when account-document
      (product-document/to-domain account-document))))

(defrecord ProductDatabaseGatewayImpl []
  product-database-gateway/ProductDatabaseGateway
  (save [_ account]
    (save-impl account))
  (find-product-by-name [_ id]
    (find-product-by-name-impl id)))
