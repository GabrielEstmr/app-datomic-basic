(ns app-datomic-basic.usecases.create-transaction
  (:require [app-datomic-basic.usecases.find-account-by-id :as usecases.find-account-by-id]
            [app-datomic-basic.usecases.update-account-balance :as usecases.update-account-balance]
            [app-datomic-basic.domain.account :as domain.account]
            [app-datomic-basic.domain.transaction :as domain.transaction]))

(defn execute [transactionDatabaseGateway accountDatabaseGateway]
  (fn [transaction]
    (let [usecase-find-account-by-id (usecases.find-account-by-id/execute accountDatabaseGateway)
          account (usecase-find-account-by-id (domain.account/get-id (domain.transaction/get-account transaction)))
          updated-account (usecases.update-account-balance/execute account transaction)
          saved-transaction (.save transactionDatabaseGateway transaction updated-account)]
      saved-transaction)))
