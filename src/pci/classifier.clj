(ns pci.classifier)

;; A more generic classifier for working with Bayes and Fisher methods

(def ^{:dynamic true :doc "Counts of feature/category combinations"}
  *feature-count* (ref {}))
  
(def ^{:dynamic true :doc "Counts of documents in each category"}
  *category-count* (ref {}))

(defprotocol Classifier
  (train [this]))

(defn- get-words
  "Get the words from a text"
  [text])
  
(defn- incf
  "Increment the feature count"
  [f cat])
  
(defn- fcount
  "Return a count for a feature in a category"
  [f cat]
  )
  
(defn- incc
  "Increment the count for a category"
  [cat]
  
;;; ======================================
;;;
;;; Naive Bayes Classifier
;;;
;;; ======================================


;;; ======================================
;;;
;;; Fisher Classifier
;;;
;;; ======================================