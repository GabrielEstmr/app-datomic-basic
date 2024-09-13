(ns app-datomic-basic.usecases.beans.usecase-beans
  (:require [app-datomic-basic.gateways.datomic.product-database-gateway :as productDatabaseGateway]
            [app-datomic-basic.gateways.datomic.category-database-gateway :as categoryDatabaseGateway]
            [app-datomic-basic.gateways.datomic.account-database-gateway :as accountDatabaseGateway]
            [app-datomic-basic.gateways.datomic.user-database-gateway :as userDatabaseGateway]
            [app-datomic-basic.gateways.datomic.transaction-database-gateway :as transactionDatabaseGateway]
            [app-datomic-basic.usecases.create-product :as usecaseCreateProduct]
            [app-datomic-basic.usecases.update-product :as usecaseUpdateProduct]
            [app-datomic-basic.usecases.find-product-by-name :as usecaseFindProductByNameProduct]
            [app-datomic-basic.usecases.find-product-by-id :as usecaseFindProductByIdProduct]
            [app-datomic-basic.usecases.create-category :as usecaseCreateCategory]
            [app-datomic-basic.usecases.find-category-by-id :as usecaseFindCategoryByIdCategory]
            [app-datomic-basic.usecases.create-account :as usecaseCreateAccount]
            [app-datomic-basic.usecases.create-transaction :as usecaseCreateTransaction]
            [app-datomic-basic.usecases.create-user :as usecaseCreateUser]
            [app-datomic-basic.usecases.find-account-by-id :as usecaseFindAccountById]
            [app-datomic-basic.usecases.find-transaction-by-id :as usecaseFindTransactionById]
            [app-datomic-basic.usecases.find-user-by-id :as usecaseFindUserById]))

(defn get-beans []
  (let [productDatabaseGateway          (productDatabaseGateway/->ProductDatabaseGatewayImpl)
        categoryDatabaseGateway         (categoryDatabaseGateway/->CategoryDatabaseGatewayImpl)
        accountDatabaseGateway          (accountDatabaseGateway/->AccountDatabaseGatewayImpl)
        userDatabaseGateway             (userDatabaseGateway/->UserDatabaseGatewayImpl)
        transactionDatabaseGateway      (transactionDatabaseGateway/->TransactionDatabaseGatewayImpl)
        usecaseCreateCategory           (usecaseCreateCategory/execute categoryDatabaseGateway)
        usecaseFindCategoryByIdCategory (usecaseFindCategoryByIdCategory/execute categoryDatabaseGateway)
        usecaseCreateProduct            (usecaseCreateProduct/execute productDatabaseGateway)
        usecaseUpdateProduct            (usecaseUpdateProduct/execute productDatabaseGateway)
        usecaseFindProductByNameProduct (usecaseFindProductByNameProduct/execute productDatabaseGateway)
        usecaseFindProductByIdProduct   (usecaseFindProductByIdProduct/execute productDatabaseGateway)
        usecaseCreateAccount            (usecaseCreateAccount/execute accountDatabaseGateway)
        usecaseCreateTransaction        (usecaseCreateTransaction/execute transactionDatabaseGateway)
        usecaseCreateUser               (usecaseCreateUser/execute userDatabaseGateway)
        usecaseFindAccountById          (usecaseFindAccountById/execute accountDatabaseGateway)
        usecaseFindTransactionById      (usecaseFindTransactionById/execute transactionDatabaseGateway)
        usecaseFindUserById             (usecaseFindUserById/execute userDatabaseGateway)]
    {:usecaseCreateCategory           usecaseCreateCategory
     :usecaseFindCategoryByIdCategory usecaseFindCategoryByIdCategory
     :usecaseCreateProduct            usecaseCreateProduct
     :usecaseUpdateProduct            usecaseUpdateProduct
     :usecaseFindProductByNameProduct usecaseFindProductByNameProduct
     :usecaseFindProductByIdProduct   usecaseFindProductByIdProduct
     :usecaseCreateAccount            usecaseCreateAccount
     :usecaseCreateTransaction        usecaseCreateTransaction
     :usecaseCreateUser               usecaseCreateUser
     :usecaseFindAccountById          usecaseFindAccountById
     :usecaseFindTransactionById      usecaseFindTransactionById
     :usecaseFindUserById             usecaseFindUserById}))
