(ns tictactoe.players
  (use [tictactoe.player_marks]
       [tictactoe.console_player :as console]
       [tictactoe.computer_player :as computer]))

(defstruct Player :mark :get-move)

(defn human [mark]
  (struct Player mark console/get-move))

(defn computer [mark]
  (struct Player mark computer/get-computer-move))

(def human-vs-human
  (cycle [(human x-mark) (human o-mark)]))

(def computer-vs-human
  (cycle [(computer x-mark) (human o-mark)]))
