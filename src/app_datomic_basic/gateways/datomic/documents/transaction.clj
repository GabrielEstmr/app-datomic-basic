(ns app-datomic-basic.gateways.datomic.documents.transaction
  (:require
   [app-datomic-basic.utils.map-utils :as map-utils]
   [app-datomic-basic.domain.transaction :as domain-transaction]
   [app-datomic-basic.gateways.datomic.documents.user :as documents.user]
   [app-datomic-basic.gateways.datomic.documents.account :as documents.account]
   [app-datomic-basic.utils.uuid-utils :as uuid-utils]))

(def schema
  [{:db/ident       :transaction/id
    :db/valueType   :db.type/uuid
    :db/cardinality :db.cardinality/one
    :db/unique      :db.unique/identity
    :db/doc         "The name of the transaction"}

   {:db/ident       :transaction/type
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc         "The type of the transaction"}

   {:db/ident       :transaction/value
    :db/valueType   :db.type/bigdec
    :db/cardinality :db.cardinality/one
    :db/doc         "The type of the transaction"}

   {:db/ident       :transaction/account
    :db/valueType   :db.type/ref                            ; ref only by db/id (not ideal)
    :db/cardinality :db.cardinality/one
    :db/doc         "transaction for the transaction"}

   {:db/ident       :transaction/user
    :db/valueType   :db.type/ref                            ; ref only by db/id (not ideal)
    :db/cardinality :db.cardinality/one
    :db/doc         "transaction for the transaction"}])

(defn create-transaction-document-args [id type value account user]
  (let [transaction {}]
    (-> transaction
        (map-utils/add-if-not-nil :transaction/id (uuid-utils/string-to-uuid id))
        (map-utils/add-if-not-nil :transaction/type type)
        (map-utils/add-if-not-nil :transaction/value value)
        (map-utils/add-if-not-nil :transaction/account (documents.account/create-account-document account))
        (map-utils/add-if-not-nil :transaction/user (documents.user/create-user-document user)))))

(defn create-transaction-document [transaction]
  (let [{:keys [id type value account user]} transaction]
    (create-transaction-document-args id type value account user)))

(defn get-id [transaction-document]
  (map-utils/get-when transaction-document :transaction/id))

(defn get-type [transaction-document]
  (map-utils/get-when transaction-document :transaction/type))

(defn get-value [transaction-document]
  (map-utils/get-when transaction-document :transaction/value))

(defn get-account [transaction-document]
  (map-utils/get-when transaction-document :transaction/account))

(defn get-user [transaction-document]
  (map-utils/get-when transaction-document :transaction/user))

(defn to-domain [transaction-document]
  (domain-transaction/create-transaction-all-args
   (str (get-id transaction-document))
   (get-type transaction-document)
   (get-value transaction-document)
   (get-account transaction-document)
   (get-user transaction-document)))
