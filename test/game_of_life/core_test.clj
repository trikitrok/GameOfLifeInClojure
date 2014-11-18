(ns game-of-life.core-test
  (:use midje.sweet)
  (:use [game-of-life.core]))

(facts 
  "about Game of Life"
  
  (facts 
    "about living cells"
    
    (fact "we can know if a cell is alive"
          (is-alive? [1 2] [[1 2] [4 5]]) => true)
    
    (fact "we can know if a cell is not alive"
          (is-alive? [1 2] [[1 25] [4 5]]) => false))
  
  (facts 
    "about cells neighbors"
    
    (fact "we can know the neighbors of a cell"
          (neighbors [1 1]) => #{[0 0] [0 1] [0 2] [1 0] [1 2] [2 0] [2 1] [2 2]}
          (neighbors [0 0]) => #{[-1 -1] [-1 0] [-1 1] [0 -1] [0 1] [1 -1] [1 0] [1 1]})
    
    (fact "we can know how many living neighbors a cell has"
          (num-alive-neighbors [1 2] [[1 2] [4 5] [1 3]]) => 1
          (num-alive-neighbors [1 2] [[1 2] [1 1] [1 3]]) => 2
          (num-alive-neighbors [10 20] [[1 2] [1 1] [1 3]]) => 0))
  
  (facts 
    "about game of life rules"
    
    (fact "a cell with enough living neighbors will survive"
          (will-survive? 3) => true
          (will-survive? 2) => true)
    
    (fact "a cell with too few living neighbors will not survive"
          (will-survive? 1) => false)
    
    (fact "a cell with too many living neighbors will not survive"
          (will-survive? 4) => false)
    
    (fact "a cell with the right amount of living neighbors will come to life"
          (will-come-to-life? 3) => true
          (will-come-to-life? 4) => false
          (will-come-to-life? 2) => false))
  
  (facts
    "about candidates to come to life in next generation"
    
    (fact "the candidates are the living cells neighbors"
          (candidates-to-come-to-life []) => #{}
          (candidates-to-come-to-life [[1 1]]) => #{[0 0] [0 1] [0 2] [1 0] [1 2] [2 0] [2 1] [2 2]})
    
    (fact "no living cells are included in the candidates"
          (candidates-to-come-to-life [[1 1] [0 0]]) => #{[0 1] [0 2] [1 0] [1 2] [2 0] [2 1] [2 2] 
                                                          [-1 -1] [-1 0] [-1 1] [0 -1] [1 -1] }))
  
  (facts 
    "about surviving cells in next generation"
    
    (fact "the surviving cells are the living cells with enoug neighbors"
          (surviving-cells []) => [])))
