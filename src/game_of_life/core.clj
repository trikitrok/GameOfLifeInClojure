(ns game-of-life.core
  (:require [clojure.set :only [union difference]]))

(defn neighbors [[x-cell y-cell]]
  (set 
    (for [x (range (dec x-cell) (+ x-cell 2)) 
          y (range (dec y-cell) (+ y-cell 2)) 
          :when (not (and (= x x-cell) (= y y-cell)))] 
      [x y])))

(defn num-neighbors-being-a-cell [cell cells]
  (count (filter (neighbors cell) cells)))

(defn will-go-on-being-a-cell? [num-neighbors-being-a-cell]
  (or (= num-neighbors-being-a-cell 3)
      (= num-neighbors-being-a-cell 2)))

(defn will-be-a-cell? [num-neighbors-being-a-cell]
  (= num-neighbors-being-a-cell 3))

(defn candidates-to-be-a-cell [cells]
  (clojure.set/difference 
    (reduce clojure.set/union 
            (map neighbors cells))
    (set cells)))

(defn keep-being-cells [cells]
  (set 
    (filter 
      #(will-go-on-being-a-cell? 
         (num-neighbors-being-a-cell % cells)) 
      cells)))

(defn new-cells [cells]
  (set 
    (filter 
      #(will-be-a-cell?
         (num-neighbors-being-a-cell % cells))
      (candidates-to-be-a-cell cells))))

(defn next-cells [cells]
  (clojure.set/union 
    (keep-being-cells cells)
    (new-cells cells)))