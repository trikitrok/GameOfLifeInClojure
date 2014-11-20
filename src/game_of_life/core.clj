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

(defn has-cell? [loc cells] 
  (= loc (some #{loc} cells)))

(defn will-have-a-cell? [loc cells]
  (let [num-neighbors (num-neighbors-being-a-cell loc cells)]
    (or (= num-neighbors 3)
        (and (= num-neighbors 2)
             (has-cell? loc cells)))))

(defn all-neighbors-locations [cells]
  (reduce clojure.set/union 
            (map neighbors cells)))

(defn next-cells [cells]
  (set 
    (filter 
      #(will-have-a-cell? % cells)
      (all-neighbors-locations cells))))

(defn game-of-life [cells num-iter]
  (take num-iter (iterate next-cells cells)))
