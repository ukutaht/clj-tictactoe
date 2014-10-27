(ns tictactoe.player_marks)

(def x-mark  :x)
(def o-mark  :o)

(defn opponent-of [mark]
  (if (= mark x-mark)
    o-mark
    x-mark))
