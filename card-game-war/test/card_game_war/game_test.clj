(ns card-game-war.game-test
  (:require [clojure.test :refer :all]
            [card-game-war.game :as game]))


;; fill in  tests for your game
(deftest test-play-round
  (testing "the highest rank wins the cards in the round"
    (is (= (game/play-round [:spade 2] [:diamond 5]) [:diamond 5])))
  (testing "queens are higher rank than jacks"
    (is (= (game/play-round [:spade :queen] [:diamond :jack]) [:spade :queen])))
  (testing "kings are higher rank than queens"
    (is (= (game/play-round [:spade :queen] [:diamond :king]) [:diamond :king])))
  (testing "aces are higher rank than kings"
    (is (= (game/play-round [:spade :ace] [:diamond :queen]) [:spade :ace])))
  (testing "if the ranks are equal, clubs beat spades"
    (is (= (game/play-round [:club :ace] [:spade :ace]) [:club :ace])))
  (testing "if the ranks are equal, diamonds beat clubs"
    (is (= (game/play-round [:club :ace] [:diamond :ace]) [:diamond :ace])))
  (testing "if the ranks are equal, hearts beat diamonds"
    (is (= (game/play-round [:heart :ace] [:diamond :ace]) [:heart :ace]))))

(deftest test-play-game
  (testing "the player loses when they run out of cards"
    (let [[p1-cards p2-cards]
          (game/play-game (conj clojure.lang.PersistentQueue/EMPTY [:spade 2] [:diamond 5])
                          (conj clojure.lang.PersistentQueue/EMPTY [:club 2] [:hearth 5]))]
      (is
        (or
          (empty? p1-cards)
          (empty? p2-cards))))))
