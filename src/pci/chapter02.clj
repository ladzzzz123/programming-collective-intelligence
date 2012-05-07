(ns pci.chapter02
  (:require pci.data))

(defn results-for-critic
  "Return the scores for a specific critic"
  [critic-name]
  (get critics critic-name))

;; Finding similar users. Euclidean distance and Pearson correlation

;; Returns a distance-based similarity score for person1 and person2

(defn square [x] (* x x))

(defn distance
  "Take the distance in each axis, square them and add them together"
  [x y]
  (let [[a b] x
        [c d] y]
  (Math/sqrt
    (+ (square (- a c)) (square (- b d)))))) 

(defn distance-normalized
  "A function that gives higher values for people that are similar"
  [x y]
  (/ 1 (+ 1 (distance x y))))

(defn sim-distance
  "Return a distance based similarity score for person 1 and person 2"
  [prefs person1 person2]
  ())