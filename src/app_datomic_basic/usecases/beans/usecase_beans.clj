(ns app-datomic-basic.usecases.beans.usecase-beans
  (:require [app-datomic-basic.gateways.datomic.product-database-gateway :as productDatabaseGateway]
            [app-datomic-basic.usecases.create-product :as usecaseCreateProduct]
            [app-datomic-basic.usecases.update-product :as usecaseUpdateProduct]
            [app-datomic-basic.usecases.find-product-by-name :as usecaseFindProductByNameProduct]
            [app-datomic-basic.usecases.find-product-by-id :as usecaseFindProductByIdProduct]))

(defn get-beans []
  (let [productDatabaseGateway          (productDatabaseGateway/->ProductDatabaseGatewayImpl)
        usecaseCreateProduct            (usecaseCreateProduct/execute productDatabaseGateway)
        usecaseUpdateProduct            (usecaseUpdateProduct/execute productDatabaseGateway)
        usecaseFindProductByNameProduct (usecaseFindProductByNameProduct/execute productDatabaseGateway)
        usecaseFindProductByIdProduct   (usecaseFindProductByIdProduct/execute productDatabaseGateway)]
    {:usecaseCreateProduct            usecaseCreateProduct
     :usecaseUpdateProduct            usecaseUpdateProduct
     :usecaseFindProductByNameProduct usecaseFindProductByNameProduct
     :usecaseFindProductByIdProduct   usecaseFindProductByIdProduct}))
