(ns app-datomic-basic.domain.product)

(defn create-product-all-args [id name slug price]
  {:id    id
   :name  name
   :slug  slug
   :price price})
