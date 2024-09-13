(ns app-datomic-basic.utils.uuid-utils
  (:require [app-datomic-basic.utils.map-utils :as map-utils])
  (:import [java.util UUID]))

(defn string-to-uuid [uuid-string]
  (when uuid-string
    (UUID/fromString uuid-string)))

(defn assoc-uuid [values key]
  (map-utils/add-if-not-nil values key (UUID/randomUUID)))
