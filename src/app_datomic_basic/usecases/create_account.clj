(ns app-datomic-basic.usecases.create-account)

(defn execute [accountDatabaseGateway]
  (fn [account]
    (let [saved-account   (.save accountDatabaseGateway account)]
      (println saved-account)
      saved-account)))
