(ns tao2-explorer.components.data-source-picker.subs
  (:require [re-frame.core :refer [register-sub reg-sub dispatch]]
            [reagent.ratom :as ratom]
            [tao2-explorer.tao2-api :as tao2-api]
            [taoensso.timbre :refer-macros [debug spy]]))

(reg-sub :data-source-picker/data-sources-list
  (fn [db [_]]
    (let [ds (get-in db [:data-source-picker :data-sources-list] {:loading? true})]
      (debug ds)
      (when (or
             (and (not (:loading? ds)) (nil? (:data ds)))
             (:error ds))
       (tao2-api/query-data-sources
        {:handler #(dispatch [:write-to [:data-source-picker :data-sources-list]
                              {:loading? false
                               :data %}])
         :error-handler #(dispatch [:write-to [:data-source-picker :data-sources-list]
                                    {:loading? false
                                     :error %}])}))
      ds)))
