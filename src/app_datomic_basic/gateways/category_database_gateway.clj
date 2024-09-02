(ns app-datomic-basic.gateways.category-database-gateway)

(defprotocol CategoryDatabaseGateway
  (save [this product])
  (findById [this id]))
