(ns api.routes
    (:require
      [reitit.coercion.schema]
      [api.handlers :as handlers]
      [schema.core :as s]
      [api.db :as db]))

;; obligatory health check
(def ping
  ["/ping"
   {:get (fn [_] {:status 200 :body "pong"})}])

(def product
  ["/product"
   ["/:id"
    {:coercion reitit.coercion.schema/coercion
     :parameters {:path {:id s/Int}}
     :get handlers/get-product}]])

(def products
  ["/products"
   {:get handlers/get-products}])

(def api
  ["/api"
   product
   products])
