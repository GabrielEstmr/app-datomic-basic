(ns app-datomic-basic.gateways.datomic.transaction-database-gateway
  (:require [app-datomic-basic.gateways.transaction-database-gateway :as transaction-database-gateway]
            [app-datomic-basic.gateways.datomic.repository.transaction-repository :as transaction-repository]
            [app-datomic-basic.gateways.datomic.documents.transaction :as transaction-document]
            [app-datomic-basic.utils.uuid-utils :as uuid-utils]))

(defn save-impl [transaction]
  (let [transaction-doc       (transaction-document/create-transaction-document transaction)
        saved-transaction-doc (transaction-repository/save transaction-doc)]
    (println saved-transaction-doc)
    (when saved-transaction-doc
      (transaction-document/to-domain saved-transaction-doc))))

(defn find-transaction-by-id-impl [id]
  (let [transaction-document (transaction-repository/find-by-id (uuid-utils/string-to-uuid id))]
    (when transaction-document
      (transaction-document/to-domain transaction-document))))

(defrecord TransactionDatabaseGatewayImpl []
  transaction-database-gateway/TransactionDatabaseGateway
  (save [_ transaction]
    (save-impl transaction))
  (findById [_ id]
    (find-transaction-by-id-impl id)))
