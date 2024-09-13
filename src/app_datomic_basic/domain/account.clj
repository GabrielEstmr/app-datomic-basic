(ns app-datomic-basic.domain.account
  (:require [app-datomic-basic.utils.map-utils :as map-utils]
            [app-datomic-basic.domain.user :as user]
            [schema.core :as s]))

(def Account
  {:id      s/Str
   :bank    s/Str
   :balance Long
   :user    user/User})

(defn create-account-all-args [id bank balance user]
  (let [account {}]
    (-> account
        (map-utils/add-if-not-nil :id id)
        (map-utils/add-if-not-nil :bank bank)
        (map-utils/add-if-not-nil :balance balance)
        (map-utils/add-if-not-nil :user user))))

(defn create-new-account
  ([id]
   (create-account-all-args id nil nil nil))
  ([bank user]
   (create-account-all-args nil bank BigDecimal/ZERO user)))

(defn get-id [account]
  (map-utils/get-when account :id))

(defn get-bank [account]
  (map-utils/get-when account :bank))

(defn get-balance [account]
  (map-utils/get-when account :balance))

(defn get-user [account]
  (map-utils/get-when account :user))
