(ns app-datomic-basic.configs.datomic
  (:require [datomic.api :as d]
            [app-datomic-basic.gateways.datomic.documents.product :as product-document]))


(def db-uri "datomic:sql://?jdbc:postgresql://localhost:5432/my-datomic?user=datomic-user&password=unsafe")

;(defonce conn (d/connect db-uri))
;
;(defonce db (d/db conn))
;
;(defn apply-schemas []
;  @(d/transact conn product-document/schema))
;
;(defn open-connection []
;  (d/create-database db-uri)
;  conn)
;



;;(d/create-database db-uri)
;
(def conn (d/connect db-uri))
;;
;;@(d/transact conn [{:db/doc "Hello world"}])
;;
;@(d/transact conn [{:db/ident       :movie/title
;                    :db/valueType   :db.type/string
;                    :db/cardinality :db.cardinality/one
;                    :db/doc         "The title of the movie"}
;
;                   {:db/ident       :movie/genre
;                    :db/valueType   :db.type/string
;                    :db/cardinality :db.cardinality/one
;                    :db/doc         "The genre of the movie"}
;
;                   {:db/ident       :movie/release-year
;                    :db/valueType   :db.type/long
;                    :db/cardinality :db.cardinality/one
;                    :db/doc         "The year the movie was released in theaters"}])
;;
;@(d/transact conn [{:movie/title        "The Goonies"
;                    :movie/genre        "action/adventure"
;                    :movie/release-year 1985}
;                   {:movie/title        "Commando"
;                    :movie/genre        "action/adventure"
;                    :movie/release-year 1985}
;                   {:movie/title        "Repo Man"
;                    :movie/genre        "punk dystopia"
;                    :movie/release-year 1984}])


(defn apply-schemas []
  @(d/transact (d/connect db-uri) [{:db/ident       :product/name
                                    :db/valueType   :db.type/string
                                    :db/cardinality :db.cardinality/one
                                    :db/unique      :db.unique/identity
                                    :db/doc         "The name of the product"}
                                   {:db/ident       :product/slug
                                    :db/valueType   :db.type/string
                                    :db/cardinality :db.cardinality/one
                                    :db/doc         "A description of the product"}
                                   {:db/ident       :product/price
                                    :db/valueType   :db.type/long
                                    :db/cardinality :db.cardinality/one
                                    :db/doc         "The price of the product"}]))

;(def db (d/db conn))
;
;(d/q '[:find ?e ?movie-title
;       :where [?e :movie/title ?movie-title]]
;     db)
