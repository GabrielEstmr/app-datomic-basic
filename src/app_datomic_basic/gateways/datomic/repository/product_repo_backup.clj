(ns app-datomic-basic.gateways.datomic.repository.product-repo-backup
  (:require
   [datomic.api :as d]
   [app-datomic-basic.utils.map-utils :as map-utils]
   [app-datomic-basic.configs.datomic :as datomic-config]
   [app-datomic-basic.gateways.datomic.documents.product :as documents.product])
  (:import [java.util UUID]))

(defn add-product-id [product-document]
  (map-utils/add-if-not-nil product-document :product/id (UUID/randomUUID)))

(defn save [product-document]
  (let [product-document-id (add-product-id product-document)]
    @(d/transact (datomic-config/get-db) [product-document-id])
    product-document-id))

(defn find-product-by-name [name]
  (d/q '[:find ?name ?slug ?price
         :in $ ?name
         :where
         [?e :product/name ?name]
         [?e :product/slug ?slug]
         [?e :product/price ?price]]
       (d/db (datomic-config/get-db)) name))

(defn find-by-bd-id [id]
  (as-> id $
        (d/q '[:find (pull ?e [:product/name :product/slug :product/price])
               :in $ ?id
               :where
               [?e :product/name]
               [(= ?e ?id)]]
             (d/db (datomic-config/get-db)) $)
        (ffirst $)
        (assoc $ :product/id id)))

(defn find-by-bd-id-v2 [product-id]
  (let [response (d/pull (datomic-config/get-db) '[*] product-id)]
    response))

;(d/pull db '[:usuario/data-de-nascimento :usuario/cidade] [:usuario/id ?usuario_id])

; ;for other attributes as `:db/unique      :db.unique/identity`
;(defn find-by-product-id [product-id]
;  (let [response (d/pull (d/db (datomic-config/get-db)) '[*] [:product/id product-id])]
;    (println response)
;    response))

;(defn find-by-product-id [product-id]
;  (let [response (d/pull (d/db (datomic-config/get-db))
;                         '[* {:product/category-id [:category/name]}]
;                         [:product/id product-id])]
;    (println response)
;    response))

(defn find-by-product-id [product-id]
  (let [response (d/pull (d/db (datomic-config/get-db))
                         '[*
                           {:product/category-id [:category/name]}]
                         [:product/id product-id])]
    (println response)
    response))

(defn find-by-product-id [id]
  (let [response (d/q '[:find (pull ?product [* {:product/category [*]}])
                        :in $ ?id
                        :where [?product :product/id ?id]] (d/db (datomic-config/get-db)) id)]
    (ffirst response)))

;; nao pode assoc
;(defn find-by-product-id [id]
;  (as-> id $
;        (d/q '[:find (pull ?e [:product/name :product/slug :product/price])
;               :in $ ?product-id
;               :where
;               [?e :product/id ?product-id]]
;             (d/db (datomic-config/get-db)) $)
;        (ffirst $)
;        (assoc $ :product/id id)))

;(defn find-by-product-id [product-id]
;  (let [db (datomic-config/get-db)
;        product-eid [:product/id product-id]
;        response (d/pull db '[*] product-eid)]
;    (println "Response:" response)
;    response))


(defn fn-add-property-executor [id property-key]
  (fn [value]
    [:db/add id property-key value]))

(defn build-update-adds [id product-document]
  (let [base-inputs [[:db/add id :product/id (documents.product/get-id product-document)]
                     [:db/add id :product/name (documents.product/get-name product-document)]
                     [:db/add id :product/slug (documents.product/get-slug product-document)]
                     [:db/add id :product/price (documents.product/get-price product-document)]
                     ]
        keywords    (map (fn-add-property-executor id :product/keyword) (documents.product/get-keywords product-document))
        inputs      (vec (concat base-inputs keywords))]
    inputs))

; IMPORTANT: Cardinality:many: add the different (dont remove others)
(defn update-v1 [product-document]
  (let [id (get product-document :product/id)]
    @(d/transact
      (datomic-config/get-db)
      (build-update-adds id product-document))
    product-document))

(defn update [product-document]
  @(d/transact (datomic-config/get-db) [product-document])
  product-document)


;[:find ?product-name ?category ?category-name
; :where
; [?p :product/id ?id]
; [?p :product/name ?product-name]
; [?p :product/category ?category]
; [?c :category/id ?category]
; [?c :category/name ?category-name]
; [(= ?id #uuid "9b280d6a-a34a-485b-b872-529640a4463b")]]


; find-all-product-by-category
