(ns api.core
  (:require [org.httpkit.server :refer [run-server]]
            [reitit.ring :as ring]))

(defonce server (atom nil))

(defn stop-server []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))

(def app
  (ring/ring-handler
    (ring/router
      [;; obligatory health check
       ["/ping" {:get (fn [_] {:status 200 :body "pong"})}]])))

(defn -main []
  (println "server started")
  (reset! server (run-server app {:port 4000})))

(comment
  (-main))
