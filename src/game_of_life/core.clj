(ns game-of-life.core
  (:require [clojure.set :only unio]))

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

(defn candidates [living-cells]
   (reduce clojure.set/union (map neighbors living-cells)))