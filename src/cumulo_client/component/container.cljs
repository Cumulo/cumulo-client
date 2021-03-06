
(ns cumulo-client.component.container
  (:require [hsl.core :refer [hsl]]
            [respo.alias :refer [create-comp div span]]
            [cumulo-client.component.todolist :refer [comp-todolist]]))

(defn render [store] (fn [state mutate] (div {} (comp-todolist (:tasks store)))))

(def comp-container (create-comp :container render))
