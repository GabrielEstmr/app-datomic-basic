(ns app-datomic-basic.gateways.datomic.documents.category
  (:require
   [app-datomic-basic.utils.map-utils :as map-utils]
   [app-datomic-basic.domain.category :as domain-category]
   [app-datomic-basic.utils.uuid-utils :as uuid-utils]))

(def schema
  [{:db/ident       :category/id
    :db/valueType   :db.type/uuid
    :db/cardinality :db.cardinality/one
    :db/unique      :db.unique/identity
    :db/doc         "The name of the product"}

   {:db/ident       :category/name
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc         "The name of the product"}])

(defn create-category-document-args
  ([id]
   (create-category-document-args id nil))
  ([id name]
   (let [category {}]
     (as-> category $
           (map-utils/add-if-not-nil $ :category/id (uuid-utils/string-to-uuid id))
           (map-utils/add-if-not-nil $ :category/name name)))))

(defn create-category-document [category]
  (let [{:keys [id name]} category]
    (create-category-document-args id name)))

(defn get-id [category-document]
  (map-utils/get-when category-document :category/id))

(defn get-name [category-document]
  (map-utils/get-when category-document :category/name))

(defn to-domain [category-document]
  (domain-category/create-category-all-args
   (str (get-id category-document))
   (get-name category-document)))
