(ns app-datomic-basic.gateways.ws.controllers.product-controller
  (:require [clojure.data.json :as json]
            [compojure.core :refer :all]
            [app-datomic-basic.gateways.ws.resources.product-request :as product-request]
            [app-datomic-basic.gateways.ws.resources.update-product-request :as update-product-request]
            [app-datomic-basic.gateways.ws.resources.product-response :as product-response]
            [app-datomic-basic.usecases.beans.usecase-beans :as usecase-beans]
            [ring.util.response :as response]))

(defn create-product-handler [request]
  (let [usecaseCreateProduct (:usecaseCreateProduct (usecase-beans/get-beans))
        body                 (slurp (:body request))
        product-request      (json/read-str body :key-fn keyword)
        created-product      (usecaseCreateProduct (product-request/to-domain product-request))
        response-body        (json/write-str (product-response/create-product-response created-product))]
    (println "OK")
    (-> (response/response response-body)
        (response/status 201)
        (response/content-type "application/json"))))

(defn update-product-handler [id request]
  (let [usecaseUpdateProduct (:usecaseUpdateProduct (usecase-beans/get-beans))
        body                 (slurp (:body request))

        response-body  (as-> body $
            (json/read-str $ :key-fn keyword)
              (assoc $ :id id)
              (update-product-request/to-domain $)
              (usecaseUpdateProduct $)
              (product-response/create-product-response $)
              (json/write-str $))

        ;product-request-base (json/read-str body :key-fn keyword)
        ;product-request      (assoc product-request-base :id id)
        ;updated-product      (usecaseCreateProduct (update-product-request/to-domain product-request))
        ;response-body        (json/write-str (product-response/create-product-response updated-product))
        ]
    (println "OK")
    (-> (response/response response-body)
        (response/status 201)
        (response/content-type "application/json"))))

(defn find-product-by-name-handler [name]
  (let [usecaseFindProductByNameProduct (:usecaseFindProductByNameProduct (usecase-beans/get-beans))
        product                         (usecaseFindProductByNameProduct name)
        response-body                   (json/write-str (product-response/create-product-response product))]
    (-> (response/response response-body)
        (response/status 200)
        (response/content-type "application/json"))))

(defn find-product-by-id-handler [id]
  (let [usecaseFindProductByIdProduct (:usecaseFindProductByIdProduct (usecase-beans/get-beans))
        product                         (usecaseFindProductByIdProduct (Long/parseLong id))
        response-body                   (json/write-str (product-response/create-product-response product))]
    (-> (response/response response-body)
        (response/status 200)
        (response/content-type "application/json"))))
