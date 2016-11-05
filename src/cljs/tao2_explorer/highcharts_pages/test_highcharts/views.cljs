(ns tao2-explorer.highcharts-pages.test-highcharts.views
  (:require [reagent.core :as reagent]
            [reagent.ratom :as ratom]
            [re-frame.core :refer [subscribe dispatch]]
            [cljs-react-material-ui.core :as ui]
            [cljs-react-material-ui.reagent :as rui]
            [tao2-explorer.highcharts-pages.test-highcharts.subs]
            [tao2-explorer.highcharts-pages.test-highcharts.events]
            [cljsjs.highcharts]
            [cljsjs.handsontable]))


(def chart-config
  {
   :title {:text "Historic World Population by Region"}
   :subtitle {:text "Source: Wikipedia.org"}
   :xAxis {:categories ["Africa" "America" "Asia" "Europe" "Oceania"]
           :title {:text nil}}
   :yAxis {:min 0
           :title {:text "Population (millions)"
                   :align "high"}
           :labels {:overflow "justify"}
           :plotLines [{
                        :value 0
                        :width 1
                        }]
           }
   :tooltip {:valueSuffix " millions"}
   :legend {:layout "vertical"
            :align "right"
            :verticalAlign "top"
            :borderWidth 1
            :shadow false}
   :credits {:enabled false}
   :series [{:name "Year 1800"
             :data [107 31 635 203 2]}
            {:name "Year 1900"
             :data [133 156 947 408 6]}
            {:name "Year 2008"
             :data [973 914 4054 732 34]}]})

(def table-config
  {
   :data  [
           ["" "Kia" "Nissan" "Toyota" "Honda"]
           ["2008" 10 11 12 13]
           ["2009" 20 11 14 13]
           ["2010" 30 15 12 13]
           ]
   :rowHeaders false
   :colHeaders false
   :contextMenu true
   })

(defn sampleTable-render []
  [:div {:style {:min-width "310px" :max-width "800px"
                 :height "400px" :margin "0 auto"}}])

(defn sampleTable-did-mount [this]
  (js/Handsontable (reagent/dom-node this) (clj->js table-config)))

(defn sampleTable []
  (reagent/create-class {:reagent-render sampleTable-render
                         :component-did-mount sampleTable-did-mount}))

(defn sampleHighchart-render []
  [:div {:style {:min-width "310px" :max-width "800px"
                 :height "400px" :margin "0 auto"}}])

(defn sampleHighchart-did-mount [this]
  (js/Highcharts.Chart. (reagent/dom-node this) (clj->js chart-config)))



(defn sampleHighchart []
  (reagent/create-class {:reagent-render sampleHighchart-render
                         :component-did-mount sampleHighchart-did-mount}))


(defn test-highcharts-page
  []
  (let [chartname (subscribe [:test-highcharts/chartname])]
    (fn []
      [:div
       [:div.row {:style {:margin-bottom "10px"}}
        [:div.col-xs-6
         [rui/text-field {:name "highchart-name"
                          :hint-text "Highchart Name"
                          :on-change #(dispatch [:test-highcharts/set-chartname
                                                 (.-value (.-target %))])}]]]
       [:div.row
        [:div.col-xs-12
         [rui/card {:style {:min-height "100%"}}
          [rui/card-title {:title @chartname
                           :style {:background-color (ui/color :grey300)}}]
          [:div {:style {:padding "10px"}}
           [sampleTable]]]]]
       [:div.row
        [:div.col-xs-12
         [rui/card {:style {:min-height "100%"}}
          [rui/card-title {:title @chartname
                           :style {:background-color (ui/color :grey300)}}]
          [:div {:style {:padding "10px"}}
           [sampleHighchart]]]]]])))



