(ns app-datomic-basic.usecases.find-product-by-name
  (:import [src.domains.exceptions ResourceNotFoundException]))

(defn execute [productDatabaseGateway]
  (fn [product]
    (let [product (.findByName productDatabaseGateway product)]
      (when-not product
        (throw (ResourceNotFoundException. "Product nor found")))
      product)))
