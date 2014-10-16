(ns tictactoe.players_spec
  (use [tictactoe.players]
       [speclj.core]))

(describe "players"
  (context "human vs human"
    (it "assigns x and o as marks"
      (should= x-mark ((first human-vs-human) :mark))
      (should= o-mark ((second human-vs-human) :mark)))
    
    (it "is a cycle"
      (should= (first human-vs-human) (nth human-vs-human 2)))))
