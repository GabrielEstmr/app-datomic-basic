(ns app-datomic-basic.usecases.find-product-by-id
  (:import [src.domains.exceptions ResourceNotFoundException]))

(defn execute [productDatabaseGateway]
  (fn [id]
    (let [product (.findById productDatabaseGateway id)]
      (when-not product
        (throw (ResourceNotFoundException. "Product not found")))
      product)))
