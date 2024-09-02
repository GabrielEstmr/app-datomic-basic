(ns app-datomic-basic.gateways.ws.resources.update-product-request
  (:require [app-datomic-basic.domain.product :as product]))

(defn to-domain [product-request]
  (let [{:keys [id name slug price keywords]} product-request]
    (product/create-product-all-args id name, slug, price keywords)))
