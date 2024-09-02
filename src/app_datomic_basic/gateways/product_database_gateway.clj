(ns app-datomic-basic.gateways.product-database-gateway)

(defprotocol ProductDatabaseGateway
  (save [this product])
  (update [this product])
  (findByName [this name])
  (findById [this id]))
