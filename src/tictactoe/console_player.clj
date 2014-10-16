(ns tictactoe.console_player)

(declare valid-input? clean)

(defn get-move [board]
  (print "Your move:")
  (flush)
  (let [input (read-line)]
    (if (valid-input? input)
      (clean input)
     :none)))

(defn valid-input? [input]
  (re-matches #"\d" input))

(defn clean [input]
  (-> input
      Integer/parseInt
      (- 1)))
