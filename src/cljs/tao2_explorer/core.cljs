(ns tao2-explorer.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :refer [subscribe dispatch]]
   [cljs.reader :as reader]
   [cljsjs.material-ui]
   [cljs-react-material-ui.core :as ui]
   [cljs-react-material-ui.reagent :as rui]
   [cljs-react-material-ui.icons :as ic]
   [devtools.core :as devtools]
   [tao2-explorer.events]
   [tao2-explorer.api-pages.get-well.views :as get-well]
   [tao2-explorer.api-pages.api-console.views :as api-console]
   [tao2-explorer.frame.views :as frame]))

(defonce debug?
  ^boolean js/goog.DEBUG)

(defn dev-setup []
  (when debug?
    (devtools/install!)
    (enable-console-print!)
    (println "dev mode")))

(defonce app-state
  (reagent/atom
   {:hello "hi"}))

(defn drawer
  "drawer"
  []
  [:div
   [rui/app-bar {:title "TAO2 EXPLORER"
                 :show-menu-icon-button false}]
   [rui/list
    [rui/list-item {:primary-text "API Methods"
                    :nestedItems [(reagent/as-element [rui/list-item "Get Well"])]}]]])

(defn home-page []
  [rui/mui-theme-provider
   {:mui-theme (ui/get-mui-theme
                {:pallete {:text-color (ui/color :green600)}})}
   [:div
    [rui/app-bar {:title "TAO2"}]
    [rui/drawer
     [drawer]]
    ;; 256px left padding to make space for drawer
    [:div {:style {:padding-left "256px" :margin "25px"}}
     [api-console/api-console-page]]]])
     ; [get-well/method-page]]]])
         ; [tv/treeview "items" tv/sample]]]]]]])
         ; [rui/mui-theme-provider
         ;  {:mui-theme (ui/get-mui-theme {:palette {:text-color (ui/color :blue200)}})}
         ;  [rui/raised-button {:label "Blue button"}]]
         ; (ic/action-home {:color (ui/color :grey600)})
         ; [rui/raised-button {:label "Click me"
         ;                     :icon (ic/social-group)
         ;                     :on-touch-tap #(println "clicked")}]]]]]]])

(defn reload []
  (reagent/render [frame/frame]
                  (.getElementById js/document "app")))

(defn ^:export main []
  (dev-setup)
  (dispatch [:initialise-db])
  (reagent/render [frame/frame]
                  (.getElementById js/document "app")))
