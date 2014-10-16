(ns tictactoe.console_player_spec
  (use [speclj.core]
       [tictactoe.console_player]
       [tictactoe.board]))

(describe "console player"
  (it "reads move from stdin"
    (with-in-str "3"
      (should= 2 (get-move empty-board))))

  (it "returns appropriate keyword if input is complete rubbish"
    (with-in-str "not-a-digit"
      (should= :none (get-move empty-board)))))
