(ns app-datomic-basic.gateways.ws.controllers.product-controller
  (:require [clojure.data.json :as json]
            [compojure.core :refer :all]
            [app-datomic-basic.gateways.ws.resources.product-request :as product-request]
            [app-datomic-basic.gateways.ws.resources.product-response :as product-response]
            [app-datomic-basic.usecases.beans.usecase-beans :as usecase-beans]
            [ring.util.response :as response]))

(defn create-product-handler [request]
  (let [usecaseCreateProduct (:usecaseCreateProduct (usecase-beans/get-beans))
        body                 (slurp (:body request))
        product-request      (json/read-str body :key-fn keyword)]
    (let [created-product (usecaseCreateProduct (product-request/to-domain product-request))
          response-body   (json/write-str (product-response/create-product-response created-product))]
      (println "OK")
      (-> (response/response response-body)
          (response/status 201)
          (response/content-type "application/json")))))
