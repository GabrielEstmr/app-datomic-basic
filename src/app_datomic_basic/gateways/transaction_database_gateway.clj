(ns app-datomic-basic.gateways.transaction-database-gateway)

(defprotocol TransactionDatabaseGateway
  (save [this transaction account])
  (findById [this id]))
