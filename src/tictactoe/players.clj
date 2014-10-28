(ns tictactoe.players
  (use [tictactoe.player_marks]))

(defstruct Player :mark :type)

(defn human [mark]
  (struct Player mark :human))

(defn computer [mark]
  (struct Player mark :computer))

(def human-vs-human
  (cycle [(human x-mark) (human o-mark)]))

(def human-vs-computer
  (cycle [(human x-mark) (computer o-mark)]))

(def computer-vs-human
  (cycle [(computer x-mark) (human o-mark)]))

(def computer-vs-computer
  (cycle [(computer x-mark) (computer o-mark)]))

(def player-combinations
  {:human-vs-human human-vs-human
   :human-vs-computer human-vs-computer
   :computer-vs-human computer-vs-human
   :computer-vs-computer computer-vs-computer
   })
