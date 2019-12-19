(ns metrics-server.api.hardware
  (:require [metrics-server.core :refer [call-api check-required-params with-collection-format]])
  (:import (java.io File)))

(defn get-metrics-with-http-info
  "Get hardware metrics"
  []
  (call-api "/hardware" :get
            {:path-params   {}
             :header-params {}
             :query-params  {}
             :form-params   {}
             :content-types []
             :accepts       []
             :auth-names    []}))

(defn get-metrics
  "Get hardware metrics"
  []
  (:data (get-metrics-with-http-info)))


;;убрать из выдачи элементы, у которых cpuTemp < 2
(defn task11 [get-metrics]
  (filter (fn [x] (> (get x :cpuTemp) 2 ))get-metrics)
  )

;;вывести среднюю cpuTemp
(defn task12 [get-metrics]
  (/ (reduce + (map (fn [file] (get file :cpuTemp)) get-metrics) )
     (count (filter (fn [x] (get x :cpuTemp)) get-metrics)))
  )

;;вывести среднюю cpuLoad
(defn task13 [get-metrics]
  (/ (reduce + (map (fn [file] (get file :cpuLoad)) get-metrics) )
     (count (filter (fn [x] (get x :cpuLoad)) get-metrics)))
  )


(defn -main [& args]
  (println (task11 (get-metrics)))
  (println (task12 (get-metrics)))
  (println (task13 (get-metrics)))
  )
