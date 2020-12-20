(ns user
    (:require
      [api.core :as api]
      [integrant.repl :as ig-repl]))

(ig-repl/set-prep! (constantly api.core/system-config))

;; aliases for the repl
(def go ig-repl/go)

(def halt ig-repl/halt)

(def reset ig-repl/reset)

(comment
  (go)
  (halt)
  (reset))
