(ns tao2-explorer.frame.events
  (:require [re-frame.core :refer [reg-event-db]]))

(reg-event-db
 :frame/set-selected-index
 (fn [db [_ idx]]
   (assoc-in db [:frame :selected-index] idx)))

(reg-event-db
  :frame/set-hide
  (fn [db [_]]
    (assoc-in db [:frame :isshow-leftDrawer] false)))

(reg-event-db
  :frame/set-show
  (fn [db [_]]
    (assoc-in db [:frame :isshow-leftDrawer] true)))
