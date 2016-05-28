
(ns cumulo-client.core
  (:require [respo-spa.core :refer [render]]
            [cumulo-client.component.container :refer [comp-container]]
            [cumulo-client.client :refer [sender]]
            [cljs.core.async :refer [>! <!]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defonce store-ref (atom {}))

(defonce states-ref (atom {}))

(defn dispatch [op op-data] (go (>! sender [op op-data])))

(defn render-app []
  (let [target (.querySelector js/document "#app")]
    (render (comp-container @store-ref) target dispatch states-ref)))

(defn -main []
  (enable-console-print!)
  (render-app)
  (add-watch store-ref :changes render-app)
  (add-watch states-ref :changes render-app)
  (println "app started!"))

(set! js/window.onload -main)

(defn on-jsload [] (render-app) (println "code updated."))
