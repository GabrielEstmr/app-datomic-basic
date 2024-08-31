(ns app-datomic-basic.utils.map-utils)

(defn get-when [map-value key]
  (when map-value
    (get map-value key nil)))

(defn add-if-not-nil [m key value]
  (when (some? value)
    (assoc m key value)))
