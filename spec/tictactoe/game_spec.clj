(ns tictactoe.game_spec
  (use [speclj.core]
       [tictactoe.board]
       [tictactoe.players]
       [tictactoe.io :as io]
       [tictactoe.game]))

(declare fake-moves null-io fake-players fake-player take-from)

(describe "game"
  (defn play-game-with-moves [moves]
    (with-redefs [fake-moves (atom moves)]
      (play empty-board (fake-players fake-moves) null-io)))

  (defn fake-players [moves]
    (cycle [ (fake-player x-mark moves) 
             (fake-player o-mark moves)]))

  (defn fake-player [mark moves]
    {:mark mark
     :get-move (take-from moves)})

  (def null-io
    {:show-board (fn [_board])
     :announce-results (fn [_board])
     :notify-invalid-move (fn [])})

  (defn take-from [move-list] 
    (fn [_]
      (let [move (peek (deref move-list))]
        (swap! move-list pop)
         move)))

  (context "playing the whole game"
    (it "terminates with x winner"
      (let [result (play-game-with-moves '(0 3 1 4 2))]
        (should= x-mark (winner result))))

    (it "terminates with o winner"
      (let [result (play-game-with-moves '(3 0 4 1 6 2))]
        (should= o-mark (winner result))))

    (it "terminates with draw"
      (let [result (play-game-with-moves '(0 1 2 4 3 6 5 8 7))]
        (should-be draw? result))))

  (context "play move"
    (defn play-x-move-on-empty-board [move]
      (with-redefs [fake-moves (atom (list move))]
        (let [player (fake-player x-mark fake-moves)]
          (play-move empty-board player null-io))))

    (it "plays the move on board"
      (let [played (play-x-move-on-empty-board 0)]
        (should= (square-at played 0) x-mark)))

    (it "does not make the move if it is invalid"
      (let [played (play-x-move-on-empty-board 9)]
        (should= empty-board played)))

    (it "shows the board"
      (should-invoke null-io {:with [:show-board] :invoke null-io} (play-x-move-on-empty-board 7)))

    (it "notifies io if move is invalid"
      (should-invoke null-io {:with [:notify-invalid-move] :invoke null-io} (play-x-move-on-empty-board 9)))))
