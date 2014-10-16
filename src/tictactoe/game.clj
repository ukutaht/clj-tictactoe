(ns tictactoe.game
  (use [tictactoe.board]
       [tictactoe.io :as io]))

(declare play-move we-have-a-winner)

(defn play [board players get-move io]
  (if (over? board)
    (we-have-a-winner board io)
    (let [new-board (play-move board (first players) get-move io)]
      (play new-board (rest players) get-move io))))

(defn play-move [board player get-move io]
  ((io :show-board) board)
  (let [move (get-move board)]
    (if (valid-move? board move)
      (mark-square board move player)
      board)))

(defn we-have-a-winner [board io]
  ((io :present-winner) board)
  board)
