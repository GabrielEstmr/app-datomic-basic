(ns app-datomic-basic.usecases.find-transaction-by-id
  (:import [src.domains.exceptions ResourceNotFoundException]))

(defn execute [transactionDatabaseGateway]
  (fn [id]
    (let [transaction (.findById transactionDatabaseGateway id)]
      (when-not transaction
        (throw (ResourceNotFoundException. "Transaction not found")))
      transaction)))
