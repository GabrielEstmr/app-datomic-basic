(ns app-datomic-basic.configs.datomic
  (:require [datomic.api :as d]
            [app-datomic-basic.gateways.datomic.documents.product :as product-document]))


(def db-uri "datomic:sql://?jdbc:postgresql://localhost:5432/my-datomic?user=datomic-user&password=unsafe")

(defonce conn (d/connect db-uri))

(defonce db (d/db conn))


(def apply-schemas []
  @(d/transact conn product-document/schema))


(defn open-connection []
  (d/create-database db-uri)
  conn)




; Produtos
; ?id
; nome String 1 ==> Computador Novo
; slug String 1 ==> /computador_novo
; preço ponto flutuante 1 ==> 3500.10


; id_entidade atributo valor
; 15 :nome Computador Novo
; 15 :slug /computador_novo
; 15 :preço 3500.10

; 17 :nome Telefone Caro
; 17 :slug /telefone
; 17 :preço 8888.88
