(ns app-datomic-basic.domain.category
  (:require [app-datomic-basic.utils.map-utils :as map-utils]
            [schema.core :as s]))

(def Category
  {(s/optional-key :id)   s/Str
   (s/optional-key :name) s/Str})

(defn create-category-all-args
  ([id]
   (create-category-all-args id nil))
  ([id name]
   (let [category {}]
     (as-> category $
           (map-utils/add-if-not-nil $ :id id)
           (map-utils/add-if-not-nil $ :name name)))))

(defn get-id [category]
  (map-utils/get-when category :id))

(defn get-name [category]
  (map-utils/get-when category :name))
