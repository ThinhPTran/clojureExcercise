(ns tao2-explorer.components.well-picker.views
  (:require [reagent.core :as reagent]
            [re-frame.core :refer [subscribe]]
            [tao2-explorer.components.well-picker.subs]
            [tao2-explorer.components.well-picker.events]
            [cljs-react-material-ui.reagent :as rui]
            [tao2-explorer.components.datatable :refer [DataTable]]
            [clojure.set :refer [rename-keys]]))

(defn- well-selector
  "well selector for the well-picker
   well list is a vector of maps [{:field :lease :well :cmpl} ...]
   callback is a fn that gets called when a well selected with args:
   {:field _ :lease _ :well _ :cmpl _}"
  [{:keys [well-list callback]}]
  [:div {:style {:padding "10px"}}
   [DataTable
    {:data well-list
     :columns [{:title "Field"
                :data :field}
               {:title "Lease"
                :data :lease}
               {:title "Well"
                :data :well}
               {:title "Completion"
                :searchable false
                :data :cmpl}]
     :deferRender true
     :select "single"}
    {:select (fn [e dt type index]
               (callback (-> (.rows dt index)
                             (.data)
                             (aget 0)
                             (js->clj)
                             (rename-keys {"field" :field
                                           "lease" :lease
                                           "well" :well
                                           "cmpl" :cmpl}))))}]])

(defn- well-picker-table
  "Shows the currently selected well"
  [{:keys [field lease well cmpl]}]
  [:div
   (if (some nil? [field lease well cmpl])
     [:div {:style {:text-align "center"
                       :font-size "1.5em"}}
      "No Well Selected"]
     [rui/table
      [rui/table-header {:display-select-all false
                         :adjustForCheckbox false}
       [rui/table-row
        [rui/table-header-column "Field"]
        [rui/table-header-column "Lease"]
        [rui/table-header-column "Well"]
        [rui/table-header-column "Cmpl"]]]
      [rui/table-body {:display-row-checkbox false}
       [rui/table-row
        (for [i [field lease well cmpl]]
          ^{:key i} [rui/table-row-column i])]]])])


(defn well-picker
  "Well Picker reagent component
   requires a data source name (dsn)"
  [{:keys [dsn preselected-well callback]}]
  (let [show-selector? (reagent/atom false)
        selected-well (reagent/atom preselected-well)
        well-list (subscribe [:well-picker/well-list dsn])]
   (fn []
     [:div {:style {:padding "10px"}}
      (if (nil? dsn)
        [:div {:style {:text-align "center"
                       :font-size "1.5em"}}
         "Select a Data Source First"]
       (if @show-selector?
         [rui/paper
          [well-selector {:well-list well-list
                          :callback (fn [well]
                                      (reset! selected-well well)
                                      (reset! show-selector? false)
                                      (when callback
                                        (callback well)))}]]
         [:div
          [well-picker-table @selected-well]
          [rui/raised-button {:label "Select Well"
                              :on-touch-tap #(swap! show-selector? not)}]]))])))
