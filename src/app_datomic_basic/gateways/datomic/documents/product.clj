(ns app-datomic-basic.gateways.datomic.documents.product
  (:require [datomic.api :as d]
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


;(def schema [
;             {:db/ident       :product/name                 ;entity/property (to avoid key conflicts in datomic
;              :db/valueType   :db.type/string
;              :db/cardinality :db.cardinality/one
;              :db/doc         "product's name"}
;             {:db/ident       :product/slug
;              :db/valueType   :db.type/string
;              :db/cardinality :db.cardinality/one
;              :db/doc         "product's path"}
;             {:db/ident       :product/price
;              :db/valueType   :db.type/bigdec
;              :db/cardinality :db.cardinality/one
;              :db/doc         "product's price"}
;             ])

;(def product-schema
;  [{:db/ident       :product/name
;    :db/valueType   :db.type/string
;    :db/cardinality :db.cardinality/one
;    :db/unique      :db.unique/identity
;    :db/doc         "The name of the product"}
;
;   {:db/ident       :product/description
;    :db/valueType   :db.type/string
;    :db/cardinality :db.cardinality/one
;    :db/doc         "A description of the product"}
;
;   {:db/ident       :product/price
;    :db/valueType   :db.type/double
;    :db/cardinality :db.cardinality/one
;    :db/doc         "The price of the product"}])


(defn create-product-document-all-args [name slug price]
  {:name  name
   :slug  slug
   :price price})

(defn create-product-document [product]
  (let [{:keys [name
                slug
                price
                ]} product]
    (create-product-document-all-args
     name
     slug
     price
     )))

(defn to-domain [product-document]
  (let [{:keys [name
                slug
                price]} product-document]
    (domain-product/create-product-all-args
     name
     slug
     price)))

(defn get-name [product-document]
  (when product-document
    (get product-document :name nil)))

(defn get-slug [product-document]
  (when product-document
    (get product-document :slug nil)))

(defn get-price [product-document]
  (when product-document
    (get product-document :price nil)))
