(ns api.db
    (:require
      [api.jdbc]
      [hugsql.core :as hugsql]
      [clj-time.core :as time]))

(def config
  {:classname   "org.postgresql.Driver"
   :subprotocol "postgres"
   :subname     "//10.254.254.254:5432/picture-room"
   :user        "postgres"
   :password    "postgres"})

(hugsql/def-db-fns "sql/products.sql")

(def test-product
  {:id                         0
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
   :option_set_id              0
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
   :preorder_release_date      "foobar"
   :preorder_message           "foobar"
   :is_preorder_only           false
   :is_price_hidden            false
   :price_hidden_label         "foobar"
   :base_variant_id            0
   :custom_url                 {:a 1}})

(comment
  (create-products-table config)
  (create-product! config test-product)
  (get-products config))
