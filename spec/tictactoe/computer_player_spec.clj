(ns tictactoe.computer_player_spec
  (use [speclj.core]
       [tictactoe.board]
       [tictactoe.spec_helper]
       [tictactoe.computer_player]
       [tictactoe.board]))

(describe "negamax player"
  (context "scores a board state based on the mark"
    (it "scores 1 for win"
      (should= 1 (score x-wins-board x)))

    (it "scores -1 for lose"
      (should= -1 (score o-wins-board x)))

    (it "scores 0 for draw"
      (should= 0 (score draw-board x))))

  (context "board successors"
    (it "is empty when board is full"
      (should= [] (successors draw-board x)))

    (it "has 9 successors for empty board"
      (should= 9 (count (successors empty-board x))))

    (it "the successors are board states after making every valid move"
      (should-contain (mark-square empty-board 0 x) (successors empty-board x))))

  (context "negamax"
    (it "returns the score from the current players perspective if game is over"
      (should= 1 (negamax x-wins-board x -10 10)))

    (it "returns the score from the current players perspective if game is over"
      (should= -1 (negamax x-wins-board o -10 10)))

    (it "returns 1 if the player can win"
      (should= 1 (negamax  [x x 2 3 4 5 6 o o] x -10 10)))

    (it "returns 0 if the player can draw but not win"
      (should= 0 (negamax [x o 2 
                           x o 5 
                           o x x] x -10 10)))

    (it "returns -1 if the player has lost"
      (should= -1 (negamax [x o 2 
                           o o 5 
                           o x x] x -10 10)))))

(describe "optimal player"
  (it "takes win"
    (should= 0 (get-computer-move [0 x x
                                   o o 5
                                   6 7 8] x)))
  (it "blocks opponent win"
    (should= 5 (get-computer-move [0 1 x
                                   o o 5
                                   x 7 8] x)))

  (it "forks"
    (should= 4 (get-computer-move [x o 2
                                   o 4 5
                                   x 7 8] x)))

  (it "forces defense when opponent threatens to fork"
    (should-contain (get-computer-move [0 1 x 
                                        3 o 5 
                                        x 7 8] o)
                    [1 3 5 7] ))

  (it "second move is center"
    (should= 4 (get-computer-move [x 1 2
                                   3 4 5
                                   6 7 8] o)))

  (it "plays corner first"
    (should-contain (get-computer-move empty-board x) [0 2 6 8] )))   
