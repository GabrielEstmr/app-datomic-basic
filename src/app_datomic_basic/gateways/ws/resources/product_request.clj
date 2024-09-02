(ns app-datomic-basic.gateways.ws.resources.product-request
  (:require [app-datomic-basic.domain.product :as product]
            [app-datomic-basic.domain.category :as category]))

(defn to-domain [product-request]
  (let [{:keys [name slug price keywords]} product-request
        {:keys [id]} (get product-request :category)]
    (product/create-product-all-args nil name slug price keywords (category/create-category-all-args id))))
