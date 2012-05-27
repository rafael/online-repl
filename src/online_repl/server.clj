(ns online-repl.server 
  (:use  [compojure.core :only  (GET PUT POST defroutes)]
         ring.util.response
         ring.adapter.jetty)
  (:require  (compojure handler route)
            [ring.util.response :as response]))

(defroutes app*
  (GET "/" request "Welcome!"))

(def app (compojure.handler/api app*))

(defn -main [& args]
  (let [port (Integer/parseInt (get (System/getenv) "PORT" "8080"))]
      (run-jetty app {:port port})))


