(ns app-datomic-basic.domain.user
  (:require [app-datomic-basic.utils.map-utils :as map-utils]
            [schema.core :as s]))

(def User
  {(s/optional-key :id)   s/Str
   (s/optional-key :name) s/Str})

(defn create-user-all-args [id name]
  (let [user {}]
    (-> user
        (map-utils/add-if-not-nil :id id)
        (map-utils/add-if-not-nil :name name))))

(defn create-new-user
  ([id]
   (create-user-all-args id nil))
  ([id name]
   (create-user-all-args id name)))

(defn get-id [user]
  (map-utils/get-when user :id))

(defn get-name [user]
  (map-utils/get-when user :name))
