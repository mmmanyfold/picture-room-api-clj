(ns api.core
    (:require
      [api.routes :as routes]
      [org.httpkit.server :refer [run-server]]
      [reitit.ring :as ring]
      [aero.core :as aero]
      [clojure.java.io :as io]
      [muuntaja.core :as m]
      [reitit.ring.coercion :as rrc]
      [reitit.coercion.spec]
      [reitit.coercion :as coercion]
      [reitit.ring.middleware.exception :as exm]
      [reitit.ring.middleware.parameters :as parm]
      [reitit.ring.middleware.muuntaja :as muu]))

(declare -main)

(defonce server (atom nil))

(defn config [profile]
  (aero/read-config "config.edn" {:profile profile}))

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
      [routes/ping
       routes/api]
      {:data {:muuntaja   m/instance
              :middleware [parm/parameters-middleware
                           muu/format-negotiate-middleware
                           muu/format-response-middleware
                           exm/exception-middleware
                           muu/format-request-middleware
                           rrc/coerce-exceptions-middleware
                           rrc/coerce-request-middleware
                           rrc/coerce-response-middleware]}})
    (ring/routes
      (ring/redirect-trailing-slash-handler)
      (ring/create-default-handler
        {:not-found (constantly {:status 404 :body "route not found"})}))))

(defn -main []
  (let [c (config :dev)]
     (println (format "server listening on port: %d" (:port c)))
     (reset! server (run-server app {:port (:port c)}))))

(comment
  (-main)
  (app {:uri "/ping" :request-method :get}))
