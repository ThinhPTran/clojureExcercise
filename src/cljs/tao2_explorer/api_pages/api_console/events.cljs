(ns tao2-explorer.api-pages.api-console.events
  (:require [re-frame.core :refer [reg-event-db]]))

(reg-event-db
 :api-console/set-params
 (fn [db [_ params]]
   (assoc-in db [:api-console :params] params)))

(reg-event-db
 :api-console/set-method
 (fn [db [_ method]]
   (assoc-in db [:api-console :method] method)))
