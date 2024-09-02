(ns app-datomic-basic.gateways.datomic.category-database-gateway
  (:require [app-datomic-basic.gateways.category-database-gateway :as category-database-gateway]
            [app-datomic-basic.gateways.datomic.repository.category-repository :as category-repository]
            [app-datomic-basic.gateways.datomic.documents.category :as category-document]
            [app-datomic-basic.utils.uuid-utils :as uuid-utils]))

(defn save-impl [category]
  (let [category-doc       (category-document/create-category-document category)
        saved-category-doc (category-repository/save category-doc)]
    (when saved-category-doc
      (category-document/to-domain saved-category-doc))))

(defn find-product-by-id-impl [id]
  (let [product-document (category-repository/find-by-category-id (uuid-utils/string-to-uuid id))]
    (when product-document
      (category-document/to-domain product-document))))

(defrecord CategoryDatabaseGatewayImpl []
  category-database-gateway/CategoryDatabaseGateway
  (save [_ category]
    (save-impl category))
  (findById [_ id]
    (find-product-by-id-impl id)))
