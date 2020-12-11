(ns api.core
  (:require [org.httpkit.server :refer [run-server]]
            [reitit.ring :as ring]
            [aero.core :as aero]
            [clojure.java.io :as io]))

(defonce server (atom nil))

(defn config []
  (aero/read-config "config.edn" {:profile :dev}))

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
  (let [c (config)]
     (println (format "server listening on port: %d" (:port c)))
     (reset! server (run-server app {:port (:port c)}))))

(comment
  (-main)
  (app {:uri "/ping" :request-method :get}))
