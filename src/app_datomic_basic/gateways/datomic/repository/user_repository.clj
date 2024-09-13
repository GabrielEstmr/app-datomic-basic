(ns app-datomic-basic.gateways.datomic.repository.user-repository
  (:require
   [datomic.api :as d]
   [app-datomic-basic.configs.datomic :as datomic-config]
   [app-datomic-basic.utils.uuid-utils :as uuid-utils]))

(defn save [user-document]
  (let [user-document-id (uuid-utils/assoc-uuid user-document :user/id)]
    @(d/transact (datomic-config/get-db) [user-document-id])
    user-document-id))

(defn find-by-id [user-id]
  (let [response (d/pull (d/db (datomic-config/get-db)) '[*] [:user/id user-id])]
    response))
