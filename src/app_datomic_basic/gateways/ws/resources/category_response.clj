(ns app-datomic-basic.gateways.ws.resources.category-response
  (:require
   [app-datomic-basic.domain.category :as category]
   [app-datomic-basic.utils.map-utils :as map-utils]))

(defn create-category-response-all-args [id name]
  (let [category-resource-base {}]
    (-> category-resource-base
        (map-utils/add-if-not-nil :id id)
        (map-utils/add-if-not-nil :name name))))

(defn create-category-response [category]
  (create-category-response-all-args
   (category/get-id category)
   (category/get-name category)))
