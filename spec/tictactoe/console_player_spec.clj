(ns tictactoe.console_player_spec
  (use [speclj.core]
       [tictactoe.console_player]
       [tictactoe.board]))

(describe "console player"
  (it "asks for move"
    (with-in-str "3"
      (let [output (with-out-str (get-move empty-board))]
        (should= "Your move:" output))))

    (it "reads move from stdin"
      (with-in-str "3"
        (should= 2 (with-redefs [*out* (new java.io.StringWriter)](get-move empty-board)))))

    (it "returns appropriate keyword if input is complete rubbish"
      (with-in-str "not-a-digit"
        (should= :none (with-redefs [*out* (new java.io.StringWriter)](get-move empty-board))))))
