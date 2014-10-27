(ns tictactoe.game
  (use [tictactoe.board]
       [tictactoe.players]
       [tictactoe.console_player]
       [tictactoe.computer_player]
       [tictactoe.io :as io]))

(declare play-turn make-move we-have-a-winner get-move current-player)

(defn play [{:keys [board] :as game}]
  (if (over? board)
    (we-have-a-winner board)
    (recur (play-turn game))))

(defn play-turn [{:keys [board] :as game}]
  (io/show-board board)
  (let [move (get-move game)]
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

(defmulti get-move (fn [game] (:type (current-player game))))

(defmethod get-move :human [game]
  (get-human-move))

(defmethod get-move :computer [game]
  (get-computer-move (:board game) (:mark (current-player game))))

(defn- current-player [game]
  (first (:players game)))

(defn -main []
  (play {:board empty-board :players computer-vs-human}))
