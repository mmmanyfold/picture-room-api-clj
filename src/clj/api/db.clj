(ns api.db
    (:require
      [api.jdbc]
      [clj-time.core :as time]
      [hugsql.core :as hugsql]))

(def config
  {:classname   "org.postgresql.Driver"
   :subprotocol "postgres"
   :subname     "//10.254.254.254:5432/picture-room"
   :user        "postgres"
   :password    "postgres"})

(hugsql/def-db-fns "sql/products.sql")

(defn product-insert-params [p]
  {:id                         (:id p)
   :created_at                 (time/now)
   :updated_at                 (time/now)
   :deleted_at                 (time/now)
   :name                       (:name p)
   :type                       (:type p)
   :sku                        (:sku p)
   :description                (:description p)
   :weight                     (:weight p)
   :width                      (:width p)
   :depth                      (:depth p)
   :height                     (:height p)
   :price                      (:price p)
   :cost_price                 (:cost_price p)
   :retail_price               (:retail_price p)
   :sale_price                 (:sale_price p)
   :map_price                  (:map_price p)
   :tax_class_id               (:tax_class_id p)
   :product_tax_code           (:product_tax_code p)
   :calculated_price           (:calculated_price p)
   :categories                 (:categories p)
   :brand_id                   (:brand_id p)
   :option_set_display         (:option_set_display p)
   :inventory_level            (:inventory_level p)
   :inventory_warning_level    (:inventory_warning_level p)
   :inventory_tracking         (:inventory_tracking p)
   :reviews_rating_sum         (:reviews_rating_sum p)
   :reviews_count              (:reviews_count p)
   :total_sold                 (:total_sold p)
   :fixed_cost_shipping_price  (:fixed_cost_shipping_price p)
   :is_free_shipping           (:is_free_shipping p)
   :is_visible                 (:is_visible p)
   :is_featured                (:is_featured p)
   :warranty                   (:warranty p)
   :bin_picking_number         (:bin_picking_number p)
   :layout_file                (:layout_file p)
   :upc                        (:upc p)
   :mpn                        (:mpn p)
   :gtin                       (:gtin p)
   :search_keywords            (:search_keywords p)
   :availability               (:availability p)
   :availability_description   (:availability_description p)
   :gift_wrapping_options_type (:gift_wrapping_options_type p)
   :sort_order                 (:sort_order p)
   :condition                  (:condition p)
   :is_condition_shown         (:is_condition_shown p)
   :order_quantity_minimum     (:order_quantity_minimum p)
   :order_quantity_maximum     (:order_quantity_maximum p)
   :page_title                 (:page_title p)
   :meta_description           (:meta_description p)
   :date_created               (:date_created p)
   :date_modified              (:date_modified p)
   :view_count                 (:view_count p)
   :preorder_message           (:preorder_message p)
   :is_preorder_only           (:is_preorder_only p)
   :is_price_hidden            (:is_price_hidden p)
   :price_hidden_label         (:price_hidden_label p)
   :custom_url                 (:custom_url p)})

(def test-product-insert
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

(comment
  (create-products-table config)
  (create-product! config test-product-insert)
  (get-products config))
