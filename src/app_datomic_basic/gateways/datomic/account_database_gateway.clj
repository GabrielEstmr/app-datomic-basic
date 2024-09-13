(ns app-datomic-basic.gateways.datomic.account-database-gateway
  (:require [app-datomic-basic.gateways.account-database-gateway :as account-database-gateway]
            [app-datomic-basic.gateways.datomic.repository.account-repository :as account-repository]
            [app-datomic-basic.gateways.datomic.documents.account :as account-document]
            [app-datomic-basic.utils.uuid-utils :as uuid-utils]))

(defn save-impl [account]
  (let [account-doc       (account-document/create-account-document account)
        saved-account-doc (account-repository/save account-doc)]
    (when saved-account-doc
      (account-document/to-domain saved-account-doc))))

(defn find-account-by-id-impl [id]
  (let [account-document (account-repository/find-by-id (uuid-utils/string-to-uuid id))]
    (when account-document
      (account-document/to-domain account-document))))

(defrecord AccountDatabaseGatewayImpl []
  account-database-gateway/AccountDatabaseGateway
  (save [_ account]
    (save-impl account))
  (findById [_ id]
    (find-account-by-id-impl id)))
