(ns tictactoe.game_spec
  (use [speclj.core]
       [tictactoe.board]
       [tictactoe.game]))

(describe "game"
  (defn make-get-move [move]
    (fn [board] move))

  (context "play move"
    (defn play-x-move-on-empty-board [move]
      (let [get-move (make-get-move move)]
        (play-move empty-board x-player get-move)))

    (it "plays the move on board"
      (let [played (play-x-move-on-empty-board 0)]
        (should= (square-at played 0) x-player))))

    (it "does not make the move if it is invalid"
      (let [played (play-x-move-on-empty-board 9)]
        (should= empty-board played)))
  )
