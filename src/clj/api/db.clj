(ns api.db
    (:require
      [api.jdbc]
      [clj-time.core :as time]
      [hugsql.core :as hugsql]))

;; avoids lint warnings of unresolved symbol by clj-kondo
(declare create-products-table!)
(declare create-product!)
(declare get-products)
(declare update-inventory-level!)

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

(comment
  (create-products-table! config)
  (create-product! config {})
  (get-products config))
