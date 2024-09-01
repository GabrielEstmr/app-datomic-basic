(ns app-datomic-basic.usecases.update-product
  (:require [app-datomic-basic.usecases.find-product-by-id :as find-product-by-id]
            [app-datomic-basic.domain.product :as product]))

(defn execute [productDatabaseGateway]
  (fn [product]
    (let [find-product-by-id-fn (find-product-by-id/execute productDatabaseGateway)
          product-found               (find-product-by-id-fn (product/get-id product))
          updated-product       (.update productDatabaseGateway product)]
      updated-product)))
