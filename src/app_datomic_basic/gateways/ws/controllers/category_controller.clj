(ns app-datomic-basic.gateways.ws.controllers.category-controller
  (:require [clojure.data.json :as json]
            [compojure.core :refer :all]
            [app-datomic-basic.gateways.ws.resources.category-request :as category-request]
            [app-datomic-basic.gateways.ws.resources.category-response :as category-response]
            [app-datomic-basic.usecases.beans.usecase-beans :as usecase-beans]
            [ring.util.response :as response]))

(defn create-category-handler [request]
  (let [usecaseCreateCategory (:usecaseCreateCategory (usecase-beans/get-beans))
        body                  (slurp (:body request))
        category-request      (json/read-str body :key-fn keyword)
        created-category      (usecaseCreateCategory (category-request/to-domain category-request))
        response-body         (json/write-str (category-response/create-category-response created-category))]
    (println "OK")
    (-> (response/response response-body)
        (response/status 201)
        (response/content-type "application/json"))))


(defn find-category-by-id-handler [id]
  (let [usecaseFindProductByIdProduct (:usecaseFindCategoryByIdCategory (usecase-beans/get-beans))
        product                       (usecaseFindProductByIdProduct id)
        response-body                 (json/write-str (category-response/create-category-response product))]
    (-> (response/response response-body)
        (response/status 200)
        (response/content-type "application/json"))))
