(ns tictactoe.io
  (use [tictactoe.board]
       [clojure.string :only (upper-case)]))

(declare clear-screen show-board present-winner lines render-line format-cell present-draw)

(defstruct GameIO :show-board :announce-results :notify-invalid-move)

(defn show-board [board]
  (println)
  (println (apply str (lines board))))

(defn announce-results [board]
  (if (has-winner? board)
    (present-winner board)
    (present-draw)))

(defn notify-invalid-move []
  (println "Invalid move, try again"))

(defn present-winner [board]
  (println (str (format-cell (winner board)) " wins")))

(defn- present-draw []
  (println "It's a draw"))

(defn- lines [board]
  (map render-line (rows board)))

(defn- render-line [row]
  (let [formatted-row (map format-cell row)]
    (str (clojure.string/join " | " formatted-row) "\n")))

(defmulti format-cell class)

(defmethod format-cell Number [index]
  (+ 1 index))

(defmethod format-cell clojure.lang.Keyword [mark]
  (upper-case (name mark)))

(def command-line-io (struct GameIO show-board announce-results notify-invalid-move))
