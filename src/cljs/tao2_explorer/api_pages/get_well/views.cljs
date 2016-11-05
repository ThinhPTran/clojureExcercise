(ns tao2-explorer.api-pages.get-well.views
  (:require [reagent.core :as reagent]
            [reagent.ratom :as ratom]
            [re-frame.core :refer [subscribe dispatch register-handler register-sub]]
            [cljs-react-material-ui.core :as ui]
            [cljs-react-material-ui.reagent :as rui]
            [tao2-explorer.api-pages.get-well.events]
            [tao2-explorer.api-pages.get-well.subs]
            [tao2-explorer.components.treeview :as tv]
            [tao2-explorer.components.data-source-picker.views :refer [data-source-picker]]
            [tao2-explorer.components.well-picker.views :refer [well-picker]]))


(def sample-well-list
  [["field" "lease" "well" "cmpl"]
   ["field2" "lease2" "well2" "cmpl2"]])

(defn method-page
  []
  (let [selected-well (reagent/atom {})
        well-list (reagent/atom sample-well-list)
        params (ratom/reaction (merge {} @selected-well))]
    (fn []
      [:div
       ;; Well Picker
       [:div.row {:style {:margin-bottom "30px"}}
        [:div.col-xs
         [rui/card
          [rui/card-title {:title "Pick a Well"
                           :style {:background-color (ui/color :grey300)}}]
          [data-source-picker]
          [well-picker {:preselected-well @selected-well
                        :well-list (vec (for [well @well-list]
                                          (zipmap [:field :lease :well :cmpl]
                                                  well)))
                        :callback #(reset! selected-well %)}]]]]
       ;; Go Button
       [:div.row.end-xs
        [:div.col-xs
         [rui/raised-button {:background-color (ui/color :green500)
                             :label "Send"
                             :style {:margin-bottom "10px"}}]]]
       ;; Param and Response Cards
       [:div.row
        [:div.col-xs-6
         [rui/card
          [rui/card-title {:title "Parameters"
                           :style {:background-color (ui/color :grey300)}}]
          [tv/treeview "Parameters" @params]]]
        [:div.col-xs-6
         [rui/card
          [rui/card-title {:title "Response"
                           :style {:background-color (ui/color :grey300)}}]
          [tv/treeview "items" tv/sample]]]]])))
