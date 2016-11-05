(ns tao2-explorer.api-pages.api-console.subs
  (:require [re-frame.core :refer [register-sub reg-sub dispatch]]
            [reagent.ratom :as ratom]))

(reg-sub :api-console/params
  (fn [db _]
    (get-in db [:api-console :params] "")))

(reg-sub :api-console/method
  (fn [db _]
    (get-in db [:api-console :method] "")))
