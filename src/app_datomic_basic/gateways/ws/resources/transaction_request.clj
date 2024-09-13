(ns app-datomic-basic.gateways.ws.resources.transaction-request
  (:require [app-datomic-basic.domain.transaction :as transaction]
            [app-datomic-basic.domain.account :as account]
            [app-datomic-basic.domain.user :as user]))

(defn to-domain [transaction-request]
  (let [{:keys      [type value]} transaction-request
        account-id (get (get transaction-request :account) :id)
        user-id    (get (get transaction-request :user) :id)]
    (transaction/create-new-transaction type (bigdec value) (account/create-new-account account-id) (user/create-new-user user-id))))
