(ns app-datomic-basic.gateways.ws.resources.update-product-request
  (:require [app-datomic-basic.domain.product :as product]
            [app-datomic-basic.domain.category :as category]))

; TODO: better impl get values if nil
(defn to-domain [product-request]
  (let [{:keys [id name slug price keywords]} product-request
        category (get product-request :category)
        category-id (get category :id)]
    (product/create-product-all-args id name slug price keywords (category/create-category-all-args category-id))))
