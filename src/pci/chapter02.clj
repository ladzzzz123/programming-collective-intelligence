(ns pci.chapter02
  (:use clojure.set)
  (:require pci.data))

(defn results-for
  "Return the scores for a specific critic"
  [critic-name]
  (get critics critic-name))

(defn key-intersection
  "Given two maps, returns the common keys as a Clojure set"
  [m1 m2]
  (let [a (set (keys m1))
        b (set (keys m2))]
    (intersection a b)))
  
;; Finding similar users. Euclidean distance and Pearson correlation

;; Returns a distance-based similarity score for person1 and person2

(defn distance
  "Take the distance in each axis, square them and add them together"
  [x y]
  (letfn [(square [x] (* x x))]
    (let [[a b] x
          [c d] y]
    (Math/sqrt
      (+ (square (- a c)) (square (- b d))))))) 

(defn distance-normalized
  "A function that gives higher values for people that are similar"
  [x y]
  (/ 1 (+ 1 (distance x y))))

(defn similarity-distance [x y] ;; sim_distance
  "Calculate the square of the Euclidean distance between two vectors and invert it."
  (+ 1 (reduce + (map #(* % %) (map - x y)))))

(defn sim-distance
  "Returns a distance-based similarity score for person1 and person2"
  [prefs person1 person2]
  (let [p1 (prefs person1)
        p2 (prefs person2)
        shared-items (key-intersection p1 p2)]   ;; get a list of shared items
    (if (zero? (count shared-items)) 
      0 ;; return 0 if no shared items
      (/ 1 (+ 1 (reduce + (map #(* % %)
        (map -
          (map #(get p1 %) shared-items)
          (map #(get p2 %) shared-items)))))))))