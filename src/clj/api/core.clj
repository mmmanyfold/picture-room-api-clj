(ns api.core
    (:require
      [api.cron]
      [api.routes :as routes]
      [reitit.ring :as ring]
      [aero.core :as aero]
      [muuntaja.core :as m]
      [reitit.ring.coercion :as rrc]
      [reitit.coercion.spec]
      [reitit.ring.middleware.exception :as exm]
      [reitit.ring.middleware.parameters :as parm]
      [reitit.ring.middleware.muuntaja :as muu]
      [ring.adapter.jetty :as jetty]
      [integrant.core :as ig]))

(def system-config
  {:api/jetty   {:port    4000
                 :join?   false
                 :handler (ig/ref :api/handler)}
   :api/handler {}})

(defmethod ig/init-key :api/jetty [_ {:keys [port join? handler]}]
   (println (format "server listening on port: %d" port))
   (jetty/run-jetty handler {:port port :join? join?}))

;; side-effect-al
(defmethod ig/halt-key! :api/jetty [_ server]
   (.stop server))

(defmethod ig/init-key :api/handler [_ _]
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

(defn config [profile]
  (aero/read-config "config.edn" {:profile profile}))

(defn -main []
  (let [c (config :dev)]
  ;; TODO: bootstrap cronjob with integrant
  (api.cron/start c))
  (ig/init system-config))
