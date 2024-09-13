(ns app-datomic-basic.gateways.transaction-database-gateway)

(defprotocol TransactionDatabaseGateway
  (save [this transaction])
  (findById [this id]))
