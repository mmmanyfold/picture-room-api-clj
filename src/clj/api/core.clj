(ns api.core
    (:require
      [org.httpkit.server :refer [run-server]]
      [reitit.ring :as ring]
      [aero.core :as aero]
      [clojure.java.io :as io]
      [muuntaja.core :as m]
      [reitit.ring.middleware.exception :as exception-handler]
      [reitit.ring.middleware.muuntaja :refer [format-negotiate-middleware
                                               format-request-middleware
                                               format-response-middleware]]))

(declare -main)

(defonce server (atom nil))

(defn config []
  (aero/read-config "config.edn" {:profile :dev}))

(defn stop-server []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))

(defn restart-server []
  (stop-server)
  (-main))

(def app
  (ring/ring-handler
    (ring/router
      [;; obligatory health check
       ["/ping" {:get (fn [_] {:status 200 :body "pong"})}]
       ["/api" {:get (fn [_] {:status 200 :body {:hello "world"}})}]]
      {:data {:muuntaja   m/instance
              :middleware [format-negotiate-middleware
                           format-response-middleware
                           exception-handler/exception-middleware
                           format-request-middleware]}})
    (ring/routes
      (ring/redirect-trailing-slash-handler)
      (ring/create-default-handler
        {:not-found (constantly {:status 404 :body "route not found"})}))))

(defn -main []
  (let [c (config)]
     (println (format "server listening on port: %d" (:port c)))
     (reset! server (run-server app {:port (:port c)}))))

(comment
  (-main)
  (app {:uri "/ping" :request-method :get}))
