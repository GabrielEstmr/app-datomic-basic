(ns app-datomic-basic.gateways.ws.resources.user-request
  (:require [app-datomic-basic.domain.user :as user]))

(defn to-domain [user-request]
  (let [{:keys [name]} user-request]
    (user/create-new-user nil name)))
