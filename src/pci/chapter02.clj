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

;; Code is not optimized for elegance,
;; Written so that it's easy to understand and translates easily to the Python version

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

(defn sim-pearson
  "Returns the Pearson correlation coefficient for p1 and p2"
  [prefs person1 person2]
  (letfn [(square [x] (* x x))]
  (let [p1 (prefs person1)
        p2 (prefs person2)
        shared-items (key-intersection p1 p2)
        n (count shared-items)
        p1map (map #(get p1 %) shared-items)
        p2map (map #(get p2 %) shared-items)
        ;; sum up the squarees
        sum1 (reduce + p1map)
        sum2 (reduce + p2map)
        ;; sum of squares
        sum1Sq (reduce + (map square p1map))
        sum2Sq (reduce + (map square p2map))
        ;; sum of the products
        pSum (reduce + (map * p1map p2map))
        numerator (- pSum (/ (* sum1 sum2) n))
        denominator
        (Math/sqrt
          (* 
            (- sum1Sq (/ (square sum1) n))
            (- sum2Sq (/ (square sum2) n))))]
    (if (zero? denominator)
      0
      (/ numerator denominator)))))
