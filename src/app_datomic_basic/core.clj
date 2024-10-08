(ns app-datomic-basic.core
  (:require
   [app-datomic-basic.configs.datomic :as datomic-config]
   [app-datomic-basic.gateways.ws.routes.app-routes :as app-routes]
   [app-datomic-basic.gateways.ws.middlewares.custom-exception-handler :as custom-exception-handler]
   [compojure.core :refer :all]
   [ring.adapter.jetty :refer [run-jetty]]
   [ring.middleware.json :refer [wrap-json-response]]

   )
  (:gen-class))


(def app
  (wrap-json-response (
                        custom-exception-handler/custom-exception-handler
                       app-routes/app-routes)))

(defn -main [& args]
  (try
    (datomic-config/init-datomic)
    (run-jetty app {:port 8081 :join? false})
    (catch Exception e
      (println (.getMessage e)))))
