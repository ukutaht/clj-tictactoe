(ns tictactoe.players
  (use [tictactoe.console_player :as console]))

(def x-mark  :x)
(def o-mark  :o)

(defstruct Player :mark :get-move)

(defn human [mark]
  (struct Player mark console/get-move))

(def human-vs-human
  (cycle [(human x-mark) (human o-mark)]))
