(ns api.handlers
    (:require [api.db :as db]))

(defn get-product
  [{:keys [parameters]}]
  (let [product (db/get-product db/config {:id (-> parameters :path :id)})]
     {:status 200 :body product}))

(defn get-products [_]
  (let [products (db/get-products db/config)]
       {:status 200 :body products}))
