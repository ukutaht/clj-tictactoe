(ns tictactoe.io_spec
  (use [speclj.core]
       [tictactoe.board]
       [tictactoe.spec_helper]
       [tictactoe.players]
       [tictactoe.io :as io]))

(describe "command line io"
  (it "displays the board in correct format"
    (let [board (-> empty-board (mark-square 0 x-player) (mark-square 8 o-player))
          output      (with-out-str (io/show-board board))]
      (should= "X | 2 | 3\n4 | 5 | 6\n7 | 8 | O\n" output)))

  (it "presents the winner"
    (let [output (with-out-str (io/present-winner x-wins-board))]
      (should= "X wins\n" output))))
