(ns api.handlers
    (:require [api.db :as db]
              [api.bigcommerce :as bc]))

(defn get-product
  [{:keys [parameters]}]
  (let [product (db/get-product db/config {:id (-> parameters :path :id)})]
     {:status 200 :body product}))

(defn get-products [_]
  (let [products (db/get-products db/config)]
    {:status 200 :body products}))

(defn update-products
  [{:keys [parameters]}])

(defn update-product [req]
  ;; TODO: get product id from ProductWebhookPayload
  (let [config (get-in req [:reitit.core/match :data :config :bigcommerce])
        {:keys [status headers body error]} (bc/fetch-product config 112)]
       ;; TODO: update db with prod info from API instead of retuning product info here
       (if (= status 200)
         {:status status :body body}
         {:status 404 :body {:error error}})))
