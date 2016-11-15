(ns tao2-explorer.highcharts-pages.test-highcharts.subs
  (:require [re-frame.core :refer [register-sub reg-sub dispatch]]
            [reagent.ratom :as ratom]
            [tao2-explorer.highcharts-pages.test-highcharts.events]))

(reg-sub :test-highcharts/chartname
         (fn [db _]
           (get-in db [:test-highcharts :chartname] "")))

(reg-sub :test-highcharts/tableconfig
         (fn [db _]
           (get-in db [:test-highcharts :tableconfig] {
                                                       :data        [
                                                                     ["" "Kia" "Nissan" "Toyota" "Honda"]
                                                                     ["2008" 0 0 0 0]
                                                                     ["2009" 0 0 0 0]
                                                                     ["2010" 0 0 0 0]]
                                                       :rowHeaders  false
                                                       :colHeaders  false
                                                       :contextMenu false})))

