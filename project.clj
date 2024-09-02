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
                 [ring/ring-core "1.9.4"]
                 [ring/ring-jetty-adapter "1.9.4"]
                 [ring/ring-defaults "0.3.3"]
                 [ring/ring-json "0.5.0"]
                 [compojure "1.6.2"]
                 [ch.qos.logback/logback-classic "1.2.3"]
                 [http-kit "2.3.0"]
                 [org.clojure/data.json "2.4.0"]
                 [org.clojure/tools.logging "1.2.4"]
                 [ring-cors "0.1.13"]]
  :plugins [[lein-cloverage "1.2.4"]]
  :java-source-paths ["src/app_datomic_basic/java"]
  :main ^:skip-aot app-datomic-basic.core
  :target-path "target/%s"
  :profiles {:uberjar   {:aot      :all
                         :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}
             :cloverage {
                         :cloverage {:ns-exclude-regex "^clojure-general-ms\\.(gateways|configs)\\..*"}}}
  :prep-tasks ["javac" "compile"])
