(ns app-datomic-basic.usecases.create-user)

(defn execute [userDatabaseGateway]
  (fn [user]
    (let [saved-user   (.save userDatabaseGateway user)]
      saved-user)))
