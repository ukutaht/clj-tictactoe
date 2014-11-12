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

(defn negamax [board mark alpha beta]
  (if (over? board)
    (score board mark)
    (loop [best-score -1
           boards (successors board mark)
           alpha alpha
           beta beta]
      (if (empty? boards)
        best-score
        (let [score (- (negamax (first boards) (opponent-of mark) (- beta) (- alpha)))
              new-best-score (max score best-score)
              new-alpha (max alpha score)]
          (if (>= new-alpha beta)
            new-best-score 
            (recur new-best-score (rest boards) new-alpha beta)))))))

(defn get-computer-move [board mark]
  (loop [valid-moves (valid-moves board)
         best-score -1
         best-move -1]
    (if (or (= best-score 1) (empty? valid-moves))
        best-move
        (let [move (first valid-moves)
              score (- (negamax (mark-square board move mark)
                                (opponent-of mark)
                                -10
                                 10))]
          (if (> score best-score)
            (recur (rest valid-moves) score move)
            (recur (rest valid-moves) best-score best-move))))))
