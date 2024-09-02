(ns app-datomic-basic.gateways.ws.resources.category-request
  (:require [app-datomic-basic.domain.category :as category]))

(defn to-domain [category-request]
  (let [{:keys [name]} category-request]
    (category/create-category-all-args nil name)))
