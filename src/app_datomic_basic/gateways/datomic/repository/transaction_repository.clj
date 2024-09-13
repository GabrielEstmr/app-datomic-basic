(ns app-datomic-basic.gateways.datomic.repository.transaction-repository
  (:require
   [datomic.api :as d]
   [app-datomic-basic.configs.datomic :as datomic-config]
   [app-datomic-basic.utils.uuid-utils :as uuid-utils]))

(defn save [transaction-document]
  (let [transaction-document-id (uuid-utils/assoc-uuid transaction-document :transaction/id)]
    @(d/transact (datomic-config/get-db) [transaction-document-id])
    (println transaction-document-id)
    transaction-document-id))

(defn find-by-id [id]
  (let [response (d/q '[:find (pull ?product [* {:transaction/user [*]} {:transaction/account [*]}])
                        :in $ ?id
                        :where [?product :transaction/id ?id]] (d/db (datomic-config/get-db)) id)]
    (ffirst response)))
