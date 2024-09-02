(ns app-datomic-basic.gateways.datomic.repository.category-repository
  (:require
   [datomic.api :as d]
   [app-datomic-basic.utils.map-utils :as map-utils]
   [app-datomic-basic.configs.datomic :as datomic-config])
  (:import [java.util UUID]))

(defn add-category-id [category-document]
  (map-utils/add-if-not-nil category-document :category/id (UUID/randomUUID)))

(defn save [category-document]
  (let [category-document-id (add-category-id category-document)]
    @(d/transact (datomic-config/get-db) [category-document-id])
    category-document-id))

(defn find-by-category-id [category-id]
  (let [response (d/pull (d/db (datomic-config/get-db)) '[*] [:category/id category-id])]
    (println response)
    response))
