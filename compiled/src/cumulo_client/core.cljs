
(ns cumulo-client.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.reader :as reader]
            [cljs.core.async :as a :refer [>! <! chan timeout]]
            [shallow-diff.patch :refer [patch]]))

(defonce sender (chan))

(defonce receiver (chan))

(defn setup-socket! [store-ref configs]
  (let [ws-url (:url configs) ws (js/WebSocket. ws-url)]
    (set! ws.onopen (fn [event]))
    (set!
      ws.onclose
      (fn [event]
        (go
          (js/console.error "Socket broken, reloading")
          (<! (timeout 6000))
          (js/location.reload))))
    (set!
      ws.onmessage
      (fn [event]
        (let [changes (reader/read-string event.data)]
          (reset! store-ref (patch @store-ref changes)))))
    (go (loop [] (.send ws (pr-str (<! sender))) (recur)))))

(defn send! [op op-data] (go (>! sender [op op-data])))
