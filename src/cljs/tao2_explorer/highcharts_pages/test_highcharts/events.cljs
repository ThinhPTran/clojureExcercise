(ns tao2-explorer.highcharts-pages.test-highcharts.events
  (:require [re-frame.core :refer [reg-event-db]]))

(reg-event-db
  :test-highcharts/set-chartname
  (fn [db [_ chartname]]
    (assoc-in db [:test-highcharts :chartname] chartname)))

;(reg-event-db
;  :test-highcharts/set-tablevalue
;  (fn [db [_ changeData]]
;    (do
;      (let [rowIdx (first (first changeData))
;            colIdx (second (first changeData))
;            oldVal (nth (first changeData) 2)
;            newVal (nth (first changeData) 3)
;            tableconfig (get-in db [:test-highcharts :tableconfig])
;            tabledata (get-in tableconfig [:data])]
;        (println changeData)
;        (println rowIdx)
;        (println colIdx)
;        (println oldVal)
;        (println newVal)
;        (println tableconfig)
;        (println tabledata)))))


;; (assoc-in db [:test-highcharts :tableconfig] nil)