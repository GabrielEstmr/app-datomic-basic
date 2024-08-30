(ns app-datomic-basic.usecases.beans.usecase-beans
  (:require [app-datomic-basic.gateways.datomic.product-database-gateway :as productDatabaseGateway]
            [app-datomic-basic.usecases.create-product :as usecaseCreateProduct]
            [app-datomic-basic.usecases.find-product-by-name :as usecaseFindProductByNameProduct]))

(defn get-beans []
  (let [productDatabaseGateway          (productDatabaseGateway/->ProductDatabaseGatewayImpl)
        usecaseCreateProduct            (usecaseCreateProduct/execute productDatabaseGateway)
        usecaseFindProductByNameProduct (usecaseFindProductByNameProduct/execute productDatabaseGateway)]
    {:usecaseCreateProduct usecaseCreateProduct
     :usecaseFindProductByNameProduct usecaseFindProductByNameProduct}))
