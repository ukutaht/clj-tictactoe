(ns tictactoe.io
  (use [tictactoe.board]
       [clojure.string :only (upper-case)]))

(declare clear-screen show-board present-winner lines render-line format-cell)

(defstruct GameIO :show-board :present-winner)

(defn show-board [board]
  (clear-screen)
  (print (apply str (lines board))))

(defn present-winner [board]
  (println (str (format-cell (winner board)) " wins")))

(defn- lines [board]
  (map render-line (rows board)))

(defn- render-line [row]
  (let [formatted-row (map format-cell row)]
    (str (clojure.string/join " | " formatted-row) "\n")))

(defn clear-screen []
  (print "\u001b[2J")
  (print "\u001B[0;0f"))

(defmulti format-cell class)

(defmethod format-cell Number [index]
  (+ 1 index))

(defmethod format-cell clojure.lang.Keyword [mark]
  (upper-case (name mark)))

(def command-line-io (struct GameIO show-board present-winner))
