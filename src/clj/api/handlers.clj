(ns api.handlers
    (:require [api.db :as db]
              [api.bigcommerce :as bc]))

(defn get-product
  [{:keys [parameters]}]
  (let [id (-> parameters :path :id)
        product (db/get-product db/config {:id id})]
     (if product
       {:status 200 :body product}
       {:status 404 :body {:error (format "no product found with id: %d" id)}})))

(defn get-products [_]
  (let [products (db/get-products db/config)]
    {:status 200 :body products}))

(defn get-products-test [req]
  (let [config (get-in req [:reitit.core/match :data :config :bigcommerce])]
       ;; TODO: update db with prod info from API instead of retuning product info here
       (if-let [products (bc/fetch-products config 1 400)]
         {:status 200 :body products}
         {:status 500 :body {:error "error failed to get paginated set of products from bigcommerce API"}})))

(defn update-product [req]
  ;; TODO: get product id from ProductWebhookPayload
  (let [config (get-in req [:reitit.core/match :data :config :bigcommerce])
        {:keys [status body error]} (bc/fetch-product config 112)]
       ;; TODO: update db with prod info from API instead of retuning product info here
       (if (= status 200)
         {:status status :body body}
         {:status 404 :body {:error error}})))
