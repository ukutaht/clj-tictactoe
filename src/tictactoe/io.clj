(ns tictactoe.io
  (use [tictactoe.board]
       [clojure.string :only (upper-case)]))

(declare show-board present-winner lines render-line format-cell)

(defn show-board [board]
  (print (apply str (lines board))))

(defn present-winner [board]
  (println (str (format-cell (winner board)) " wins")))

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

(def command-line-io {:show-board show-board
                      :present-winner present-winner})
