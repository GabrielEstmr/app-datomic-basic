(ns app-datomic-basic.gateways.datomic.documents.user
  (:require
   [app-datomic-basic.utils.map-utils :as map-utils]
   [app-datomic-basic.domain.user :as domain-user]
   [app-datomic-basic.utils.uuid-utils :as uuid-utils]))

(def schema
  [{:db/ident       :user/id
    :db/valueType   :db.type/uuid
    :db/cardinality :db.cardinality/one
    :db/unique      :db.unique/identity
    :db/doc         "The name of the user"}

   {:db/ident       :user/name
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc         "The type of the user"}])

(defn create-user-document-args
  ([id]
   (create-user-document-args id nil))
  ([id name]
   (let [user {}]
     (as-> user $
           (map-utils/add-if-not-nil $ :user/id (uuid-utils/string-to-uuid id))
           (map-utils/add-if-not-nil $ :user/name name)))))

(defn create-user-document [user]
  (let [{:keys [id name]} user]
    (create-user-document-args id name)))

(defn get-id [user-document]
  (map-utils/get-when user-document :user/id))

(defn get-name [user-document]
  (map-utils/get-when user-document :user/name))

(defn to-domain [user-document]
  (domain-user/create-user-all-args
   (str (get-id user-document))
   (get-name user-document)))
