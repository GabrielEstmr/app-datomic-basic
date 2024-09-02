(ns app-datomic-basic.gateways.datomic.documents.product
  (:require
   [app-datomic-basic.utils.map-utils :as map-utils]
   [app-datomic-basic.gateways.datomic.documents.category :as documents.category]
   [app-datomic-basic.domain.product :as domain-product]
   [app-datomic-basic.utils.uuid-utils :as uuid-utils]))

(def schema
  [{:db/ident       :product/id
    :db/valueType   :db.type/uuid
    :db/cardinality :db.cardinality/one
    :db/unique      :db.unique/identity
    :db/doc         "The name of the product"}

   {:db/ident       :product/name
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc         "The name of the product"}

   {:db/ident       :product/slug
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc         "A description of the product"}

   {:db/ident       :product/price
    :db/valueType   :db.type/double
    :db/cardinality :db.cardinality/one
    :db/doc         "The price of the product"}

   {:db/ident       :product/keyword
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/many
    :db/doc         "Keyword for the product"}

   {:db/ident       :product/category
    :db/valueType   :db.type/ref                            ; ref only by db/id (not ideal)
    :db/cardinality :db.cardinality/one
    :db/doc         "Category for the product"}])

(defn create-product-document-all-args [id name slug price keywords category]
  (let [product {}]
    (as-> product $
          (map-utils/add-if-not-nil $ :product/id (uuid-utils/string-to-uuid id))
          (map-utils/add-if-not-nil $ :product/name name)
          (map-utils/add-if-not-nil $ :product/slug slug)
          (map-utils/add-if-not-nil $ :product/price price)
          (map-utils/add-if-not-nil $ :product/keyword keywords)
          (map-utils/add-if-not-nil $ :product/category
                                    (documents.category/create-category-document category)))))

(defn create-product-document [product]
  (let [{:keys [id name slug price keywords]} product]
    (create-product-document-all-args id name slug price keywords (get product :category))))

(defn get-id [product-document]
  (map-utils/get-when product-document :product/id))

(defn get-name [product-document]
  (map-utils/get-when product-document :product/name))

(defn get-slug [product-document]
  (map-utils/get-when product-document :product/slug))

(defn get-price [product-document]
  (map-utils/get-when product-document :product/price))

(defn get-keywords [product-document]
  (map-utils/get-when product-document :product/keyword))

(defn get-category [product-document]
  (map-utils/get-when product-document :product/category))

(defn to-domain [product-document]
  (domain-product/create-product-all-args
   (str (get-id product-document))
   (get-name product-document)
   (get-slug product-document)
   (get-price product-document)
   (get-keywords product-document)
   (documents.category/to-domain (get-category product-document))))
