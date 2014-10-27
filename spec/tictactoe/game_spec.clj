(ns tictactoe.game_spec
  (use [speclj.core]
       [tictactoe.board]
       [tictactoe.players]
       [tictactoe.player_marks]
       [tictactoe.io :as io]
       [tictactoe.game]))

(declare fake-moves null-io fake-players fake-player take-from)

(describe "game"
  (defn play-game-with-moves [moves]
    (with-redefs [fake-moves (atom moves)
                  *out* (new java.io.StringWriter)]
      (play empty-board (fake-players fake-moves))))

  (defn fake-players [moves]
    (cycle [ (fake-player x-mark moves) 
             (fake-player o-mark moves)]))

  (defn fake-player [mark moves]
    {:mark mark
     :get-move (take-from moves)})

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

    (it "announces winner with o winner"
      (should-invoke io/announce-results {} (play-game-with-moves '(0 1 2 4 3 6 5 8 7))))

  (context "play move"
    (defn play-x-move-on-empty-board [move]
      (with-redefs [fake-moves (atom (list move))
                    *out*  (new java.io.StringWriter)]
        (let [player (fake-player x-mark fake-moves)]
          (play-move empty-board player))))

    (it "plays the move on board"
      (let [played (play-x-move-on-empty-board 0)]
        (should= (square-at played 0) x-mark)))

    (it "does not make the move if it is invalid"
      (let [played (play-x-move-on-empty-board 9)]
        (should= empty-board played)))

    (it "shows the board"
      (should-invoke io/show-board {} (play-x-move-on-empty-board 7)))

    (it "notifies io if move is invalid"
      (should-invoke io/notify-invalid-move {} (play-x-move-on-empty-board 9)))))
