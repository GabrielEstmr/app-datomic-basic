(ns app-datomic-basic.domain.product
  (:require [app-datomic-basic.utils.map-utils :as map-utils]))

(defn create-product-all-args [id name slug price]
  {:id    id
   :name  name
   :slug  slug
   :price price})

(defn get-id [product]
  (map-utils/get-when product :id))
