(ns app-datomic-basic.gateways.ws.controllers.transaction-controller
  (:require [clojure.data.json :as json]
            [compojure.core :refer :all]
            [app-datomic-basic.gateways.ws.resources.transaction-request :as transaction-request]
            [app-datomic-basic.gateways.ws.resources.transaction-response :as transaction-response]
            [app-datomic-basic.usecases.beans.usecase-beans :as usecase-beans]
            [ring.util.response :as response]))

(defn create-transaction-handler [request]
  (let [usecaseCreateTransaction (:usecaseCreateTransaction (usecase-beans/get-beans))
        body                     (slurp (:body request))
        transaction-request      (json/read-str body :key-fn keyword)
        created-transaction      (usecaseCreateTransaction (transaction-request/to-domain transaction-request))
        response-body            (json/write-str (transaction-response/create-transaction-response created-transaction))]
    (println "OK")
    (-> (response/response response-body)
        (response/status 201)
        (response/content-type "application/json"))))

(defn find-transaction-by-id-handler [id]
  (let [usecaseFindTransactionById (:usecaseFindTransactionById (usecase-beans/get-beans))
        protransaction             (usecaseFindTransactionById id)
        response-body              (json/write-str (transaction-response/create-transaction-response protransaction))]
    (-> (response/response response-body)
        (response/status 200)
        (response/content-type "application/json"))))
