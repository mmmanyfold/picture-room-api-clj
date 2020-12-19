(ns api.core-test
    (:require
      [clojure.test :refer :all]
      [ring.mock.request :as mock]
      [api.handlers :as handlers]
      [api.core :as api]
      [clj-time.core :as time]
      [api.core :refer :all] [api.db :as db]))

(declare test-product)

(defn db-setup [f]
  (db/create-products-table! db/config)
  (f)
  (db/drop-products-table! db/config))

(use-fixtures :once db-setup)

(deftest products-api

   (testing "test product insert"
      (let [product (db/product-insert-params test-product)
            res (db/create-product! db/config product)]
           (= 1 (:id res))))

   (testing "get products from products table"
      (let [products (db/get-products db/config)]
           (= (count products) 1)))

   (testing "update inventory level"
      (let [product (db/update-inventory-level! db/config {:id 1 :inventory_level 99})
            product (db/get-product db/config {:id 1})]
           (= (:inventory_level product) 99)))

   (testing "delete products from products table"
      (let [res (db/delete-product! db/config {:id 1})]
         (= res 1)))

   (testing "GET all products"
      (let [product (db/product-insert-params test-product)
            _ (db/create-product! db/config product)
            resp (handlers/get-products (mock/request :get "/api/products"))]
           (is (= (:status resp) 200))
           (is (= (-> resp :body :data first :id) 1))
           (is (= (-> resp :body :data first :reviews_rating_sum) 10)))))

(deftest product-api-requests)

(def test-product
  {:id                         1
   :created_at                 (time/now)
   :updated_at                 (time/now)
   :deleted_at                 (time/now)
   :name                       "foobar"
   :type                       "foobar"
   :sku                        "foobar"
   :description                "foobar"
   :weight                     1.0
   :width                      1.0
   :depth                      1.0
   :height                     1.0
   :price                      1.0
   :cost_price                 1.0
   :retail_price               1.0
   :sale_price                 1.0
   :map_price                  1.0
   :tax_class_id               1
   :product_tax_code           "foobar"
   :calculated_price           0.0
   :categories                 [1, 2, 3]
   :brand_id                   0
   :option_set_display         "foobar"
   :inventory_level            1
   :inventory_warning_level    1
   :inventory_tracking         "foobar"
   :reviews_rating_sum         10
   :reviews_count              100
   :total_sold                 100
   :fixed_cost_shipping_price  1.0
   :is_free_shipping           true
   :is_visible                 true
   :is_featured                true
   :warranty                   "foobar"
   :bin_picking_number         "foobar"
   :layout_file                "foobar"
   :upc                        "foobar"
   :mpn                        "foobar"
   :gtin                       "foobar"
   :search_keywords            "foobar"
   :availability               "foobar"
   :availability_description   "foobar"
   :gift_wrapping_options_type "foobar"
   :sort_order                 0
   :condition                  "foobar"
   :is_condition_shown         false
   :order_quantity_minimum     1
   :order_quantity_maximum     1
   :page_title                 "foobar"
   :meta_description           "foobar"
   :date_created               "foobar"
   :date_modified              "foobar"
   :view_count                 0
   :preorder_message           "foobar"
   :is_preorder_only           false
   :is_price_hidden            false
   :price_hidden_label         "foobar"
   :custom_url                 {:a 1}})
