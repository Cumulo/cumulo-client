
(ns cumulo-client.component.todolist
  (:require [respo.alias :refer [create-comp div input span]]
            [respo.component.debug :refer [comp-debug]]
            [cumulo-client.component.task :refer [comp-task]]
            [cumulo-client.util.text :refer [text]]
            [hsl.core :refer [hsl]]))

(defn init-state [tasks] "")

(defn update-state [state new-text] new-text)

(def style-button
 {:line-height 2,
  :color (hsl 0 0 100),
  :background-color (hsl 200 80 50),
  :padding "0 8px",
  :display "inline-block"})

(def style-input
 {:font-size "14px",
  :padding "0 8px",
  :outline "none",
  :border "none",
  :font-family "Verdana"})

(defn handle-add [state mutate]
  (fn [e dispatch] (dispatch :task/add state) (mutate "")))

(defn handle-input [mutate] (fn [e dispatch] (mutate (:value e))))

(defn render [tasks]
  (fn [state mutate]
    (div
      {}
      (div
        {}
        (input
          {:style style-input,
           :event {:input (handle-input mutate)},
           :attrs {:value state}})
        (div
          {:style style-button,
           :event {:click (handle-add state mutate)}}
          (text "Add")))
      (div
        {}
        (->>
          tasks
          (map (fn [entry] [(key entry) (comp-task (val entry))]))
          (into (sorted-map)))))))

(def comp-todolist
 (create-comp :todolist init-state update-state render))
