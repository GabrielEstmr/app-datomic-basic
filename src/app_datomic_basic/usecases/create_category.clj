(ns app-datomic-basic.usecases.create-category
  (:require [app-datomic-basic.domain.category :as category]
            [schema.core :as s]))

(defn execute [categoryDatabaseGateway]
  (s/fn
    [category :- category/Category]
    :- category/Category
    (let [saved-category (.save categoryDatabaseGateway category)]
      saved-category)))
