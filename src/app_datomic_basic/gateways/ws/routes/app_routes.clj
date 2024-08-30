(ns app-datomic-basic.gateways.ws.routes.app-routes
  (:require [app-datomic-basic.gateways.ws.controllers.product-controller :as product-controller]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :as response]))

(defroutes app-routes
           (POST "/api/v1/products" request (product-controller/create-product-handler request))
           (GET "/api/v1/products/:id" [id] (product-controller/find-product-by-name-handler id))
           (route/not-found "Not Found"))
