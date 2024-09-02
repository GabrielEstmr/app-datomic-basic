(ns app-datomic-basic.usecases.find-product-by-name
  (:import [src.domains.exceptions ResourceNotFoundException]))

(defn execute [productDatabaseGateway]
  (fn [name]
    (let [product (.findByName productDatabaseGateway name)]
      (when-not product
        (throw (ResourceNotFoundException. "Product not found")))
      product)))
