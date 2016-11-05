(ns tao2-explorer.components.treeview
  (:require
   [reagent.core :as reagent]
   [cljs-react-material-ui.reagent :as rui]
   [cljs-react-material-ui.icons :as ic]
   [taoensso.timbre :refer-macros [debug spy]]))

(def sample
  {:numbers [1 2 3 4 5 6 7 8 9 10]
   :strings ["this" "is" "a" "string" "array"]
   :point-set [[1 2] [3 4] [5 6] [7 8] [9 10]]})

(declare render-obj)

(defn- render-label
  "Component for rendering the label of an element of the tree view"
  [{:keys [name num-items expandable type expanded on-click]}]
  [:span (merge {:type "button"}
                {:style (merge (when expandable {:cursor "pointer"}))}
                (when (fn? on-click) {:on-click on-click}))
   (when expandable
     (if expanded
       (ic/navigation-expand-less {:style {:vertical-align "middle"}})
       (ic/navigation-expand-more {:style {:vertical-align "middle"}})))
   (when (some? name)
     [:span name ": "])
   (when (some? type)
     [:span type])
   (when (some? num-items)
     [:span "[" num-items "]"])])

(defn- map-inner-render
  "Renders the contents of a map"
  [map render-fn]
  [:ul {:style {:margin-left "25px"}}
   (for [item map
         :let [name (first item)
               obj (second item)]]
     ^{:key name} [:li [render-fn name obj]])])

(defn- coll-inner-render
  "Renders the contents of a coll that is not a map"
  [coll render-fn]
  [:ul {:style {:margin-left "25px"}}
   (for [i (range (count coll))
         :let [obj (nth coll i)]]
     ^{:key i}[:li [render-fn i obj]])])

(defn- render-coll
  "Renderer for collections"
  [name coll render-fn]
  (let [expanded (reagent/atom false)]
    (fn [name coll]
      [:div
        (render-label {:name name
                       :num-items (count coll)
                       :expandable true
                       :type (cond
                               (map? coll) "Map"
                               (vector? coll) "Vector"
                               (set? coll) "Set"
                               (list? coll) "List"
                               :else "Collection")
                       :on-click #(swap! expanded not)
                       :expanded @expanded})
        (when @expanded
          (cond
            (map? coll) (map-inner-render coll render-fn)
            :else (coll-inner-render coll render-fn)))])))

(def default-renderers
  "The list of default renderers for the treeview"
  [{:test coll?
    :expandable true
    :renderer render-coll}])

(defn- default-renderer
  "Default renderer for "
  [name obj]
  [:span name ": " (if (nil? obj)
                     "nil"
                     (str obj))])

(defn- render-obj
  "Object renderer component"
  [name obj renderers]
  ;; render function the renderers can call without needing knowledge of the renderers map
  (let [render-fn (partial #(render-obj %2 %3 %1) renderers)]
    (if-let [r (first (filter #((:test %) obj) renderers))]
      [(:renderer r) name obj render-fn]
      [default-renderer name obj render-fn])))

(defn treeview
  "Reagent tree view component"
  ([name tree] (treeview name tree default-renderers))
  ([name tree renderers]
   (reagent/create-class
    {:reagent-render
     (fn [name tree]
       [:div {:style {:text-align "left"}}
        [render-obj name tree renderers]])})))
