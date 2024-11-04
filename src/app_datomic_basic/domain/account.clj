(ns app-datomic-basic.domain.account
  (:require [app-datomic-basic.utils.map-utils :as map-utils]
            [app-datomic-basic.domain.user :as user]
            [schema.core :as s]))

(def account-skeleton
  {:id      {:schema   s/Str
             :doc      "Accounts' id"
             :required true}
   :bank    {:schema   (s/maybe s/Str)
             :doc      "Accounts' id"
             :required false}
   :balance {:schema   BigDecimal
             :doc      "Accounts' balance"
             :required false}
   :user    {:schema   user/User
             :doc      "Accounts' user"
             :required false}
   :version {:schema   s/Int
             :doc      "Account Balance's id"
             :required true}})

(def Account account-skeleton)

(defn create-account-all-args [id bank balance user version]
  (let [account {}]
    (-> account
        (map-utils/add-if-not-nil :id id)
        (map-utils/add-if-not-nil :bank bank)
        (map-utils/add-if-not-nil :balance balance)
        (map-utils/add-if-not-nil :user user)
        (map-utils/add-if-not-nil :version version))))

(defn create-new-account
  ([id]
   (create-account-all-args id nil nil nil nil))
  ([bank user]
   (create-account-all-args nil bank BigDecimal/ZERO user 0)))

(defn get-id [account]
  (map-utils/get-when account :id))

(defn get-bank [account]
  (map-utils/get-when account :bank))

(defn get-balance [account]
  (map-utils/get-when account :balance))

(defn get-user [account]
  (map-utils/get-when account :user))

(defn get-version [account]
  (map-utils/get-when account :version))
