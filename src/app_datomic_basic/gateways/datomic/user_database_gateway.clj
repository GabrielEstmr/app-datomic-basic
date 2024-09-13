(ns app-datomic-basic.gateways.datomic.user-database-gateway
  (:require [app-datomic-basic.gateways.user-database-gateway :as user-database-gateway]
            [app-datomic-basic.gateways.datomic.repository.user-repository :as user-repository]
            [app-datomic-basic.gateways.datomic.documents.user :as user-document]
            [app-datomic-basic.utils.uuid-utils :as uuid-utils]))

(defn save-impl [user]
  (let [user-doc       (user-document/create-user-document user)
        saved-user-doc (user-repository/save user-doc)]
    (when saved-user-doc
      (user-document/to-domain saved-user-doc))))

(defn find-user-by-id-impl [id]
  (let [user-document (user-repository/find-by-id (uuid-utils/string-to-uuid id))]
    (when user-document
      (user-document/to-domain user-document))))

(defrecord UserDatabaseGatewayImpl []
  user-database-gateway/UserDatabaseGateway
  (save [_ user]
    (save-impl user))
  (findById [_ id]
    (find-user-by-id-impl id)))
