(ns api.handlers
    (:require [api.db :as db]))

;; TODO: use (:id path-params) as input to query
(defn get-product
  [req]
  (let [product (db/get-product db/config {:id 0})]
     {:status 200 :body product}))

(defn get-products [_]
  (let [products (db/get-products db/config)]
       {:status 200 :body products}))
