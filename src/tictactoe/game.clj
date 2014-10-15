(ns tictactoe.game
  (use [tictactoe.board]))

(defn play-move [board player get-move]
  (let [move (get-move board)]
    (if (valid-move? board move)
      (mark-square board move player)
      board)))
