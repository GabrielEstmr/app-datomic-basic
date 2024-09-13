(ns app-datomic-basic.domain.transaction
  (:require [app-datomic-basic.utils.map-utils :as map-utils]
            [app-datomic-basic.domain.account :as account]
            [app-datomic-basic.domain.user :as user]
            [schema.core :as s]))

(def Transaction
  {:id      s/Str
   :type    s/Str
   :value   s/Str
   :account account/Account                                 ;java.utils.UUID
   :user    user/User})


(defn create-transaction-all-args [id type value account user]
  (let [transaction {}]
    (-> transaction
        (map-utils/add-if-not-nil :id id)
        (map-utils/add-if-not-nil :type type)
        (map-utils/add-if-not-nil :value value)
        (map-utils/add-if-not-nil :account account)
        (map-utils/add-if-not-nil :user user))))

(defn create-new-transaction [type value account user]
  (create-transaction-all-args nil type value account user))

(defn get-id [transaction]
  (map-utils/get-when transaction :id))

(defn get-type [transaction]
  (map-utils/get-when transaction :type))

(defn get-value [transaction]
  (map-utils/get-when transaction :value))

(defn get-account [transaction]
  (map-utils/get-when transaction :account))

(defn get-user [transaction]
  (map-utils/get-when transaction :user))
