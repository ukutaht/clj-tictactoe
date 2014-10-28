(ns tictactoe.game_spec
  (use [speclj.core]
       [tictactoe.board]
       [tictactoe.players]
       [tictactoe.computer_player :as computer]
       [tictactoe.player_marks]
       [tictactoe.io :as io]
       [tictactoe.game :as game]))

(declare fake-moves null-io fake-players fake-player take-from)

(describe "game"
  (around [it] (with-redefs [*out*  (new java.io.StringWriter)]))

  (defn play-game-with-moves [moves]
    (with-redefs [game/get-move (take-from (atom moves))]
      (play {:board empty-board :players fake-players})))

  (defn fake-player [mark]
    {:mark mark
     :type :irrelevant})

  (def fake-players
    (cycle [(fake-player x-mark) 
            (fake-player o-mark)]))

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

  (context "play turn"
    (defn play-x-turn-on-empty-board [move]
      (with-redefs [game/get-move (take-from (atom (list move)))]
        (play-turn {:board empty-board :players [(fake-player x-mark)]})))

    (it "plays the move on board"
      (let [played (:board (play-x-turn-on-empty-board 0))]
        (should= (square-at played 0) x-mark)))

    (it "does not make the move if it is invalid"
      (let [played (:board (play-x-turn-on-empty-board 9))]
        (should= empty-board played)))

    (it "returns the rest of the players if valid move"
      (let [rest-players (:players (play-x-turn-on-empty-board 7))]
        (should-be empty? rest-players)))

    (it "does not rotate players if invalid move"
      (let [rest-players (:players (play-x-turn-on-empty-board 9))]
        (should-not (empty? rest-players))))

    (it "dispatches to io if human turn"
      (should-invoke io/get-human-move (play-turn {:board empty-board :players human-vs-human})))

    (it "dispatches to computer player if computer turn"
      (should-invoke computer/get-computer-move (play-turn {:board empty-board :players computer-vs-human})))

    (it "shows the board"
      (should-invoke io/show-board {} (play-x-turn-on-empty-board 7)))

    (it "notifies io if move is invalid"
      (should-invoke io/notify-invalid-move {} (play-x-turn-on-empty-board 9)))))
