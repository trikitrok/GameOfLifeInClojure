(ns game-of-life.core-test
  (:use midje.sweet)
  (:use [game-of-life.core]))

(facts 
  "about Game of Life"
  
  (facts 
    "about cells neighbors"
    
    (fact 
      "we can know the neighbors of a cell"
      (neighbors [1 1]) => #{[0 0] [0 1] [0 2] [1 0] [1 2] [2 0] [2 1] [2 2]}
      (neighbors [0 0]) => #{[-1 -1] [-1 0] [-1 1] [0 -1] [0 1] [1 -1] [1 0] [1 1]})
    
    (fact 
      "we can know how many neighbors are a cell"
      (num-neighbors-being-a-cell [1 2] [[1 2] [4 5] [1 3]]) => 1
      (num-neighbors-being-a-cell [1 2] [[1 2] [1 1] [1 3]]) => 2
      (num-neighbors-being-a-cell [10 20] [[1 2] [1 1] [1 3]]) => 0))
  
  (facts 
    "about game of life rules"
    
    (fact 
      "a location with a cell will have a cell in next generation if it has the right number of neighbors"
      (will-have-a-cell? [1 2] [[1 2] [1 1] [1 3]]) => true
      (will-have-a-cell? [1 2] [[1 2] [1 1]]) => false
      (will-have-a-cell? [1 2] [[1 2] [1 1] [1 3] [2 2]]) => true)
    
    (fact 
      "a location without a cell will have a cell in next generation if it has the right number of neighbors"
      (will-have-a-cell? [1 2] [[1 1] [1 3]]) => false)
    
    (fact 
      "a cell with enough neighbors with cells will go on being a cell in the next generation"
      (will-go-on-being-a-cell? 3) => true
      (will-go-on-being-a-cell? 2) => true)
    
    (fact 
      "a cell with too few neighbors with cells will not go on being a cell in the next generation"
      (will-go-on-being-a-cell? 1) => false)
    
    (fact 
      "a cell with too many neighbors with cells will not go on being a cell in the next generation"
      (will-go-on-being-a-cell? 4) => false)
    
    (fact 
      "a candidate with the right amount of neighbors with cells will be a cell in the next generation"
      (will-be-a-cell? 3) => true
      (will-be-a-cell? 4) => false
      (will-be-a-cell? 2) => false))
  
  (facts
    "about candidates to be a cell in next generation"
    
    (fact 
      "the candidates are the cells neighbors"
      (candidates-to-be-a-cell []) => #{}
      (candidates-to-be-a-cell [[1 1]]) => #{[0 0] [0 1] [0 2] [1 0] [1 2] [2 0] [2 1] [2 2]})
    
    (fact 
      "no cells are included in the candidates"
      (candidates-to-be-a-cell [[1 1] [0 0]]) => #{[0 1] [0 2] [1 0] [1 2] [2 0] [2 1] [2 2] 
                                                   [-1 -1] [-1 0] [-1 1] [0 -1] [1 -1] }))
  
  (facts 
    "about cells keep being cells in next generation"
    
    (fact 
      "no cells keep being cells when there are no cells"
      (keep-being-cells []) => #{})
    
    (fact 
      "no cells keep being cells because they do not have enough neighbors that are cells"
      (keep-being-cells [[2 2] [1 1]]) => #{})
    
    (fact 
      "the cells keep being cells are the cells with just enough neighbors that are cells"
      (keep-being-cells [[2 2] [0 0] [1 1] [-1 -1]]) => #{[0 0] [1 1]}
      (keep-being-cells [[0 0] [0 1] [1 0] [1 1]]) => #{[0 0] [0 1] [1 0] [1 1]}))
  
  (facts 
    "about new cells in next generation"
    
    (fact 
      "the new cells are neighbors of the cells with enough neighbors that are cells"
      (new-cells []) => #{}
      (new-cells [[2 2] [0 0] [1 1] [-1 -1] [1 0]]) => #{[0 1] [0 -1] [2 1]}
      (new-cells [[0 1] [1 0] [1 1]]) => #{[0 0]}))
  (facts 
    "about next generation cells"
    
    (fact 
      "the next generation cells are the union of the cells that keep being cells and the new cells"
      (next-cells []) => #{}
      (next-cells [[2 2] [0 0] [1 1] [-1 -1] [1 0]]) => #{[0 1] [0 -1] [2 1] [1 0] [0 0] [1 1]}
      (next-cells [[0 1] [1 0] [1 1]]) => #{[0 0] [0 1] [1 0] [1 1]}))
  
  (facts
    "about Still lifes"
    
    (fact 
      "a Block is a still life 
      (http://en.wikipedia.org/wiki/Still_life_%28cellular_automaton%29#Blocks)" 
      (let [a-block #{[0 0] [0 1] [1 1] [1 0]}
            five-gens (game-of-life a-block 5)]     
        (nth five-gens 1) => a-block
        (nth five-gens 2) => a-block
        (nth five-gens 3) => a-block
        (nth five-gens 4) => a-block))
    
    (fact 
      "a Beehive is a still life 
      (http://www.conwaylife.com/wiki/Beehive)"
      (let [a-beehive #{[0 1] [0 2] [1 0] [1 3] [2 1] [2 2]}
            five-gens (game-of-life a-beehive 5)]     
        (nth five-gens 1) => a-beehive
        (nth five-gens 2) => a-beehive
        (nth five-gens 3) => a-beehive
        (nth five-gens 4) => a-beehive))
    
    (fact 
      "a Loaf is a still life 
      (http://en.wikipedia.org/wiki/Still_life_%28cellular_automaton%29#Loaves)"
      (let [a-loaf #{[0 1] [0 2] [1 0] [1 3] [2 1] [2 3] [3 2]}
            five-gens (game-of-life a-loaf 5)] 
        (nth five-gens 1) => a-loaf
        (nth five-gens 2) => a-loaf
        (nth five-gens 3) => a-loaf
        (nth five-gens 4) => a-loaf))
    
    (fact 
      "a Boat is a still life 
      (http://commons.wikimedia.org/wiki/File:Game_of_life_boat.svg)"
      (let [a-boat #{[0 0] [0 1] [1 0] [1 2] [2 1]}
            five-gens (game-of-life a-boat 5)] 
        (nth five-gens 1) => a-boat
        (nth five-gens 2) => a-boat
        (nth five-gens 3) => a-boat
        (nth five-gens 4) => a-boat)))
  
  (facts
    "about Oscillators"
    (fact 
      "a Blinker oscillates with period two 
      (http://en.wikipedia.org/wiki/File:Game_of_life_blinker.gif)"
      (let [a-blinker #{[0 1] [0 2] [0 0]}
            a-blinker-next #{[0 1] [1 1] [-1 1]}
            five-gens (game-of-life a-blinker 5)]
        (nth five-gens 1) => a-blinker-next
        (nth five-gens 2) => a-blinker
        (nth five-gens 3) => a-blinker-next
        (nth five-gens 4) => a-blinker))
    
    (fact 
      "a Toad oscillates with period two 
      (http://en.wikipedia.org/wiki/File:Game_of_life_toad.gif)"
      (let [a-toad #{[0 3] [1 0] [1 2] [0 2] [1 1] [0 1]}
            a-toad-next #{[0 3] [1 0] [0 0] [-1 2] [1 3] [2 1]}
            five-gens (game-of-life a-toad 5)]
        (nth five-gens 1) => a-toad-next
        (nth five-gens 2) => a-toad
        (nth five-gens 3) => a-toad-next
        (nth five-gens 4) => a-toad)))
  
  (facts
    "about Gliders"
    (fact 
      "a Glider moves diagonally with period 4 
      (http://en.wikipedia.org/wiki/File:Game_of_life_animated_glider.gif)"
      (let [a-glider #{[0 0] [0 1] [0 2] [1, 0] [2, 1]}
            five-gens (game-of-life a-glider 5)]
        (nth five-gens 1) => #{[0 0] [0 1] [1 0] [-1 1] [1 2]}
        (nth five-gens 2) => #{[0 0] [1 0] [-1 1] [-1 0] [0 2]}
        (nth five-gens 3) => #{[0 0] [-1 1] [-1 0] [1 1] [0 -1]}
        (nth five-gens 4) => #{[-1 1] [-1 0] [0 -1] [1 0] [-1 -1]}))))
