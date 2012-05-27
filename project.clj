(defproject online-repl "1.0.0"
  :description "A compojure app that interprets clojure code"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [ring "1.1.0"]
                 [ring/ring-jetty-adapter "1.1.0"]
                 [compojure "1.1.0"]
                 [clojail "0.5.0"]]
  :main online-repl.server)
