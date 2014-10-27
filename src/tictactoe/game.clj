(ns tictactoe.game
  (use [tictactoe.board]
       [tictactoe.players]
       [tictactoe.io :as io]))

(declare play-turn make-move we-have-a-winner)

(defn play [{:keys [board] :as game}]
  (if (over? board)
    (we-have-a-winner board)
    (recur (play-turn game))))

(defn play-turn [{:keys [board players] :as game}]
  (io/show-board board)
  (let [player (first players)
        move ((player :get-move) board)]
    (if (valid-move? board move)
      (make-move game move)
      (do 
        (io/notify-invalid-move)
        game))))

(defn- make-move [{:keys [board players]} move]
  (let [player (first players)]
    {:board (mark-square board move (player :mark))
     :players (rest players)}))

(defn- we-have-a-winner [board]
  (io/show-board board)
  (io/announce-results board)
  board)

(defn -main []
  (play {:board empty-board :players computer-vs-human}))
