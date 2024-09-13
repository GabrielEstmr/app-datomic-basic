(ns app-datomic-basic.usecases.find-user-by-id
  (:import [src.domains.exceptions ResourceNotFoundException]))

(defn execute [userDatabaseGateway]
  (fn [id]
    (let [user (.findById userDatabaseGateway id)]
      (when-not user
        (throw (ResourceNotFoundException. "User not found")))
      user)))
