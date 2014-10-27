(ns tictactoe.players
  (use [tictactoe.player_marks]))

(defstruct Player :mark :type)

(defn human [mark]
  (struct Player mark :human))

(defn computer [mark]
  (struct Player mark :computer))

(def human-vs-human
  (cycle [(human x-mark) (human o-mark)]))

(def computer-vs-human
  (cycle [(computer x-mark) (human o-mark)]))
