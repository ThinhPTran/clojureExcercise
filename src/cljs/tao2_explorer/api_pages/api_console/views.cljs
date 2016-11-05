(ns tao2-explorer.api-pages.api-console.views
  (:require [reagent.core :as reagent]
            [reagent.ratom :as ratom]
            [re-frame.core :refer [subscribe dispatch]]
            [cljs-react-material-ui.core :as ui]
            [cljs-react-material-ui.reagent :as rui]
            [tao2-explorer.tao2-api :as tao2]
            [tao2-explorer.api-pages.api-console.events]
            [tao2-explorer.api-pages.api-console.subs]
            [tao2-explorer.components.treeview :as tv]
            [cljs.reader :as reader]
            [cljs.pprint :as pp]))

(defn- send-request
  [method params-string response-atom loading-atom]
  (try
    (let [params (if (nil? params-string)
                   nil
                   (reader/read-string params-string))]
      (reset! loading-atom true)
      (tao2/tao2-call {:method method
                       :args params
                       :handler
                       (fn [r]
                         (reset! loading-atom false)
                         (reset! response-atom r))
                       :error-handler
                       (fn [r]
                         (reset! loading-atom false)
                         (reset! response-atom r))}))
    (catch :default e
      (reset! loading-atom false)
      (println "error occured"))))

(defn api-console-page
  []
  (let [response (reagent/atom "")
        params (subscribe [:api-console/params])
        api-method (subscribe [:api-console/method])
        loading? (reagent/atom false)]
    (fn []
      [:div
       [:div.row {:style {:margin-bottom "10px"}}
        [:div.col-xs-6
         [rui/text-field {:name "api-method"
                          :hint-text "API Method"
                          :on-change #(dispatch [:api-console/set-method
                                                 (.-value (.-target %))])}]]
        [:div.col-xs-6
         [:div {:style {:float "right"}}
          [rui/raised-button {:background-color (ui/color :green500)
                              :label "Send"
                              :disabled @loading?
                              :on-touch-tap #(send-request
                                              @api-method @params response
                                              loading?)}]]]]

       [:div.row {:style {:margin-bottom "10px"}}
        [:div.col-xs-12
         [rui/card
          [rui/card-title {:title "Parameters"
                           :style {:background-color (ui/color :grey300)}}]
          [:div {:style {:padding "10px"}}
           [rui/text-field {:name "parameters"
                            :hint-text "Parameters"
                            :full-width true
                            :multi-line true
                            :rows 4
                            :rows-max  8
                            :on-change #(dispatch [:api-console/set-params
                                                   (.-value (.-target %))])}]]]]]
       [:div.row
        [:div.col-xs-12
         [rui/card {:style {:min-height "100%"}}
          [rui/card-title {:title "Response"
                           :style {:background-color (ui/color :grey300)}}]
          [:div {:style {:padding "10px"}}
           (if @loading?
             [:div.center-xs [:i.fa.fa-spinner.fa-pulse.fa-2x]]
             [tv/treeview "response" @response])]]]]])))
          ; [:p {:style {:padding "10px"
          ;              :white-space "pre-wrap"}}
          ;     (with-out-str (pp/pprint @response))]]]]])))
