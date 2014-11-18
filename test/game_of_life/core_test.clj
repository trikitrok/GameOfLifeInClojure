(ns game-of-life.core-test
  (:use midje.sweet)
  (:use [game-of-life.core]))

(facts 
  "about Game of Life"
  
  (facts 
    "about cells neighbors"
    
    (fact "we can know the neighbors of a cell"
          (neighbors [1 1]) => #{[0 0] [0 1] [0 2] [1 0] [1 2] [2 0] [2 1] [2 2]}
          (neighbors [0 0]) => #{[-1 -1] [-1 0] [-1 1] [0 -1] [0 1] [1 -1] [1 0] [1 1]})
    
    (fact "we can know how many neighbors are a cell"
          (num-neighbors-being-a-cell [1 2] [[1 2] [4 5] [1 3]]) => 1
          (num-neighbors-being-a-cell [1 2] [[1 2] [1 1] [1 3]]) => 2
          (num-neighbors-being-a-cell [10 20] [[1 2] [1 1] [1 3]]) => 0))
  
  (facts 
    "about game of life rules"
    
    (fact "a cell with enough neighbors with cells will go on being a cell in the next generation"
          (will-go-on-being-a-cell? 3) => true
          (will-go-on-being-a-cell? 2) => true)
    
    (fact "a cell with too few neighbors with cells will not go on being a cell in the next generation"
          (will-go-on-being-a-cell? 1) => false)
    
    (fact "a cell with too many neighbors with cells will not go on being a cell in the next generation"
          (will-go-on-being-a-cell? 4) => false)
    
    (fact "a candidate with the right amount of neighbors with cells will be a cell in the next generation"
          (will-be-a-cell? 3) => true
          (will-be-a-cell? 4) => false
          (will-be-a-cell? 2) => false))
  
  (facts
    "about candidates to be a cell in next generation"
    
    (fact "the candidates are the cells neighbors"
          (candidates-to-be-a-cell []) => #{}
          (candidates-to-be-a-cell [[1 1]]) => #{[0 0] [0 1] [0 2] [1 0] [1 2] [2 0] [2 1] [2 2]})
    
    (fact "no cells are included in the candidates"
          (candidates-to-be-a-cell [[1 1] [0 0]]) => #{[0 1] [0 2] [1 0] [1 2] [2 0] [2 1] [2 2] 
                                                       [-1 -1] [-1 0] [-1 1] [0 -1] [1 -1] }))
  
  (facts 
    "about cells keep being cells in next generation"
    
    (fact "no cells keep being cells when there are no cells"
          (keep-being-cells []) => #{})
    
    (fact "no cells keep being cells because they do not have enough neighbors that are cells"
          (keep-being-cells [[2 2] [1 1]]) => #{})
    
    (fact "the cells keep being cells are the cells with just enough neighbors that are cells"
          (keep-being-cells [[2 2] [0 0] [1 1] [-1 -1]]) => #{[0 0] [1 1]}
          (keep-being-cells [[0 0] [0 1] [1 0] [1 1]]) => #{[0 0] [0 1] [1 0] [1 1]}))
  
  (facts 
    "about new cells in next generation"
    
    (fact "the new cells are neighbors of the cells with enough neighbors that are cells"
          (new-cells []) => #{}
          (new-cells [[2 2] [0 0] [1 1] [-1 -1] [1 0]]) => #{[0 1] [0 -1] [2 1]}
          (new-cells [[0 1] [1 0] [1 1]]) => #{[0 0]}))
  (facts 
    "about next generation cells"
    
    (fact "the next generation cells are the union of the cells that keep being cells and the new cells"
          (next-cells []) => #{}
          (next-cells [[2 2] [0 0] [1 1] [-1 -1] [1 0]]) => #{[0 1] [0 -1] [2 1] [1 0] [0 0] [1 1]}
          (next-cells [[0 1] [1 0] [1 1]]) => #{[0 0] [0 1] [1 0] [1 1]})))
