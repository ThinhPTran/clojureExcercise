(ns tao2-explorer.highcharts-pages.test-highcharts.subs
  (:require [re-frame.core :refer [register-sub reg-sub dispatch]]
            [reagent.ratom :as ratom]))

(reg-sub :test-highcharts/chartname
         (fn [db _]
           (get-in db [:test-highcharts :chartname] "")))
