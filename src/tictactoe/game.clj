(ns tictactoe.game
  (use [tictactoe.board]
       [tictactoe.players]
       [tictactoe.io :as io]))

(declare play-move we-have-a-winner)

(defn play [board players io]
  (if (over? board)
    (we-have-a-winner board io)
    (let [new-board (play-move board (first players) io)]
      (play new-board (rest players) io))))

(defn play-move [board player io]
  ((io :show-board) board)
  (let [move ((player :get-move) board)]
    (if (valid-move? board move)
      (mark-square board move (player :mark))
      (do 
        ((io :notify-invalid-move))
        board))))

(defn- we-have-a-winner [board io]
  ((io :announce-results) board)
  board)

(defn -main []
  (play empty-board computer-vs-human command-line-io))
