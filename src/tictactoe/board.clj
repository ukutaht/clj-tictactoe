(ns tictactoe.board
  (use [tictactoe.player_marks :as players]))

(declare no-mark? valid-move? player? winner
         invalid-move  has-winner? no-winner?
         resolve-combinations resolve-combination
         pick-indices keep-all-same-lines over?
         get-the-mark all-same? draw? valid-moves)

(def empty-board [0 1 2
                  3 4 5
                  6 7 8])

(def winning-combinations 
  [[0 1 2] [3 4 5] [6 7 8]
   [0 3 6] [1 4 7] [2 5 8]
   [0 4 8] [2 4 6]])

(defn mark-square [board move player]
  (if (valid-move? board move)
    (assoc board move player)
    (invalid-move move)))

(defn square-at [board index]
  (nth board index))

(defn valid-move? [board move]
  (some #{move} (valid-moves board)))

(defn valid-moves [board]
  (if (over? board)
    []
    (filter no-mark? board)))

(defn over? [board]
  (or
    (has-winner? board)
    (draw? board)))

(defn has-winner? [board]
  (not (nil? (winner board))))

(defn draw? [board]
  (and
    (not (has-winner? board))
    (every? player? board)))

(defn winner [board]
  (-> board
      resolve-combinations
      keep-all-same-lines
      get-the-mark))

(defn rows [board]
  (partition 3 board))

(defn- resolve-combinations [board]
  (map resolve-combination (repeat board) winning-combinations))

(defn- keep-all-same-lines [combinations]
  (filter all-same? combinations))

(defn- get-the-mark [combinations]
  (first (first combinations)))

(defn- resolve-combination [board combination]
  (pick-indices board combination))

(defn- pick-indices [board combination]
  (map (partial nth board) combination))

(defn- all-same? [collection]
  (apply = collection))

(defn- player? [mark]
  (or 
    (= mark players/x-mark)
    (= mark players/o-mark)))

(def no-mark? (complement player?))

(defn- invalid-move [move]
  (throw (java.lang.IllegalArgumentException. (str "Invalid move " move))))
