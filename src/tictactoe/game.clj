(ns tictactoe.game
  (use [tictactoe.board]
       [tictactoe.io :as io]))

(declare play-move)

(defn play [board players get-move io]
  (if (has-winner? board)
    ((io :present-winner) board)
    (let [new-board (play-move board (first players) get-move io)]
      (play new-board (rest players) get-move io))))

(defn play-move [board player get-move io]
  ((io :show-board) board)
  (let [move (get-move board)]
    (if (valid-move? board move)
      (mark-square board move player)
      board)))
