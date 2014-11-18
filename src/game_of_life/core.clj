(ns game-of-life.core)

(defn is-alive? [cell living-cells]
  (= cell (some #{cell} living-cells)))

(defn neighbors [cell]
  #{[0 0] [0 1] [0 2] [1 0] [1 2] [2 0] [2 1] [2 2]})