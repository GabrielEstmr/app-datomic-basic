(ns app-datomic-basic.gateways.user-database-gateway)

(defprotocol UserDatabaseGateway
  (save [this transaction])
  (findById [this id]))
