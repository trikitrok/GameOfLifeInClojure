(ns game-of-life.core)

(defn is-alive? [cell living-cells]
  (= cell (some #{cell} living-cells)))

(defn neighbors [cell]
  (set 
    (for [x (range 3) 
          y (range 3) 
          :when (not (and (= x 1) (= y 1)))] 
      [x y])))