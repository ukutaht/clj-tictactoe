(ns tictactoe.io_spec
  (use [speclj.core]
       [tictactoe.board]
       [tictactoe.spec_helper]
       [tictactoe.players]
       [tictactoe.io :as io]))

(describe "command line io"
  (it "displays the board in correct format"
    (with-redefs [io/clear-screen #()]
      (let [board   [x-mark 1 2 3 4 5 6 7 o-mark]
            output  (with-out-str ((command-line-io :show-board) board))]
        (should= "X | 2 | 3\n4 | 5 | 6\n7 | 8 | O\n" output))))

  (it "presents the winner X"
    (let [output (with-out-str ((command-line-io :present-winner) x-wins-board))]
      (should= "X wins\n" output)))

  (it "presents the winner O"
    (let [output (with-out-str ((command-line-io :present-winner) o-wins-board))]
      (should= "O wins\n" output))))
