
(ns cumulo-client.component.task
  (:require [hsl.core :refer [hsl]]
            [respo.alias :refer [create-comp div span]]
            [respo.component.debug :refer [comp-debug]]
            [cumulo-client.util.text :refer [text]]))

(def style-task {})

(def style-rm
 {:color (hsl 0 0 100),
  :font-size "12px",
  :background-color (hsl 0 80 60),
  :padding "0 4px",
  :display "inline-block"})

(defn handle-rm [task-id] (fn [e dispatch] (dispatch :task/rm task-id)))

(defn render [task]
  (fn [state mutate]
    (div
      {:style style-task}
      (text (:text task))
      (div
        {:style style-rm, :event {:click (handle-rm (:id task))}}
        (text "rm"))
      (comment comp-debug task {}))))

(def comp-task (create-comp :task render))
