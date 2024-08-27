(ns app-datomic-basic.gateways.ws.resources.product-response)

(defn create-product-response-all-args [name slug price]
  {:name  name
   :slug  slug
   :price price})

(defn create-product-response [product]
  (let [{:keys [name
                slug
                price]} product]
    (create-product-response-all-args
     name
     slug
     price)))
