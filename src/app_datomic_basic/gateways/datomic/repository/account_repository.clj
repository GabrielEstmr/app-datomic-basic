(ns app-datomic-basic.gateways.datomic.repository.account-repository
  (:require
   [datomic.api :as d]
   [app-datomic-basic.configs.datomic :as datomic-config]
   [app-datomic-basic.utils.uuid-utils :as uuid-utils]))

(defn save [account-document]
  (let [account-document-id (uuid-utils/assoc-uuid account-document :account/id)]
    @(d/transact (datomic-config/get-db) [account-document-id])
    account-document-id))

(defn find-by-id [id]
  (let [response (d/q '[:find (pull ?product [* {:account/user [*]}])
                        :in $ ?id
                        :where [?product :account/id ?id]] (d/db (datomic-config/get-db)) id)]
    (ffirst response)))
