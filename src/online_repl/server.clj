(ns online-repl.server
  (:use  [compojure.core :only  (GET PUT POST defroutes)]
         [clojail core]
         ring.util.response
         ring.adapter.jetty)
  (:require  (compojure handler route)
            [ring.util.response :as response])
  (:import java.util.concurrent.ExecutionException))



(def tester #{'alter-var-root java.lang.Thread}) ; Create a blacklist.
(def sb  (sandbox #{}))

(defn parse-string [code]
  (let  [expr  (try  (read-string code)  (catch java.lang.RuntimeException e '()))]
    (try
      (str  (sb expr))
        (catch java.util.concurrent.ExecutionException e))))

(defroutes app*
  (GET "/" request "Post something to this URL with a parameter `code` and I will try to evalaute it")
  (POST "/"  [code]  (if  (empty? code)
                    {:status 400 :body "No `code` parameter provided"}
                    (let [result (parse-string code)] 
                    {:status 200 :body result}))))

(def app (compojure.handler/site app*))

(defn -main [& args]
  (let [port (Integer/parseInt (get (System/getenv) "PORT" "8080"))]
      (run-jetty app {:port port})))


