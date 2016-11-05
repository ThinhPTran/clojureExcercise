(ns tao2-explorer.components.selectable-list
  (:require [reagent.core :as reagent]
            [reagent.ratom :as ratom]
            [cljs-react-material-ui.core :as ui]
            [cljs-react-material-ui.reagent :as rui]))

(defn get-props
  [list-item]
  (nth list-item 1))

(defn- merge-props
  "Merges the props of a list-item with the specified props
   Assumes list-item has a props map"
  [list-item props]
  (let [list-props (get-props list-item)]
    (assoc list-item 1 (reagent/merge-props list-props props))))

(defn- count-items
  [list-items]
  (+ (count (filter #(contains? (get-props %) :nestedItems)))
     (for [item list-items]
       (if (contains? (get-props item) :nestedItems)
         (count-items (:nestedItems (get-props item)))
         0))))

; (defn- splurge
;   [list-items curr-idx selected-idx]
;   (let [idx (atom 0)]
;     (map)))

;; A Selectable List component made from a material UI list
;; Used like a regular material ui list except 2 additional options are accepted
;; :default-index is the default index of the item that will be selected at the start
;;                defaults to first item
;; :on-change is a function that will be called with the new selected-index every
;;            time it changes
;; Assumes all children are material ui list-items (dunno what will happen if they're not)
;; Currently will only work for lists that don't have nested items
;; Modify to add support for multilevel lists later
(defn selectable-list
  [props children]
  (let [default-index (if (:default-index props) (:default-index props) 0)
        on-change (if (:on-change props) (:on-change props) nil)
        selected-index (reagent/atom default-index)]
    (fn []
      (let [si @selected-index] ;; Avoids reactive deref unssuported warning
        [rui/list
         (for [idx (range (count children))
               :let [list-item (nth children idx)]]
           (as-> list-item $
                 (merge-props $ {:on-touch-tap #(do
                                                  (when (:on-change props)
                                                      ((:on-change props) idx))
                                                  (reset! selected-index idx))})
                 (if (= idx si)
                   (merge-props $ {:style {:background-color "rgba(0,0,0,0.2)"}})
                   $)))]))))
