(ns api.bigcommerce
    (:require [org.httpkit.client :as http]))

(def includes "include=images,variants")

;; https://api.bigcommerce.com/stores/{store_hash}/v3/catalog/products/{productId}
(def endpoints
  {:products "https://api.bigcommerce.com/stores/%s/v3/catalog/products?limit=%d&page=%d&%s"
   :product  "https://api.bigcommerce.com/stores/%s/v3/catalog/products/%d?%s"})

;; TODO: recursively get all products
(defn fetch-products
  "fetch all products from bigcommerce API"
  [{:keys [store limit page]}]
  (let [endpoint (format (endpoints :products) store limit page includes)]
       endpoint))

(defn fetch-product
  "fetch product info from bigcommerce API by product id"
  [{:keys [client token store]} id]
  (let [opts {:headers {"X-Auth-Client" client
                        "X-Auth-Token"  token}}
        endpoint (format (endpoints :product) store id includes)]
    @(http/get endpoint opts)))
