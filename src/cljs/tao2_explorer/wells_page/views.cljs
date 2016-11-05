(ns tao2-explorer.wells-page.views
  (:require [reagent.core :as reagent]
            [reagent.ratom :as ratom]
            [re-frame.core :refer [subscribe dispatch]]
            [cljs-react-material-ui.core :as ui]
            [cljs-react-material-ui.reagent :as rui]
            [tao2-explorer.components.data-source-picker.views :refer [data-source-picker]]
            [tao2-explorer.components.well-picker.views :refer [well-picker]]))

;(defn wells-page
;  []
;  [:div])

(defn wells-page
  []
  (let [data-source (reagent/atom nil)]
   (fn []
    [:div
     [:div.row {:style {:margin-bottom "30px"}}
      [:div.col-xs
       [data-source-picker #(reset! data-source %)]
       [well-picker {}]]]])))
