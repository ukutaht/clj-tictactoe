(ns tictactoe.computer_player_spec
  (use [speclj.core]
       [tictactoe.spec_helper]
       [tictactoe.computer_player]
       [tictactoe.board]))

(describe "computer player"
  (it "takes the win"
    (let [board [x x 2
                 o o 5
                 6 7 8]]
      (should= 2 (get-computer-move board x))))

  (it "blocks opponent"
    (let [board [x 1 2
                 o o 5
                 x 7 8]]
      (should= 5 (get-computer-move board x)))))
