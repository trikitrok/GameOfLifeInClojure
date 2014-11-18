(ns game-of-life.core-test
  (:use midje.sweet)
  (:use [game-of-life.core]))

(facts "about Game of Life"
       (fact "we can know if a cell is alive"
             (is-alive? [1 2] [[1 2] [4 5]]) => true)
       
       (fact "we can know if a cell is not alive"
             (is-alive? [1 2] [[1 25] [4 5]]) => false)
)
