(ns app-datomic-basic.gateways.datomic.product-database-gateway
  (:require [app-datomic-basic.gateways.product-database-gateway :as product-database-gateway]
            [app-datomic-basic.gateways.datomic.repository.product-repository :as product-repository]
            [app-datomic-basic.gateways.datomic.documents.product :as product-document]))

(defn save-impl [product]
  (let [product-doc       (product-document/create-product-document product)
        saved-product-doc (product-repository/save product-doc)]
    (when saved-product-doc
      (product-document/to-domain saved-product-doc))))

(defn update-impl [product]
  (let [product-doc       (product-document/create-product-document product)
        updated-product-doc (product-repository/update product-doc)]
    (when updated-product-doc
      (product-document/to-domain updated-product-doc))))

(defn find-product-by-name-impl [name]
  (let [product-document (product-repository/find-product-by-name name)]
    (when product-document
      (product-document/to-domain product-document))))

(defn find-product-by-id-impl [id]
  (let [product-document (product-repository/find-by-id id)]
    (when product-document
      (product-document/to-domain product-document))))

(defrecord ProductDatabaseGatewayImpl []
  product-database-gateway/ProductDatabaseGateway
  (save [_ account]
    (save-impl account))
  (update [_ account]
    (update-impl account))
  (findByName [_ name]
    (find-product-by-name-impl name))
  (findById [_ id]
    (find-product-by-id-impl id)))
