(ns tao2-explorer.components.data-source-picker.views
  (:require [reagent.core :as reagent]
            [re-frame.core :refer [subscribe]]
            [cljs-react-material-ui.reagent :as rui]
            [tao2-explorer.components.data-source-picker.subs]
            [taoensso.timbre :refer-macros [debug spy]]))

;; Callback is a function that will be called with [:dsn <dsn-description]
;; default-dsn (optional) is a dsn keyword. e.g. :winglue
(defn data-source-picker
  "Data Source picker component"
  [{:keys [callback default-dsn]}]
  (let [data-sources (subscribe [:data-source-picker/data-sources-list])
        ds-list (:data @data-sources)
        selected-ds (reagent/atom (if default-dsn (name default-dsn) nil))]
    (fn []
      [rui/select-field {:autoWidth true
                         :on-change (fn [event key payload]
                                      (reset! selected-ds payload)
                                      (when callback
                                        (callback [(keyword payload)
                                                   ((keyword payload) ds-list)])))
                         :value @selected-ds}
       (when (not (:loading? @data-sources))
         (for [idx (range (count ds-list))
               :let [ds (get (vec ds-list) idx)]]
           ;; ds is a [:<dsn> <dsn-description] pair
           ^{:key (first ds)}
           [rui/menu-item {:primary-text (second ds)
                           :key idx
                           :value (name (first ds))}]))])))
