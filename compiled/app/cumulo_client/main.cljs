
(ns cumulo-client.main
  (:require [respo.core :refer [render! clear-cache!]]
            [cumulo-client.component.container :refer [comp-container]]
            [cumulo-client.core :refer [send! setup-socket!]]))

(defonce store-ref (atom {}))

(defonce states-ref (atom {}))

(defn dispatch! [op op-data] (send! op op-data))

(defn render-app! []
  (let [target (.querySelector js/document "#app")]
    (render! (comp-container @store-ref) target dispatch! states-ref)))

(defn -main []
  (enable-console-print!)
  (render-app!)
  (setup-socket! store-ref {:url "ws://localhost:4010"})
  (add-watch store-ref :changes render-app!)
  (add-watch states-ref :changes render-app!)
  (println "app started!"))

(set! js/window.onload -main)

(defn on-jsload []
  (clear-cache!)
  (render-app!)
  (println "code updated."))
