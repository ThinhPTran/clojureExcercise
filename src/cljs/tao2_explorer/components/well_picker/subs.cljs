(ns tao2-explorer.components.well-picker.subs
  (:require [re-frame.core :refer [reg-sub dispatch]]
            [reagent.ratom :as ratom]
            [tao2-explorer.tao2-api :as tao2-api]))

(reg-sub :well-picker/well-list
  (fn [db [dsn]]
    (let [wells (get-in db [:well-picker :well-list] {:loading? true})]
      (when  (and (not (:loading? wells))
                  (nil? (:data wells))))

      (tao2-api/query-wells
       {:dsn dsn
        :select-set [:field :lease :well :cmpl]
        :where-map {}
        :handler #(dispatch [:write-to [:well-picker :well-list]
                             {:loading? false
                              :data %}])
        :error-handler #(dispatch [:write-to [:well-picker :well-list]
                                   {:loading? false
                                    :error %}])})
      wells)))
