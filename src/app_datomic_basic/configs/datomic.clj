(ns app-datomic-basic.configs.datomic
  (:require [datomic.api :as d]))


(def db-uri "datomic:sql://?jdbc:postgresql://localhost:5432/my-datomic?user=datomic-user&password=unsafe")

(d/create-database db-uri)

(def conn (d/connect db-uri))

@(d/transact conn [{:db/doc "Hello world"}])

@(d/transact conn [{:db/ident :movie/title
                    :db/valueType :db.type/string
                    :db/cardinality :db.cardinality/one
                    :db/doc "The title of the movie"}

                   {:db/ident :movie/genre
                    :db/valueType :db.type/string
                    :db/cardinality :db.cardinality/one
                    :db/doc "The genre of the movie"}

                   {:db/ident :movie/release-year
                    :db/valueType :db.type/long
                    :db/cardinality :db.cardinality/one
                    :db/doc "The year the movie was released in theaters"}])

@(d/transact conn [{:movie/title "The Goonies"
                    :movie/genre "action/adventure"
                    :movie/release-year 1985}
                   {:movie/title "Commando"
                    :movie/genre "action/adventure"
                    :movie/release-year 1985}
                   {:movie/title "Repo Man"
                    :movie/genre "punk dystopia"
                    :movie/release-year 1984}])

(def db (d/db conn))



;(defn test-datomic []
;  (d/q '[:find ?e ?movie-title
;         :where [?e :movie/title ?movie-title]]
;       db))


(def conn (d/connect db-uri))

(def db (d/db conn))




(defn test-datomic []
  (d/q '[:find ?id ?type ?gender
         :in $ ?name
         :where
         [?e :artist/name ?name]
         [?e :artist/gid ?id]
         [?e :artist/type ?teid]
         [?teid :db/ident ?type]
         [?e :artist/gender ?geid]
         [?geid :db/ident ?gender]]
       db
       "Jimi Hendrix"))
