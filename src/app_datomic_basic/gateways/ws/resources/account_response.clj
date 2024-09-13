(ns app-datomic-basic.gateways.ws.resources.account-response
  (:require
   [app-datomic-basic.domain.account :as account]
   [app-datomic-basic.utils.map-utils :as map-utils]))

(defn create-account-response-all-args [id bank balance user]
  (let [account-resource-base {}]
    (-> account-resource-base
        (map-utils/add-if-not-nil :id id)
        (map-utils/add-if-not-nil :bank bank)
        (map-utils/add-if-not-nil :balance balance)
        (map-utils/add-if-not-nil :user user))))

(defn create-account-response [account]
  (create-account-response-all-args
   (account/get-id account)
   (account/get-bank account)
   (account/get-balance account)
   (account/get-user account)))
