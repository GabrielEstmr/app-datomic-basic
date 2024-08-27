(ns app-datomic-basic.core
  (:require
            [app-datomic-basic.configs.datomic :as db]
            [app-datomic-basic.configs.db-test :as test])
  (:gen-class))

(defn -main []
  (let [
        conn "(db/init-db)"]
    (db/test-datomic)
    (println "Application started.")))
