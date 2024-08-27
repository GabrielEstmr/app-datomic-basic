(ns app-datomic-basic.core
  (:require
            [app-datomic-basic.configs.datomic :as db]

            [clojure-general-ms.gateways.ws.routes.app-routes :as app-routes]
            [clojure-general-ms.gateways.ws.middlewares.custom-exception-handler :as custom-exception-handler]
            [compojure.core :refer :all]
            [clojure-general-ms.configs.mongo.mongo-config-doc :as mongo-config]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.json :refer [wrap-json-response]]

            )
  (:gen-class))


(def app
  (wrap-json-response (
                        custom-exception-handler/custom-exception-handler
                       app-routes/app-routes)))



(defn -main []
  (let [
        conn (db/open-connection)]

    (println "Datomic Connection" conn)))
