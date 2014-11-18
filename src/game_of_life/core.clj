(ns game-of-life.core)

(defn is-alive? [cell living-cells]
  (= cell (some #{cell} living-cells)))