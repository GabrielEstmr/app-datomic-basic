(ns app-datomic-basic.usecases.create-category)

(defn execute [categoryDatabaseGateway]
  (fn [category]
    (let [saved-category   (.save categoryDatabaseGateway category)]
      saved-category)))
