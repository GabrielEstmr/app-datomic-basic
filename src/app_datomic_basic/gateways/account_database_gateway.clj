(ns app-datomic-basic.gateways.account-database-gateway)

(defprotocol AccountDatabaseGateway
  (save [this transaction])
  (findById [this id]))
