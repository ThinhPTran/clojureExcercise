(ns tao2-explorer.db)


(def init-tableconfig {
                       :data        [
                                     ["" "Kia" "Nissan" "Toyota" "Honda"]
                                     ["2008" 0 0 0 0]
                                     ["2009" 0 0 0 0]
                                     ["2010" 0 0 0 0]]
                       :rowHeaders  false
                       :colHeaders  false
                       :contextMenu false})

(def initial-db
  {:test-highcharts {:tableconfig init-tableconfig}})
