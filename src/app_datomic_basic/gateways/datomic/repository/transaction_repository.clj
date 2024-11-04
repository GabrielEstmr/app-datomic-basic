(ns app-datomic-basic.gateways.datomic.repository.transaction-repository
  (:require
   [datomic.api :as d]
   [app-datomic-basic.configs.datomic :as datomic-config]
   [app-datomic-basic.gateways.datomic.documents.account :as documents.account]
   [app-datomic-basic.utils.uuid-utils :as uuid-utils]))

;(defn save [transaction-document
;            account-document]
;  (let [transaction-document-id (uuid-utils/assoc-uuid transaction-document :transaction/id)]
;    @(d/transact (datomic-config/get-db) [transaction-document-id
;                                          [:db/cas [:]]])
;    (println transaction-document-id)
;    transaction-document-id))

(defn find-by-id [id]
  (let [response (d/q '[:find (pull ?transaction [* {:transaction/user [*]} {:transaction/account [*]}])
                        :in $ ?id
                        :where [?transaction :transaction/id ?id]] (d/db (datomic-config/get-db)) id)]
    (ffirst response)))

(defn save
  [transaction-document
   account-document]
  (let [transaction-document-id (uuid-utils/assoc-uuid transaction-document :transaction/id)
        pulled-account          (ffirst
                                 (d/q '[:find (pull ?entity [* {:account/user [*]}])
                                        :in $ ?id
                                        :where [?entity :account/id ?id]]
                                      (d/db (datomic-config/get-db))
                                      (documents.account/get-id account-document)))
        account-id              (:db/id pulled-account)     ;; Extracting the actual `:db/id`
        tx-data                 [transaction-document-id
                                 {:db/id           account-id
                                  :account/balance (documents.account/get-balance account-document)}
                                 ;; CAS as a separate vector
                                 [:db/cas account-id
                                  :account/version
                                  (documents.account/get-current-version account-document)
                                  (documents.account/get-next-version account-document)]]]
    @(d/transact (datomic-config/get-db) tx-data)
    transaction-document-id))

;(defn create-transaction
;  [conn account-id amount]
;  (let [db              (d/db conn)
;        account         (d/entity db [:account/id account-id])
;        current-balance (:account/balance account)
;        account-db-id   (:db/id account)
;        new-balance     (+ current-balance amount)
;        transaction-id  (d/tempid :db.part/user)
;
;        ;; Prepare the data with CAS on the current balance
;        tx-data         [{:db/id           account-db-id
;                          :account/balance current-balance  ;; CAS - compare current balance
;                          :db/cas          [:account/balance current-balance new-balance]} ;; If CAS succeeds, new-balance is set
;                         {:db/id                  transaction-id ;; Create the transaction entity
;                          :transaction/id         (java.util.UUID/randomUUID)
;                          :transaction/account-id account-id
;                          :transaction/amount     amount
;                          :transaction/timestamp  (java.util.Date.)}]]
;
;    ;; Execute the transaction atomically with CAS
;    @(d/transact conn tx-data)))
