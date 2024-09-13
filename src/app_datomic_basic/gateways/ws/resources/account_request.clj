(ns app-datomic-basic.gateways.ws.resources.account-request
  (:require [app-datomic-basic.domain.account :as account]
            [app-datomic-basic.domain.user :as user]))

(defn to-domain [account-request]
  (let [{:keys [bank]} account-request
        {:keys [id]} (get account-request :user)]
    (account/create-new-account bank (user/create-new-user id))))
