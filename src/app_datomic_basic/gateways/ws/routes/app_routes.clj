(ns app-datomic-basic.gateways.ws.routes.app-routes
  (:require [app-datomic-basic.gateways.ws.controllers.product-controller :as product-controller]
            [app-datomic-basic.gateways.ws.controllers.category-controller :as category-controller]
            [app-datomic-basic.gateways.ws.controllers.user-controller :as user-controller]
            [app-datomic-basic.gateways.ws.controllers.account-controller :as account-controller]
            [app-datomic-basic.gateways.ws.controllers.transaction-controller :as transaction-controller]
            [compojure.core :refer :all]
            [compojure.route :as route]))

(defroutes app-routes
           (POST "/api/v1/products" request (product-controller/create-product-handler request))
           (PUT "/api/v1/products/:id" [id :as request] (product-controller/update-product-handler id request))
           (GET "/api/v1/products/find-by-name/:name" [name] (product-controller/find-product-by-name-handler name))
           (GET "/api/v1/products/:id" [id] (product-controller/find-product-by-id-handler id))

           (POST "/api/v1/categories" request (category-controller/create-category-handler request))
           (GET "/api/v1/categories/:id" [id] (category-controller/find-category-by-id-handler id))

           (POST "/api/v1/users" request (user-controller/create-user-handler request))
           (GET "/api/v1/users/:id" [id] (user-controller/find-user-by-id-handler id))

           (POST "/api/v1/accounts" request (account-controller/create-account-handler request))
           (GET "/api/v1/accounts/:id" [id] (account-controller/find-account-by-id-handler id))

           (POST "/api/v1/transactions" request (transaction-controller/create-transaction-handler request))
           (GET "/api/v1/transactions/:id" [id] (transaction-controller/find-transaction-by-id-handler id))

           (route/not-found "Not Found"))
