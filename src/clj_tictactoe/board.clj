(ns clj-tictactoe.board)

(declare empty-square? valid-move? square-played? 
         invalid-move no-winner? all-x-or-o? winners
         is-player? player-mark-if-all-squares-played)

(def no-player :_)
(def x-player  :x)
(def o-player  :o)

(defmacro dbg[x] `(let [x# ~x] (println "dbg:" '~x "=" x#) x#))

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
    (empty-square? board move)))

(defn winner [board]
  (first (filter is-player? (winners board))))

(defn has-winner? [board]
  (not (nil? (winner board))))

(defn draw? [board]
  (and
    (no-winner? board)
    (every? square-played? board)))

(defn- winners [board]
  (map player-mark-if-all-squares-played (repeat board) winning-combinations))

(defn- player-mark-if-all-squares-played [board combination]
  (if (apply = (keep-indexed #(if (some #{%1} combination) %2) board))
    (first (keep-indexed #(if (some #{%1} combination) %2) board)))
  )

(defn- empty-square? [board move]
  (= no-player (square-at board move)))

(defn- is-player? [mark]
  (or 
    (= mark x-player)
    (= mark o-player)))

(defn- square-played? [square]
  (not (= no-player square)))

(defn- no-winner? [board]
  (not (has-winner? board)))

(defn- invalid-move [move]
  (throw (java.lang.IllegalArgumentException. (str "Invalid move " move))))

