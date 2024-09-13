(ns app-datomic-basic.gateways.datomic.documents.account
  (:require
   [app-datomic-basic.utils.map-utils :as map-utils]
   [app-datomic-basic.domain.account :as domain-account]
   [app-datomic-basic.gateways.datomic.documents.user :as documents.user]
   [app-datomic-basic.utils.uuid-utils :as uuid-utils]))

(def schema
  [{:db/ident       :account/id
    :db/valueType   :db.type/uuid
    :db/cardinality :db.cardinality/one
    :db/unique      :db.unique/identity
    :db/doc         "The name of the account"}

   {:db/ident       :account/bank
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc         "The bank name of the account"}

   {:db/ident       :account/balance
    :db/valueType   :db.type/bigdec
    :db/cardinality :db.cardinality/one
    :db/doc         "The balance of the account"}

   {:db/ident       :account/user
    :db/valueType   :db.type/ref                            ; ref only by db/id (not ideal)
    :db/cardinality :db.cardinality/one
    :db/doc         "user for the account"}])

(defn assoc-user [account user]
  (if user
    (map-utils/add-if-not-nil account :account/user (documents.user/create-user-document user))
    account))


(defn create-account-document-args
  [id bank balance user]
  (let [account {}]
    (-> account
        (map-utils/add-if-not-nil :account/id (uuid-utils/string-to-uuid id))
        (map-utils/add-if-not-nil :account/bank bank)
        (map-utils/add-if-not-nil :account/balance balance)
        (assoc-user user))))

(defn create-account-document [account]
  (let [{:keys [id bank balance user]} account]
    (create-account-document-args id bank balance user)))

(defn get-id [account-document]
  (map-utils/get-when account-document :account/id))

(defn get-name [account-document]
  (map-utils/get-when account-document :account/name))

(defn get-balance [account-document]
  (map-utils/get-when account-document :account/balance))

(defn get-user [account-document]
  (map-utils/get-when account-document :account/user))

(defn to-domain [account-document]
  (domain-account/create-account-all-args
   (str (get-id account-document))
   (get-name account-document)
   (get-balance account-document)
   (documents.user/to-domain (get-user account-document))))
