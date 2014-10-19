(ns tictactoe.computer_player
  (use [tictactoe.board]
       [tictactoe.player_marks]))

(declare negamax opponent-of score successors negamax-values index-of-max)

(defn get-computer-move [board]
  (let [negamax-values (negamax-values board x-mark)
        best-move-index (index-of-max negamax-values)]
       (nth (valid-moves board) best-move-index)))

(defn negamax-values [board mark]
  (map #(- (negamax %1 (opponent-of mark))) (successors board mark))) 

(defn negamax [board mark]
  (if (over? board)
    (score board mark)
    (apply max (negamax-values board mark))))

(defn successors [board mark]
  (map #(mark-square board %1 mark) (valid-moves board)))

(defn index-of-max [coll]
  (.indexOf coll (apply max coll)))

(defn score [board mark]
  (cond 
    (draw? board) 0
    (= (winner board) mark) 1
    :else -1))

(defn opponent-of [mark]
  (if (= mark x-mark)
    o-mark
    x-mark))
