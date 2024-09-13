(ns app-datomic-basic.gateways.ws.resources.transaction-response
  (:require
   [app-datomic-basic.domain.transaction :as transaction]
   [app-datomic-basic.utils.map-utils :as map-utils]))

(defn create-transaction-response-all-args [id type value account user]
  (let [transaction-resource-base {}]
    (-> transaction-resource-base
        (map-utils/add-if-not-nil :id id)
        (map-utils/add-if-not-nil :type type)
        (map-utils/add-if-not-nil :value value)
        (map-utils/add-if-not-nil :account account)
        (map-utils/add-if-not-nil :user user))))

(defn create-transaction-response [transaction]
  (create-transaction-response-all-args
   (transaction/get-id transaction)
   (transaction/get-type transaction)
   (transaction/get-value transaction)
   (transaction/get-account transaction)
   (transaction/get-user transaction)))
