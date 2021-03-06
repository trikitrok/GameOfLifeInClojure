(ns game-of-life.core)

(defn neighbors [[x-cell y-cell]]
  (set (for [x (range (dec x-cell) (+ x-cell 2)) 
             y (range (dec y-cell) (+ y-cell 2)) 
             :when (not (and (= x x-cell) (= y y-cell)))] 
         [x y])))

(defn num-neighbors-with-a-cell [loc cells]
  (count (filter (neighbors loc) cells)))

(defn has-cell? [loc cells] 
  (= loc (some #{loc} cells)))

(defn will-have-cell? [loc cells]
  (let [num-neighbors (num-neighbors-with-a-cell loc cells)]
    (or (= num-neighbors 3)
        (and (= num-neighbors 2)
             (has-cell? loc cells)))))

(defn locations [cells]
  (reduce clojure.set/union (map neighbors cells)))

(defn next-cells [cells]
  (set (filter #(will-have-cell? % cells) (locations cells))))

(defn game-of-life [cells num-iter]
  (take num-iter (iterate next-cells cells)))
