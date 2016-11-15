(ns tao2-explorer.highcharts-pages.test-highcharts.subs
  (:require [re-frame.core :refer [register-sub reg-sub dispatch]]
            [reagent.ratom :as ratom]
            [tao2-explorer.highcharts-pages.test-highcharts.events]
            [tao2-explorer.db :as t2db]))

(reg-sub :test-highcharts/chartname
         (fn [db _]
           (get-in db [:test-highcharts :chartname] "")))

(reg-sub :test-highcharts/tableconfig
         (fn [db _]
           (get-in db [:test-highcharts :tableconfig] t2db/init-tableconfig)))

