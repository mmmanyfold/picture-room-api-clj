(ns api.routes
    (:require
      [reitit.coercion.schema]
      [api.handlers :as handlers]
      [schema.core :as s]))

;; obligatory health check
(def ping
  ["/ping"
   {:get (fn [_] {:status 200 :body "pong"})}])

(def products
  ["/products"
   ["" {:get handlers/get-products}]
   ["/:id"
    {:coercion reitit.coercion.schema/coercion
     :parameters {:path {:id s/Int}}
     :get handlers/get-product}]])

(def webhooks
  ["/webhooks"
   ["/product"
    {:parameters {:path {:id s/Int}}
     :post handlers/update-product}]])

(def api
  ["/api"
   products
   webhooks])
