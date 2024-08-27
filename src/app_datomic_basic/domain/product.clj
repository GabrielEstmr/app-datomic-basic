(ns app-datomic-basic.domain.product)

(defn create-product-all-args [name slug price]
  {:name  name
   :slug  slug
   :price price})
