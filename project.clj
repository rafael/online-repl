(defproject online-repl "1.0.0"
  :description "A compojure app that interprets clojure code"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [org.clojure/data.json "0.1.2"]
                 [ring "1.1.0"]
                 [ring/ring-jetty-adapter "1.1.0"]
                 [compojure "1.1.0"]
                 [ring-json-params "0.1.3"]
                 [clj-json "0.5.0"]
                 [clojail "0.5.0"]]
  :main online-repl.server)
