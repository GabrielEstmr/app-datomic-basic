(ns app-datomic-basic.domain.product
  (:require [app-datomic-basic.utils.map-utils :as map-utils]))

(defn create-product-all-args [id name slug price keywords category]
  {:id       id
   :name     name
   :slug     slug
   :price    price
   :keywords keywords
   :category category})

(defn get-id [product]
  (map-utils/get-when product :id))

(defn get-name [product]
  (map-utils/get-when product :name))

(defn get-slug [product]
  (map-utils/get-when product :slug))

(defn get-price [product]
  (map-utils/get-when product :price))

(defn get-keywords [product]
  (map-utils/get-when product :keywords))

(defn get-category [product]
  (map-utils/get-when product :category))
