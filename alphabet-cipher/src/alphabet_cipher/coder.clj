(ns alphabet-cipher.coder
  (:require [clojure.string :as string]))

(def a-z
  (vec (map char (range 97 123))))

(def encode-map
  (reduce (fn [input-map [x y]] (assoc input-map y (take (count a-z) (drop x (cycle a-z))))) {} (map-indexed vector a-z)))

(defn make-pairs [message-1 message-2]
  (map-indexed (fn [idx item] [item (nth message-2 idx)]) message-1))

(defn adjust-keyword-len [keyword message]
  (take (count message) (cycle keyword)))

(defn get-encode-pairs [pairs]
  (string/join ""
    (map
      (fn [[m1 m2]]
        (nth (get-in encode-map [m1]) (.indexOf a-z m2)))
      pairs)))

(defn get-decode-pairs [pairs]
  (string/join ""
    (map
      (fn [[m1 m2]]
        (nth a-z (.indexOf (get-in encode-map [m1]) m2)))
      pairs)))

(defn encode [keyword message]
  (let [pairs (make-pairs (adjust-keyword-len keyword message) message)]
    (get-encode-pairs pairs)))

(defn decode [keyword message]
  (let [pairs (make-pairs (adjust-keyword-len keyword message) message)]
    (get-decode-pairs pairs)))

(defn detect-keyword [keyword cipher message]
  (reduce
    (fn [current new]
      (if (not= cipher (encode (str current) message))
        (str current new)
        current))
    keyword))

(defn decipher [cipher message]
  (let [keyword (get-decode-pairs (make-pairs message cipher))]
    (detect-keyword keyword cipher message)))
