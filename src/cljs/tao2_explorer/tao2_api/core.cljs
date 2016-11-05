(ns tao2-explorer.tao2-api.core
  (:require [ajax.core :refer [GET POST]]
            [taoensso.timbre :refer-macros [info error]]
            [goog.string :as gstring]
            [goog.string.format]))

;; logs a successful response to the console
(defn log-handler [method args response]
  (info "API call for method" method "succeeded\n"
        "with args" args "\n"
        "response:" response))

;; logs an error response to the console
(defn- log-error-handler [erresp method args]
  (error "API call for method" method "failed\n"
         "with args" args "\n"
         "status:" (:status erresp) "\n"
         "status-text:" (:status-text erresp) "\n"
         "server-error:" (:server-error erresp) "\n"
         "failure:" (name (:failure erresp)))
  erresp)


;; Makes an API call to tao2
(defn tao2-call [{:keys [method args handler error-handler]}]
   (info "Calling API  \n"
         "Method:" method "  \n"
         "Args:" args "  \n")
   (POST (gstring/format "http://192.168.1.137:4201/api/%s" method)
         {:params args
          :handler (if (nil? handler)
                     (partial log-handler method args)
                     handler)
          :error-handler #(cond-> %
                                  true (log-error-handler method args)
                                  (some? error-handler) (error-handler))}))
