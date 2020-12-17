(ns api.cron
    (:require
      [api.db :as db]
      [api.bigcommerce :as bc]
      [clj-time.format :as f]
      [clj-time.core :as time]
      [clojure.core.async :as async :refer [<! timeout go]]))

(def running (atom true))

(defn now []
  (f/unparse (f/formatters :date-hour-minute-second) (time/now)))

(defn hours
  "Converts hours -> milliseconds"
  [n] (* n 3.6e+6))

(def interval (hours 1))

(defn task-wrapper [config]
  (let [c (:bigcommerce config)
        products (bc/fetch-products c 1 400)]
    (when (> (count products) 0)
      (doseq [p (:data products)]
        (db/create-product! db/config (db/product-insert-params p))))))

(defn start
  [config]
  (go
    (while @running
      (<! (timeout interval))
      (println (format "cronjob started at: %s" (now)))
      (task-wrapper config)
      (println (format "cronjob completed at: %s" (now))))))
