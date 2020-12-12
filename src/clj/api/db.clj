(ns api.db
    (:require [hugsql.core :as hugsql]))

(def config
  {:classname   "org.postgresql.Driver"
   :subprotocol "postgres"
   :subname     "//10.254.254.254:5432/picture-room"
   :user        "postgres"
   :password    "postgres"})

(hugsql/def-db-fns "sql/products.sql")

(comment
  (create-products-table config))
