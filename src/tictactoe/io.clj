(ns tictactoe.io)

(defn show-board [board]
  (print (apply str (map name board))))
