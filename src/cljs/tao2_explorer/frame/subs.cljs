(ns tao2-explorer.frame.subs
  (:require [re-frame.core :refer [reg-sub]]
            [reagent.ratom :refer [reaction]]))

(reg-sub :frame/selected-index
  (fn [db _]
    (get-in db [:frame :selected-index] 0)))

(reg-sub :frame/isshow-leftDrawer
         (fn [db _]
           (get-in db [:frame :isshow-leftDrawer] true)))
