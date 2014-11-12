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

(defn negamax [alpha beta board mark]
  (if (over? board)
    (score board mark)
    (loop [best-score -1
           boards (successors board mark)
           alpha alpha
           beta beta]
      (if (empty? boards)
        best-score
        (let [score (- (negamax (- beta) (- alpha) (first boards) (opponent-of mark)))
              new-best-score (max score best-score)
              new-alpha (max alpha score)]
          (if (>= new-alpha beta)
            new-best-score 
            (recur new-best-score (rest boards) new-alpha beta)))))))

(defn call-negamax [mark board] (- (negamax -10 10 board (opponent-of mark))))

(defn moves-with-scores [board mark]
  (map vector (valid-moves board) 
              (map (partial call-negamax mark) (successors board mark)))  )

(defn get-computer-move [board mark]
  (->> (moves-with-scores board mark)
       (apply max-key second)
        first))
