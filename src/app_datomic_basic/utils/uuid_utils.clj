(ns app-datomic-basic.utils.uuid-utils
  (:import [java.util UUID]))

(defn string-to-uuid [uuid-string]
  (when uuid-string
    (UUID/fromString uuid-string)))
