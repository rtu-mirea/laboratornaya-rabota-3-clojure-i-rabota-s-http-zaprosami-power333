(ns metrics-server.api.files
  (:require [metrics-server.core :refer [call-api check-required-params with-collection-format]])
  (:import (java.io File)))

(defn get-files-with-http-info
  "Get files in directory on server"
  []
  (call-api "/files" :get
            {:path-params   {}
             :header-params {}
             :query-params  {}
             :form-params   {}
             :content-types []
             :accepts       []
             :auth-names    []}))

(defn get-files
  "Get files in directory on server"
  []
  (:data (get-files-with-http-info)))



;;убрать из выдачи элементы
(defn task21 [get-files]
  (filter (fn [x] (not (get x :directory))) get-files)
  )

;;отображать только исполняемые файлы
(defn task22 [get-files]
  (filter (fn [a] (get a :executable)) get-files)
  )

;;поменять все расширения .conf в .cfg
(defn task23 [get-files]
  (map (fn [f]
                 {
                  :name (clojure.string/replace (get f :name) #".conf" ".cfg")
                  :size (get f :size)
                  :changed (get f :changed)
                  :directory (get f :directory)
                  :executable (get f :executable)
                  }
         ) get-files)
  )


(defn task24 [get-files]
    (/ (reduce + (map (fn [file] (get file :size))
                      (filter (fn [x] (not (get x :directory))) get-files))
               )
       (count (filter (fn [x] (not (get x :directory))) get-files)))
  )


(defn -main [& args]
  (println  (task21 (get-files) ))
  (println  (task22 (get-files) ))
  (println  (task23 (get-files) ))
  (println  (task24 (get-files) ))
  )