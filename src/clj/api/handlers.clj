(ns api.handlers
    (:require [api.db :as db]
              [api.bigcommerce :as bc]
              [clojure.data.json :as json]))

(declare webhook-router)

(defn get-product
  [{:keys [parameters]}]
  (let [id (-> parameters :path :id)
        product (db/get-product db/config {:id id})]
     (if product
       {:status 200 :body product}
       {:status 404 :body {:error (format "no product found with id: %d" id)}})))

(defn get-products [_]
  (let [products (db/get-products db/config)]
    {:status 200 :body {:data products}}))

(defn update-product [req]
  (let [config (-> req :reitit.core/match :data :bigcommerce)
        params (-> req :parameters :body)
        res (webhook-router config params)]
       (if res
         {:status 200 :body {:message "successfully updated product info via API webhook request"}}
         {:status 500 :body {:error "error updating product data via bigcommerce API webhook"}})))

(defn webhook-router [config params]
    (let [id (-> params :data :id)
          inventory-value (-> params :data :inventory :value)
          scope (-> params :scope)]
       (case scope
           ("store/product/created"
             "store/product/updated")
             (let [{:keys [status body error]} (bc/fetch-product config id)]
                (when (and (not error) (= status 200))
                  (let [payload (json/read-str body :key-fn keyword)]
                   (db/create-product! db/config (db/product-insert-params (:data payload))))))
           "store/product/deleted"
             (db/delete-product! db/config {:id id})
           "store/product/inventory/updated"
             (db/update-inventory-level! db/config {:id id :inventory_level inventory-value})
           nil)))
