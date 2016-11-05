(ns tao2-explorer.tao2-api
  (:require [ajax.core :refer [GET POST]]
            [taoensso.timbre :refer-macros [info error]]
            [goog.string :as gstring]
            [goog.string.format]))

;;================================Tao2 API======================================
(defn log-handler [method args response]
  (info "API call for method" method "succeeded\n"
        "with args" args "\n"
        "response:" response))

(defn- log-error-handler [erresp method args]
  (error "API call for method" method "failed\n"
         "with args" args "\n"
         "status:" (:status erresp) "\n"
         "status-text:" (:status-text erresp) "\n"
         "server-error:" (:server-error erresp) "\n"
         "failure:" (name (:failure erresp)))
  erresp)

(defn tao2-call [{:keys [method args handler error-handler]}]
   (info "Calling API  \n"
         "Method:" method "  \n"
         "Args:" args "  \n")
   (POST (gstring/format "http://192.168.11.115:3000/api/%s" method)
         {:params args
          :handler (if (nil? handler)
                     (partial log-handler method args)
                     handler)
          :error-handler #(cond-> %
                                  true (log-error-handler method args)
                                  (some? error-handler) (error-handler))}))

;; Some helper functions for the API
(defn query-data-sources [{:keys [handler error-handler]}]
  (tao2-call {:method "query-data-sources"
              :args nil
              :handler handler
              :error-handler error-handler}))

(defn query-wells
  [{:keys [dsn select-set where-map handler error-handler]
    :or {where-map {}}}]
  (tao2-call {:method "query-matching-wells"
              :args {:dsn dsn
                     :select-set select-set
                     :where-map where-map}
              :handler handler
              :error-handler error-handler}))
