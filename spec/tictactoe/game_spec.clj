(ns tictactoe.game_spec
  (use [speclj.core]
       [tictactoe.board]
       [tictactoe.players]
       [tictactoe.io :as io]
       [tictactoe.game]))

(declare fake-moves null-io)

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
      (play empty-board player-marks (take-from fake-moves) null-io)))

  (def null-io
    {:show-board (fn [_board])
     :present-winner (fn [_board])})

  (context "playing the whole game"
    (it "terminates with x winner"
      (let [result (play-game-with-moves '(0 3 1 4 2))]
        (should= x-player (winner result))))

    (it "terminates with o winner"
      (let [result (play-game-with-moves '(3 0 4 1 6 2))]
        (should= o-player (winner result))))

    (it "terminates with draw"
      (let [result (play-game-with-moves '(0 1 2 4 3 6 5 8 7))]
        (should (draw? result)))))

  (context "play move"
    (defn play-x-move-on-empty-board [move]
      (let [get-move (make-get-move move)]
        (play-move empty-board x-player get-move null-io)))

    (it "plays the move on board"
      (let [played (play-x-move-on-empty-board 0)]
        (should= (square-at played 0) x-player))))

    (it "does not make the move if it is invalid"
      (let [played (play-x-move-on-empty-board 9)]
        (should= empty-board played))))
