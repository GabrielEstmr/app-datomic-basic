(ns app-datomic-basic.gateways.ws.resources.transaction-request
  (:require [app-datomic-basic.domain.transaction :as transaction]
            [app-datomic-basic.domain.account :as account]))

(defn to-domain [transaction-request]
  (let [{:keys [type value]} transaction-request
        account-id (get-in transaction-request [:account :id])]
    (transaction/create-new-transaction type (bigdec value) (account/create-new-account account-id))))
