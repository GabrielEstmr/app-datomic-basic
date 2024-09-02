(ns app-datomic-basic.usecases.create-product)

(defn execute [productDatabaseGateway]
  (fn [product]
    (let [saved-product   (.save productDatabaseGateway product)]
      saved-product)))
