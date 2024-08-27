(ns app-datomic-basic.configs.db-test
  (:require [clojure.java.jdbc :as jdbc]))

(def db-spec {:classname "org.postgresql.Driver"
              :subprotocol "postgresql"
              :subname "//localhost:5432/datomic"
              :user "datomic"
              :password "datomic"})

(defn test-connection []
  (try
    (jdbc/query db-spec ["SELECT 1"])
    (println "Connection successful")
    (catch Exception e
      (println "Connection failed: " (.getMessage e)))))
