
(ns cumulo-client.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.reader :as reader]
            [cljs.core.async :as a :refer [>! <! chan timeout]]
            [shallow-diff.patch :refer [patch]]))

(defonce sender (chan))

(defn send! [op op-data] (go (>! sender [op op-data])))

(defn setup-socket! [store-ref configs]
  (let [ws-url (:url configs)
        ws (js/WebSocket. ws-url)
        handle-close! (if (fn? (:on-close! configs)) (:on-close! configs) identity)
        handle-open! (if (fn? (:on-open! configs)) (:on-open! configs) identity)]
    (set! ws.onopen (fn [event] (handle-open! event)))
    (set! ws.onclose (fn [event] (handle-close! event)))
    (set!
     ws.onmessage
     (fn [event]
       (let [changes (reader/read-string event.data)]
         (reset! store-ref (patch @store-ref changes)))))
    (go (loop [] (.send ws (pr-str (<! sender))) (recur)))))

(defonce receiver (chan))
