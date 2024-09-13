(ns app-datomic-basic.gateways.ws.resources.user-response
  (:require
   [app-datomic-basic.domain.user :as user]
   [app-datomic-basic.utils.map-utils :as map-utils]))

(defn create-user-response-all-args [id name]
  (let [user-resource-base {}]
    (-> user-resource-base
        (map-utils/add-if-not-nil :id id)
        (map-utils/add-if-not-nil :name name))))

(defn create-user-response [user]
  (create-user-response-all-args
   (user/get-id user)
   (user/get-name user)))
