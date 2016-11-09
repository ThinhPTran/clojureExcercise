(ns tao2-explorer.frame.views
  (:require [reagent.core :as reagent]
            [reagent.ratom :as ratom]
            [re-frame.core :refer [subscribe dispatch]]
            [cljs-react-material-ui.core :as ui]
            [cljs-react-material-ui.reagent :as rui]
            [tao2-explorer.frame.events]
            [tao2-explorer.frame.subs]
            [tao2-explorer.components.selectable-list :as sl]
            [tao2-explorer.api-pages.api-console.views :as api-console]
            [tao2-explorer.depth-profile.views :as depth-profile]
            [tao2-explorer.wells-page.views :as wells-page]
            [tao2-explorer.highcharts-pages.test-highcharts.views :as test-highchart]))


(def pages-list
  [{:name "Wells"
    :page wells-page/wells-page}
   {:name "API Console"
    :page api-console/api-console-page}
   {:name "Depth Profile"
    :page depth-profile/depth-profile}
   {:name "Test Highchart"
    :page test-highchart/test-highcharts-page}])


(defn btnHideLeftDrawer
  "This is a hide button for left drawer"
  []
  (fn []
    [:div
     [rui/raised-button {:background-color (ui/color :green500)
                         :label "Hide"
                         :on-touch-tap #(dispatch [:frame/set-hide])}]]))

(defn tao2Header
  []
  (fn []
    [:div
     [rui/app-bar {:title "TAO2"
                   :onLeftIconButtonTouchTap #(dispatch [:frame/set-show])}]]))

(defn drawer
  "drawer aka the left sidebar"
  []
  (let [selected-index (subscribe [:frame/selected-index])]
    (fn []
      [:div
       [:div.row
        [rui/app-bar {:title "TAO2 EXPLORER"
                      :show-menu-icon-button false}]
        [btnHideLeftDrawer]]


       [sl/selectable-list {:on-change #(dispatch [:frame/set-selected-index %])
                            :default-index @selected-index}
         (for [page pages-list
               :let [name (:name page)]]
           ^{:key name} [rui/list-item {:primary-text name}])]])))

(defn frame
  "The frame of the SPA"
  []
  (let [selected-index (subscribe [:frame/selected-index])
        isshowleft (subscribe [:frame/isshow-leftDrawer])]
    (fn []
      [rui/mui-theme-provider {:mui-theme
                               (ui/get-mui-theme
                                 {:pallete
                                  {:text-color
                                   (ui/color :green600)}})}
       [:div
        [tao2Header]
        [rui/drawer {:open @isshowleft}
         [drawer]]
       ;; 256px left padding to make space for drawer
        (if @isshowleft [:div {:style {:padding-left "256px" :margin "25px"}}
                         [(:page (nth pages-list @selected-index))]]
                        [:div {:style {:padding-left "5px" :margin "25px"}}
                         [(:page (nth pages-list @selected-index))]])]])))


