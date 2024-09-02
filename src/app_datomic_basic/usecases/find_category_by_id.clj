(ns app-datomic-basic.usecases.find-category-by-id
  (:import [src.domains.exceptions ResourceNotFoundException]))

(defn execute [categoryDatabaseGateway]
  (fn [id]
    (let [category (.findById categoryDatabaseGateway id)]
      (when-not category
        (throw (ResourceNotFoundException. "Category not found")))
      category)))
