(ns tictactoe.io_spec
  (use [speclj.core]
       [tictactoe.io :as io]
       [tictactoe.board]))

(describe "command line io"
  (it "displays the board in correct format"
    (let [output (with-out-str (io/show-board empty-board))]
      (should= "         " output)))
  
  (it "displays x-s"
    (let [one-x-board (mark-square empty-board 0 x-player)
          output      (with-out-str (io/show-board one-x-board))]
    
    (should (.contains output (name x-player))))))
