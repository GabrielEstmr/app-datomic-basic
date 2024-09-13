(ns app-datomic-basic.usecases.find-account-by-id
  (:import [src.domains.exceptions ResourceNotFoundException]))

(defn execute [accountDatabaseGateway]
  (fn [id]
    (let [account (.findById accountDatabaseGateway id)]
      (when-not account
        (throw (ResourceNotFoundException. "Account not found")))
      account)))
