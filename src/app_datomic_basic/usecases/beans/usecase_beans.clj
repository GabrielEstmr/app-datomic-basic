(ns app-datomic-basic.usecases.beans.usecase-beans
  (:require [app-datomic-basic.gateways.datomic.product-database-gateway :as productDatabaseGateway]
            [app-datomic-basic.usecases.create-product :as usecaseCreateProduct]))

(defn get-beans []
  (let [productDatabaseGateway (productDatabaseGateway/->ProductDatabaseGatewayImpl)
        usecaseCreateProduct   (usecaseCreateProduct/execute productDatabaseGateway)]
    {:usecaseCreateProduct usecaseCreateProduct}))
