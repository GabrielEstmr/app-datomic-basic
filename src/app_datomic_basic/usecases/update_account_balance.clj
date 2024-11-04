(ns app-datomic-basic.usecases.update-account-balance
  (:require [app-datomic-basic.domain.transaction :as domain.transaction]))

(defn get-transaction-value [transaction]
  (if (domain.transaction/is-add-transaction transaction)
    (domain.transaction/get-value transaction)
    (.negate (domain.transaction/get-value transaction))))

(defn execute [account transaction]
  (assoc account :balance  (+ (-> account :balance) (get-transaction-value transaction))))
