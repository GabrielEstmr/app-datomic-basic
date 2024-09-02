(ns app-datomic-basic.gateways.ws.routes.app-routes
  (:require [app-datomic-basic.gateways.ws.controllers.product-controller :as product-controller]
            [app-datomic-basic.gateways.ws.controllers.category-controller :as category-controller]
            [compojure.core :refer :all]
            [compojure.route :as route]))

(defroutes app-routes
           (POST "/api/v1/products" request (product-controller/create-product-handler request))
           (PUT "/api/v1/products/:id" [id :as request] (product-controller/update-product-handler id request))
           (GET "/api/v1/products/find-by-name/:name" [name] (product-controller/find-product-by-name-handler name))
           (GET "/api/v1/products/:id" [id] (product-controller/find-product-by-id-handler id))

           (POST "/api/v1/categories" request (category-controller/create-category-handler request))
           (GET "/api/v1/categories/:id" [id] (category-controller/find-category-by-id-handler id))

           (route/not-found "Not Found"))
