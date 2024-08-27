(ns app-datomic-basic.core
  (:require
            [app-datomic-basic.configs.datomic :as db])
  (:gen-class))

(defn -main []
  (let [
        conn (db/open-connection)]

    (println "Datomic Connection" conn)))
