(ns tictactoe.spec_helper
  (use [tictactoe.players]))

(def x x-player)
(def o o-player)

(def draw-board  [x o x
                  x o x
                  o x o])

(def x-wins-board  [x x x
                    x o o
                    o x o])

(def o-wins-board  [x o o
                    o x o
                    x x o])
