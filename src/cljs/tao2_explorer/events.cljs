(ns tao2-explorer.events
  (:require [re-frame.core :refer [reg-event-db]]
            [tao2-explorer.db :as db]))

;; Initializes the applications database
(reg-event-db
 :initialise-db
 (fn [_ [_]]
   db/initial-db))


(reg-event-db
 :write-to
 (fn [db [_ path data]]
   (assoc-in db path data)))
