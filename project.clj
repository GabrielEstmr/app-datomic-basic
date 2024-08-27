(defproject app-datomic-basic "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [com.datomic/peer "1.0.7187"]
                 [org.slf4j/slf4j-simple "1.7.32"]
                 [org.postgresql/postgresql "42.7.4"]
                 [com.bhauman/rebel-readline "0.1.4"]
                 ;[org.clojure/java.jdbc "0.7.12"]

                 ;[com.datomic/datomic-pro "1.7.32"] ;; Adjust version as needed
                 ;[com.datomic/client-pro "1.0.83"] ;; For client API
                 ]
  :main ^:skip-aot app-datomic-basic.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
