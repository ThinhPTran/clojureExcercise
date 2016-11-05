(defproject tao2-explorer "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.229"]
                 ;; Material UI requires react to be excluded
                 [reagent "0.6.0" :exclusions [cljsjs/react]]
                 [cljs-ajax "0.5.8"]
                 [binaryage/devtools "0.8.1"]
                 [cljs-react-material-ui "0.2.21"]
                 [com.taoensso/timbre "4.7.4"]
                 [re-frame "0.8.0"]
                 [cljsjs/highcharts "4.2.5-2"]
                 [cljsjs/handsontable "0.26.1-0"]]


  :min-lein-version "2.5.3"

  :source-paths ["src/clj"]

  :plugins [[lein-cljsbuild "1.1.3"]]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :figwheel {:css-dirs ["resources/public/css"]}

  :profiles
  {:dev
   {:dependencies []

    :plugins      [[lein-figwheel "0.5.4-3"]]}}


  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src/cljs"]
     :figwheel     {:on-jsload "tao2-explorer.core/reload"}
     :compiler     {:main                 tao2-explorer.core
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/out"
                    :asset-path           "js/compiled/out"
                    :source-map-timestamp true}}

    {:id           "min"
     :source-paths ["src/cljs"]
     :compiler     {:main          tao2-explorer.core
                    :output-to     "resources/public/js/compiled/app.js"
                    :optimizations :advanced
                    :closure-defines {goog.DEBUG false}
                    :pretty-print  false}}]})
