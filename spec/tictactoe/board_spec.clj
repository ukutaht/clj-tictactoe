(ns tictactoe.board_spec
  (:require [speclj.core :refer :all]
            [tictactoe.board :refer :all]))

(def x x-player)
(def o o-player)

(describe "board"
  (context "making moves"
    (it "marks first square on board"
      (let [marked-board (mark-square empty-board 0 x-player) ]
        (should= x-player (square-at marked-board 0))))

    (it "marks last square on board"
      (let [marked-board (mark-square empty-board 8 o-player) ]
        (should= o-player (square-at marked-board 8))))

    (it "throws exception when trying to play an invalid move"
      (should-throw java.lang.IllegalArgumentException (mark-square empty-board -1))))

  (context "move validity"
    (it "is invalid if a move has been played at the index"
      (let [marked-board (mark-square empty-board 0 x-player) ]
        (should-not (valid-move? marked-board 0))))

    (it "is invalid if move is over bounds"
      (should-not (valid-move? empty-board 9)))

    (it "is invalid if move is under bounds"
      (should-not (valid-move? empty-board -1))))

  
  (context "terminal states"
    (def draw-board  [x o x
                      x o x
                      o x o])

    (def x-wins-board  [x x x
                        x o o
                        o x o])

    (def o-wins-board  [x o o
                        o x o
                        x x o])

    (context "win"
      (def horizontal-win-boards  [[x x x 3 4 5 6 7 8]
                                   [0 1 2 x x x 6 7 8]
                                   [0 1 2 3 4 5 x x x]])

      (def vertical-win-boards    [[x 1 2 x 4 5 x 7 8]
                                   [0 x 2 3 x 5 6 x 8]
                                   [0 1 x 3 4 x 6 7 x]])

      (def diagonal-win-boards    [[x 1 2 3 x 5 6 7 x]
                                   [0 1 x 3 x 5 x 7 8]])

      (it "has no winner when empty"
        (should-not (has-winner? empty-board)))

      (it "has no winner when draw"
        (should-not (has-winner? draw-board)))

      (it "recognises horizontal win"
        (should (every? has-winner? horizontal-win-boards)))

      (it "recognises vertical win"
        (should (every? has-winner? vertical-win-boards)))

      (it "recognises diagonal win"
        (should (every? has-winner? diagonal-win-boards))))

      (it "returns the winner the it's x"
         (should= x (winner x-wins-board)))

      (it "returns the winner the it's o"
         (should= o (winner o-wins-board)))

      (it "returns nil when no winner"
         (should-be-nil (winner draw-board)))

    (context "draw"
      (it "is not a draw when the board is empty"
        (should-not (draw? empty-board)))
      (it "is not a draw when the has a winner"
        (should-not (draw? x-wins-board)))
      (it "is a draw when the board is full and has no winner"
        (should (draw? draw-board)))))
)
