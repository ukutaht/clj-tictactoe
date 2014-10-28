(ns tictactoe.io
  (use [tictactoe.board]
       [tictactoe.players]
       [clojure.string :only (upper-case)]))

(declare player-type-options ask-for-move valid-input? clean show-board present-winner lines render-line format-cell present-draw)

(defn get-player-types []
  (println "Please select player types")
  (println (player-type-options))
  (let [options (vec (keys player-combinations))
        input (read-line)]
    (if (and (valid-input? input) 
             (contains? options (clean input)))
      (nth options (clean input))
      (recur))))

(defn get-human-move []
  (ask-for-move)
  (let [input (read-line)]
    (if (valid-input? input)
      (clean input)
     :none)))

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

(defn player-type-options []
  (->> player-combinations
      keys
      (map-indexed #(str (inc %1) " - " (name %2)))
      (interpose "\n")
      (apply str)))

(defn- ask-for-move []
  (print "Your move:")
  (flush))

(defn- valid-input? [input]
  (re-matches #"\d" input))

(defn- clean [input]
  (-> input
      Integer/parseInt
      (- 1)))


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
