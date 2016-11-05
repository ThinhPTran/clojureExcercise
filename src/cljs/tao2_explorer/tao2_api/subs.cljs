(ns tao2-explorer.tao2-api.subs
  (:require [ajax.core :refer [GET POST]]
            [taoensso.timbre :refer-macros [info error]]
            [goog.string :as gstring]
            [goog.string.format]))

(reg-sub :tao2/data-sources-list
  (fn [db [_]]
    (get-in )))
