(ns app-datomic-basic.gateways.ws.resources.product-response
  (:require
   [app-datomic-basic.domain.product :as product]
   [app-datomic-basic.utils.map-utils :as map-utils]))

(defn create-product-response-all-args [id name slug price keywords]
  (let [product-resource-base {}]
    (-> product-resource-base
        (map-utils/add-if-not-nil :id id)
        (map-utils/add-if-not-nil :name name)
        (map-utils/add-if-not-nil :slug slug)
        (map-utils/add-if-not-nil :price price)
        (map-utils/add-if-not-nil :keywords keywords))))

(defn create-product-response [product]
  (create-product-response-all-args
   (product/get-id product)
   (product/get-name product)
   (product/get-slug product)
   (product/get-price product)
   (product/get-keywords product)))
