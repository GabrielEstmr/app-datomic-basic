(ns app-datomic-basic.gateways.ws.resources.product-response)

(defn create-product-response-all-args [id name slug price]
  {:id    id
   :name  name
   :slug  slug
   :price price})

(defn create-product-response [product]
  (let [{:keys [id
                name
                slug
                price]} product]
    (create-product-response-all-args
     id
     name
     slug
     price)))
