(ns app-datomic-basic.usecases.beans.usecase-beans
  (:require [app-datomic-basic.gateways.datomic.product-database-gateway :as productDatabaseGateway]
            [app-datomic-basic.gateways.datomic.category-database-gateway :as categoryDatabaseGateway]
            [app-datomic-basic.usecases.create-product :as usecaseCreateProduct]
            [app-datomic-basic.usecases.update-product :as usecaseUpdateProduct]
            [app-datomic-basic.usecases.find-product-by-name :as usecaseFindProductByNameProduct]
            [app-datomic-basic.usecases.find-product-by-id :as usecaseFindProductByIdProduct]
            [app-datomic-basic.usecases.create-category :as usecaseCreateCategory]
            [app-datomic-basic.usecases.find-category-by-id :as usecaseFindCategoryByIdCategory]))

(defn get-beans []
  (let [productDatabaseGateway          (productDatabaseGateway/->ProductDatabaseGatewayImpl)
        categoryDatabaseGateway         (categoryDatabaseGateway/->CategoryDatabaseGatewayImpl)
        usecaseCreateCategory           (usecaseCreateCategory/execute categoryDatabaseGateway)
        usecaseFindCategoryByIdCategory (usecaseFindCategoryByIdCategory/execute categoryDatabaseGateway)
        usecaseCreateProduct            (usecaseCreateProduct/execute productDatabaseGateway)
        usecaseUpdateProduct            (usecaseUpdateProduct/execute productDatabaseGateway)
        usecaseFindProductByNameProduct (usecaseFindProductByNameProduct/execute productDatabaseGateway)
        usecaseFindProductByIdProduct   (usecaseFindProductByIdProduct/execute productDatabaseGateway)]
    {:usecaseCreateCategory           usecaseCreateCategory
     :usecaseFindCategoryByIdCategory usecaseFindCategoryByIdCategory
     :usecaseCreateProduct            usecaseCreateProduct
     :usecaseUpdateProduct            usecaseUpdateProduct
     :usecaseFindProductByNameProduct usecaseFindProductByNameProduct
     :usecaseFindProductByIdProduct   usecaseFindProductByIdProduct}))
