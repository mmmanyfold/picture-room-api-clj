(ns api.bigcommerce
    (:require [org.httpkit.client :as http]
              [clojure.data.json :as json]))

(def includes "include=images,variants")

;; https://api.bigcommerce.com/stores/{store_hash}/v3/catalog/products/{productId}
(def endpoints
  {:products "https://api.bigcommerce.com/stores/%s/v3/catalog/products?limit=%d&page=%d&%s"
   :product  "https://api.bigcommerce.com/stores/%s/v3/catalog/products/%d?%s"})

(defn with-auth-headers [client token]
  {"X-Auth-Client" client
   "X-Auth-Token"  token})

(defn fetch-products
  "recursive call to fetch all products from bigcommerce API"
  [{:keys [client token store] :as config} page limit & [products]]
  (let [opts {:headers (with-auth-headers client token)}
        endpoint (format (endpoints :products) store limit page includes)
        {:keys [body error status]} @(http/get endpoint opts)]
       (if (and (not error) (= status 200))
         (let [payload (json/read-str body :key-fn keyword)
               {:keys [current_page total_pages]} (-> payload :meta :pagination)
               next-products (-> products
                                 (update :data concat (:data payload))
                                 (update :meta merge (:meta payload)))]
              (if (not= current_page total_pages)
                (fetch-products config (+ 1 page) limit next-products)
                next-products))
         error)))

(defn fetch-product
  "fetch product info from bigcommerce API by product id"
  [{:keys [client token store]} id]
  (let [opts {:headers (with-auth-headers client token)}
        endpoint (format (endpoints :product) store id includes)]
    @(http/get endpoint opts)))
