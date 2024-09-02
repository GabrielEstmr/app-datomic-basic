(ns app-datomic-basic.domain.category
  (:require [app-datomic-basic.utils.map-utils :as map-utils]))

(defn create-category-all-args [id name]
  {:id       id
   :name     name})

(defn get-id [category]
  (map-utils/get-when category :id))

(defn get-name [category]
  (map-utils/get-when category :name))
