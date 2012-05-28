(ns online-repl.server
  (:use  [compojure.core :only  (GET PUT POST defroutes)]
         [clojure.data.json :only  (read-json)]
         [clojail core]
         ring.util.response
         ring.adapter.jetty
         ring.middleware.json-params  
         clojure.pprint)
  (:require (compojure handler route)
            [clj-json.core :as json])
  (:import java.util.concurrent.ExecutionException))



(def tester #{'alter-var-root java.lang.Thread}) ; Create a blacklist.
(def sb  (sandbox #{}))

(defn commify-seq  [items]
  (apply str  (interpose \, items)))

(defn json-response  [data &  [status]]
  {:status  (or status 200)
      :headers  {"Content-Type" "application/json"}
      :body  (json/generate-string data)})

(defn parse-string [code]
  (let  [expr  (try  (read-string code)  (catch java.lang.RuntimeException e '()))]
    (try
      (let [result (sb expr)] 
        (cond (seq? result)
          (commify-seq result) 
        :else (str  result)))
      (catch java.util.concurrent.ExecutionException e)))) 

(defroutes app*
  (GET "/" request "Post something to this URL with a parameter `code` and I will try to evalaute it")
  (POST "/"  {body :body} 
              (let [code (:code (read-json (slurp body)))] 
                  (if  (nil? code)
                    (json-response {:error "No `code` parameter provided"} 400) 
                    (let [result (parse-string code)] 
                      (json-response {:result result}))))))

(def app (compojure.handler/api app*)) 

(defn -main [& args]
  (let [port (Integer/parseInt (get (System/getenv) "PORT" "8080"))]
      (run-jetty app {:port port})))
