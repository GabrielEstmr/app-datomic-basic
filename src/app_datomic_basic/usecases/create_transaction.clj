(ns app-datomic-basic.usecases.create-transaction)

(defn execute [transactionDatabaseGateway]
  (fn [transaction]
    (let [saved-transaction   (.save transactionDatabaseGateway transaction)]
      saved-transaction)))
