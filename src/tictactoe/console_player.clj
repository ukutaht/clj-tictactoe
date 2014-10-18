(ns tictactoe.console_player)

(declare valid-input? clean ask-for-move)

(defn get-move [board]
  (ask-for-move)
  (let [input (read-line)]
    (if (valid-input? input)
      (clean input)
     :none)))

(defn ask-for-move []
  (print "Your move:")
  (flush))

(defn valid-input? [input]
  (re-matches #"\d" input))

(defn clean [input]
  (-> input
      Integer/parseInt
      (- 1)))
