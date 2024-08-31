(ns app-datomic-basic.gateways.datomic.documents.product
  (:require [datomic.api :as d]
            [app-datomic-basic.utils.map-utils :as map-utils]
            [app-datomic-basic.domain.product :as domain-product]))

(def schema
  [{:db/ident       :product/name
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/unique      :db.unique/identity
    :db/doc         "The name of the product"}

   {:db/ident       :product/slug
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc         "A description of the product"}

   {:db/ident       :product/price
    :db/valueType   :db.type/double
    :db/cardinality :db.cardinality/one
    :db/doc         "The price of the product"}])

(defn create-product-document-all-args [id name slug price]
  (let [product {}]
    (as-> product $
          (map-utils/add-if-not-nil $ :product/id id)
          (map-utils/add-if-not-nil $ :product/name name)
          (map-utils/add-if-not-nil $ :product/slug slug)
          (map-utils/add-if-not-nil $ :product/price price))))

(defn create-product-document [product]
  (let [{:keys [id name slug price]} product]
    (create-product-document-all-args id name slug price)))

(defn to-domain [product-document]
  (domain-product/create-product-all-args
   ((first product-document) 0)
   ((first product-document) 1)
   ((first product-document) 2)))

(defn get-id [product-document]
  (map-utils/get-when product-document :product/id))

(defn get-name [product-document]
  (map-utils/get-when product-document :product/name))

(defn get-slug [product-document]
  (map-utils/get-when product-document :product/slug))

(defn get-price [product-document]
  (map-utils/get-when product-document :product/price))
