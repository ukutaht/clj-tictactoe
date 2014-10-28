(ns tictactoe.io_spec
  (use [speclj.core]
       [tictactoe.board]
       [tictactoe.spec_helper]
       [tictactoe.players]
       [tictactoe.player_marks]
       [tictactoe.io :as io]))

(describe "command line io"
  (it "displays the board in correct format"
    (let [board   [x-mark 1 2 3 4 5 6 7 o-mark]
          output  (with-out-str (io/show-board board))]
      (should= "\nX | 2 | 3\n4 | 5 | 6\n7 | 8 | O\n\n" output)))

  (it "presents the winner X"
    (let [output (with-out-str (io/announce-results x-wins-board))]
      (should= "X wins\n" output)))

  (it "presents the winner O"
    (let [output (with-out-str (io/announce-results o-wins-board))]
      (should= "O wins\n" output)))

  (it "announces draw"
    (let [output (with-out-str (io/announce-results draw-board))]
      (should= "It's a draw\n" output)))

  (it "notifies invalid move"
    (let [output (with-out-str (io/notify-invalid-move))]
      (should= "Invalid move, try again\n" output)))

  (describe "console player"
    (it "asks for move"
      (with-in-str "3"
        (let [output (with-out-str (get-human-move))]
          (should= "Your move:" output))))

      (it "reads move from stdin"
        (with-in-str "3"
          (should= 2 (with-redefs [*out* (new java.io.StringWriter)](get-human-move)))))

      (it "returns appropriate keyword if input is complete rubbish"
        (with-in-str "not-a-digit"
          (should= :none (with-redefs [*out* (new java.io.StringWriter)](get-human-move))))))

  (context "game type"
    (it "presents options"
      (let [output (with-out-str (with-in-str "1\n" (get-player-types)))]
        (should-contain "1 - human-vs-human" output)
        (should-contain "2 - human-vs-computer" output)
        (should-contain "3 - computer-vs-human" output)
        (should-contain "4 - computer-vs-computer" output)))

    (it "gets human vs human"
      (with-redefs [*out* (new java.io.StringWriter)]
        (with-in-str "1\n"
          (should= :human-vs-human (get-player-types)))))

    (it "gets human vs computer"
      (with-redefs [*out* (new java.io.StringWriter)]
        (with-in-str "2\n"
          (should= :human-vs-computer (get-player-types)))))

    (it "gets computer vs human"
      (with-redefs [*out* (new java.io.StringWriter)]
        (with-in-str "3\n"
          (should= :computer-vs-human (get-player-types)))))

    (it "gets computer vs computer"
      (with-redefs [*out* (new java.io.StringWriter)]
        (with-in-str "4\n"
          (should= :computer-vs-computer (get-player-types)))))

    (it "recurs until good input is received"
      (with-redefs [*out* (new java.io.StringWriter)]
        (with-in-str "wat\nwrong\n999\n0\n5\n4\n"
          (should= :computer-vs-computer (get-player-types)))))))
