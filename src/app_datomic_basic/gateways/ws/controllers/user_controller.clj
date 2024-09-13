(ns app-datomic-basic.gateways.ws.controllers.user-controller
  (:require [clojure.data.json :as json]
            [compojure.core :refer :all]
            [app-datomic-basic.gateways.ws.resources.user-request :as user-request]
            [app-datomic-basic.gateways.ws.resources.user-response :as user-response]
            [app-datomic-basic.usecases.beans.usecase-beans :as usecase-beans]
            [ring.util.response :as response]))

(defn create-user-handler [request]
  (let [usecaseCreateUser (:usecaseCreateUser (usecase-beans/get-beans))
        body              (slurp (:body request))
        user-request      (json/read-str body :key-fn keyword)
        created-user      (usecaseCreateUser (user-request/to-domain user-request))
        response-body     (json/write-str (user-response/create-user-response created-user))]
    (println "OK")
    (-> (response/response response-body)
        (response/status 201)
        (response/content-type "application/json"))))

(defn find-user-by-id-handler [id]
  (let [usecaseFindUserById (:usecaseFindUserById (usecase-beans/get-beans))
        product             (usecaseFindUserById id)
        response-body       (json/write-str (user-response/create-user-response product))]
    (-> (response/response response-body)
        (response/status 200)
        (response/content-type "application/json"))))
