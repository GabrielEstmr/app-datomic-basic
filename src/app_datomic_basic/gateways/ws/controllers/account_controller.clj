(ns app-datomic-basic.gateways.ws.controllers.account-controller
  (:require [clojure.data.json :as json]
            [compojure.core :refer :all]
            [app-datomic-basic.gateways.ws.resources.account-request :as account-request]
            [app-datomic-basic.gateways.ws.resources.account-response :as account-response]
            [app-datomic-basic.usecases.beans.usecase-beans :as usecase-beans]
            [ring.util.response :as response]))

(defn create-account-handler [request]
  (let [usecaseCreateAccount (:usecaseCreateAccount (usecase-beans/get-beans))
        body                 (slurp (:body request))
        account-request         (json/read-str body :key-fn keyword)
        account-user         (usecaseCreateAccount (account-request/to-domain account-request))
        response-body        (json/write-str (account-response/create-account-response account-user))]
    (println "OK")
    (-> (response/response response-body)
        (response/status 201)
        (response/content-type "application/json"))))

(defn find-account-by-id-handler [id]
  (let [usecaseFindAccountById (:usecaseFindAccountById (usecase-beans/get-beans))
        account                (usecaseFindAccountById id)
        response-body          (json/write-str (account-response/create-account-response account))]
    (-> (response/response response-body)
        (response/status 200)
        (response/content-type "application/json"))))
