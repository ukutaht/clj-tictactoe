(ns tictactoe.game_spec
  (use [speclj.core]
       [tictactoe.board]
       [tictactoe.players]
       [tictactoe.io :as io]
       [tictactoe.game]))

(declare fake-moves)

(describe "game"
  (defn make-get-move [move]
    (fn [board] move))

  (defn take-from [move-list] 
    (fn [board]
      (let [move (peek (deref move-list))]
        (swap! move-list pop)
         move)))

  (defn play-game-with-moves [moves]
    (with-redefs [fake-moves (atom moves)]
      (with-out-str
        (play empty-board player-marks (take-from fake-moves) command-line-io))))

  (context "playing the whole game"
    (it "terminates with x winner"
      (let [output (play-game-with-moves '(0 3 1 4 2))]
        (should (.contains output "X wins"))))

    (it "terminates with o winner"
      (let [output (play-game-with-moves '(3 0 4 1 6 2))]
        (should (.contains output "O wins")))))

  (context "play move"
    (defn play-x-move-on-empty-board [move]
      (let [get-move (make-get-move move)]
        (play-move empty-board x-player get-move command-line-io)))

    (it "plays the move on board"
      (let [played (play-x-move-on-empty-board 0)]
        (should= (square-at played 0) x-player))))

    (it "does not make the move if it is invalid"
      (let [played (play-x-move-on-empty-board 9)]
        (should= empty-board played))))
