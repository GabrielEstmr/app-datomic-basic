(ns app-datomic-basic.gateways.product-database-gateway)


(defprotocol ProductDatabaseGateway
  (save [this product])
  (find-product-by-name [this name]))
