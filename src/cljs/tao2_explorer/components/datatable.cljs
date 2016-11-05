(ns tao2-explorer.components.datatable
  (:require [reagent.core :as reagent]))

(defn- setup-table [table-node options]
  (let [js-table-node (clj->js table-node)]
    (if (.-length (js/$ js-table-node))
      (.DataTable (js/$ js-table-node) (clj->js options)))))

(defn- bind-callbacks [data-table callbacks]
  (doseq [k (keys callbacks)]
    (.on data-table (name k) (k callbacks))))

(defn- unbind-callbacks [data-table callbacks]
  (doseq [k (keys callbacks)]
    (.off data-table (name k) (k callbacks))))

(defn DataTable [options callbacks]
  "DataTable widget. options is a map conforming to the possible options of
   DataTables. Read: https://datatables.net/reference/option/

   Callbacks arg is a map of :event callback_fn. refer to
   https://datatables.net/reference/event/ for a list of all events.
   events are maped to keywords, e.g. :select is the select event.
   callbacks will be called with the args specified in the DataTables reference
   and the data-table object as the first arg. e.g. (fn data-table ...)"
  (let [data-table (atom nil)]
    (reagent/create-class
     {:component-did-mount
      (fn [this]
        (reset! data-table (setup-table (reagent/dom-node this) options))
        (bind-callbacks @data-table callbacks))

      :component-will-update
      (fn [this [_ new-options new-callbacks]]
        (reset! data-table (setup-table (reagent/dom-node this) (merge new-options
                                                                       {:destroy true})))
        (bind-callbacks @data-table new-callbacks))

      :component-did-update
      (fn [this [_ _ old-callbacks]]
        (unbind-callbacks @data-table old-callbacks))

      :reagent-render
      (fn []
        [:table.display {:width "100%"}])})))

;; Implement this among other stuff eventually
;; https://datatables.net/examples/api/multi_filter_select.html
