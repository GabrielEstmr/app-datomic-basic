(ns app-datomic-basic.gateways.ws.resources.product-request
  (:require [app-datomic-basic.domain.product :as product]))

(defn to-domain [product-request]
  (let [{:keys [name slug price]} product-request]
    (product/create-product-all-args name, slug, price)))
