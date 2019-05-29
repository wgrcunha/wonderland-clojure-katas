(ns card-game-war.game)

;; feel free to use these cards or use your own data structure
(def suits [:spade :club :diamond :heart])
(def ranks [2 3 4 5 6 7 8 9 10 :jack :queen :king :ace])

(defn highest-rank [[_ p1-rank :as p1-card] [_ p2-rank :as p2-card]]
  (let [index-p1-rank (.indexOf ranks p1-rank)
        index-p2-rank (.indexOf ranks p2-rank)]
    (cond
      (> index-p1-rank index-p2-rank) p1-card
      (> index-p2-rank index-p1-rank) p2-card
      :default nil)))

(defn highest-suit [[p1-suit _ :as p1-card] [p2-suit _ :as p2-card]]
  (let [index-p1-suit (.indexOf suits p1-suit)
        index-p2-suit (.indexOf suits p2-suit)]
    (cond
      (> index-p1-suit index-p2-suit) p1-card
      (> index-p2-suit index-p1-suit) p2-card)))

(defn compare-cards [p1-card p2-card]
  (if-let [winning-card (highest-rank p1-card p2-card)]
    winning-card
    (highest-suit p1-card p2-card)))

(defn play-round [p1-card p2-card]
  (compare-cards p1-card p2-card))

(defn update-cards [winner p1-cards p2-cards card-1 card-2]
  (let [p1-cards (pop p1-cards)
        p2-cards (pop p2-cards)]
    (if (= winner "p1")
      [(conj p1-cards card-1 card-2)
       p2-cards]
      [p1-cards
       (conj p2-cards card-1 card-2)])))

(defn do-round [player1-cards player2-cards]
  (let [p1-card (peek player1-cards)
        p2-card (peek player2-cards)]
    (if (= (play-round p1-card p2-card) p1-card)
      (update-cards "p1" player1-cards player2-cards p1-card p2-card)
      (update-cards "p2" player1-cards player2-cards p1-card p2-card))))

(defn play-game [player1-cards player2-cards]
  (loop [p1-cards player1-cards p2-cards player2-cards]
    (if (or (empty? p1-cards) (empty? p2-cards))
      [p1-cards p2-cards]
      (let [[p1-cards p2-cards] (do-round p1-cards p2-cards)]
        (recur p1-cards p2-cards)))))
