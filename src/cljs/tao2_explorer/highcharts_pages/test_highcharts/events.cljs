(ns tao2-explorer.highcharts-pages.test-highcharts.events
  (:require [re-frame.core :refer [reg-event-db]]))

(reg-event-db
  :test-highcharts/set-chartname
  (fn [db [_ chartname]]
    (assoc-in db [:test-highcharts :chartname] chartname)))

(reg-event-db
  :test-highcharts/set-tablevalue
  (fn [db [_ tableconfig changeData]]
      (let [rowIdx (first (first changeData))
            colIdx (second (first changeData))
            oldVal (nth (first changeData) 2)
            newVal (nth (first changeData) 3)
            dataTable (get-in tableconfig [:data] nil)
            newDataTable (assoc-in dataTable [rowIdx colIdx] (js/parseInt newVal))
            newtablecofig (assoc-in tableconfig [:data] newDataTable)
            newdb (assoc-in db [:test-highcharts :tableconfig] newtablecofig)]
        ;(println rowIdx)
        ;(println colIdx)
        ;(println oldVal)
        ;(println newVal)
        ;(println tableconfig)
        ;(println dataTable)
        ;(println newDataTable)
        ;(println newtablecofig)
        ;(println newdb)
        newdb)))

