(ns tao2-explorer.highcharts-pages.test-highcharts.events
  (:require [re-frame.core :refer [reg-event-db]]))

(reg-event-db
  :test-highcharts/set-chartname
  (fn [db [_ chartname]]
    (assoc-in db [:test-highcharts :chartname] chartname)))
