(ns game-of-life.core
  (:require [clojure.set :only [union difference]]))

(defn is-alive? [cell living-cells]
  (= cell (some #{cell} living-cells)))

(defn neighbors [[x-cell y-cell]]
  (set 
    (for [x (range (dec x-cell) (+ x-cell 2)) 
          y (range (dec y-cell) (+ y-cell 2)) 
          :when (not (and (= x x-cell) (= y y-cell)))] 
      [x y])))

(defn num-alive-neighbors [cell living-cells]
  (count (filter (neighbors cell) living-cells)))

(defn will-survive? [num-alive-neighbors]
  (or (= num-alive-neighbors 3)
      (= num-alive-neighbors 2)))

(defn will-come-to-life? [num-alive-neighbors]
  (= num-alive-neighbors 3))

(defn candidates-to-come-to-life [living-cells]
  (clojure.set/difference 
    (reduce clojure.set/union 
            (map neighbors living-cells))
    (set living-cells)))

(defn surviving-cells [living-cells]
  (set 
    (filter 
      #(will-survive? 
         (num-alive-neighbors % living-cells)) 
      living-cells)))

(defn come-to-life-cells [living-cells]
  (set 
    (filter 
      #(will-come-to-life?
         (num-alive-neighbors % living-cells))
      (candidates-to-come-to-life living-cells))))

(defn next-cells [living-cells]
  (clojure.set/union 
    (surviving-cells living-cells)
    (come-to-life-cells living-cells)))