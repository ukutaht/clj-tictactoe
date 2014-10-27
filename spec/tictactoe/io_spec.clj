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
      (should= "Invalid move, try again\n" output))))
