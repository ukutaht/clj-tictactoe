(ns tictactoe.computer_player
  (use [tictactoe.board]
       [tictactoe.player_marks]))

(defn successors [board mark]
  (map #(mark-square board % mark) (valid-moves board)))

(defn score [board mark]
  (let [winner (winner board)]
    (cond 
      (= winner mark) 1 
      (= winner (opponent-of mark)) -1
      :else 0)))

(defn negamax [board mark]
  (if (over? board)
    (score board mark)
    (apply max (map #(- (negamax % (opponent-of mark))) (successors board mark))))) 

(defn get-computer-move [board mark]
  (loop [valid-moves (valid-moves board)
         best-score -1
         best-move -1]
    (if (or (= best-score 1) (empty? valid-moves))
        best-move
        (let [move (first valid-moves)
              score (- (negamax (mark-square board move mark) (opponent-of mark)))]
          (if (> score best-score)
            (recur (rest valid-moves) score move)
            (recur (rest valid-moves) best-score best-move))))))
