(ns tictactoe.board)

(declare no-player? valid-move? player? winner
         invalid-move  no-winner?  resolve-combinations
         resolve-combination pick-indices
         keep-all-same-lines remove-non-players get-the-mark
         all-same?)

(def x-player  :x)
(def o-player  :o)

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
  (and 
    (contains? board move)
    (no-player? (square-at board move))))

(defn has-winner? [board]
  (not (nil? (winner board))))

(def no-winner? (complement has-winner?))

(defn draw? [board]
  (and
    (no-winner? board)
    (every? player? board)))

(defn winner [board]
  (-> board
      resolve-combinations
      keep-all-same-lines
      remove-non-players
      get-the-mark))

(defn- resolve-combinations [board]
  (map resolve-combination (repeat board) winning-combinations))

(defn- keep-all-same-lines [combinations]
  (filter all-same? combinations))

(defn- remove-non-players [combinations]
  (filter (partial every? player?) combinations))

(defn get-the-mark [combinations]
  (first (first combinations)))

(defn- resolve-combination [board combination]
  (pick-indices board combination))

(defn- pick-indices [board combination]
  (map (partial nth board) combination))

(defn- all-same? [collection]
  (apply = collection))

(defn- player? [mark]
  (or 
    (= mark x-player)
    (= mark o-player)))

(def no-player? (complement player?))

(defn- invalid-move [move]
  (throw (java.lang.IllegalArgumentException. (str "Invalid move " move))))
