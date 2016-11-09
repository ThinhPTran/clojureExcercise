(ns tao2-explorer.highcharts-pages.test-highcharts.subs
  (:require [re-frame.core :refer [register-sub reg-sub dispatch]]
            [reagent.ratom :as ratom]))

(reg-sub :test-highcharts/chartname
         (fn [db _]
           (get-in db [:test-highcharts :chartname] "")))

(reg-sub :test-highcharts/tableconfig
         (fn [db _]
           (get-in db [:test-highcharts :tableconfig] {
                                                       :data        [
                                                                     ["" "Kia" "Nissan" "Toyota" "Honda"]
                                                                     ["2008" 10 11 12 13]
                                                                     ["2009" 20 11 14 20]
                                                                     ["2010" 30 15 12 5]]
                                                       :rowHeaders  false
                                                       :colHeaders  false
                                                       :contextMenu true})))

