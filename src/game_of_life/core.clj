(ns game-of-life.core
  (:require [clojure.set :only [union difference]]))

(defn neighbors [[x-cell y-cell]]
  (set 
    (for [x (range (dec x-cell) (+ x-cell 2)) 
          y (range (dec y-cell) (+ y-cell 2)) 
          :when (not (and (= x x-cell) (= y y-cell)))] 
      [x y])))

(defn num-alive-neighbors [cell cells]
  (count (filter (neighbors cell) cells)))

(defn will-survive? [num-alive-neighbors]
  (or (= num-alive-neighbors 3)
      (= num-alive-neighbors 2)))

(defn will-come-to-life? [num-alive-neighbors]
  (= num-alive-neighbors 3))

(defn candidates-to-come-to-life [cells]
  (clojure.set/difference 
    (reduce clojure.set/union 
            (map neighbors cells))
    (set cells)))

(defn surviving-cells [cells]
  (set 
    (filter 
      #(will-survive? 
         (num-alive-neighbors % cells)) 
      cells)))

(defn come-to-life-cells [cells]
  (set 
    (filter 
      #(will-come-to-life?
         (num-alive-neighbors % cells))
      (candidates-to-come-to-life cells))))

(defn next-cells [cells]
  (clojure.set/union 
    (surviving-cells cells)
    (come-to-life-cells cells)))