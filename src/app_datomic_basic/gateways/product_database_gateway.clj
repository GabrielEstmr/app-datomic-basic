(ns app-datomic-basic.gateways.product-database-gateway)


(defprotocol ProductDatabaseGateway
  (save [this product])
  (findByName [this name]))
