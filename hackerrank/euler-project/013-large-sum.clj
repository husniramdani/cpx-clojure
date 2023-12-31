(require 
  '[clojure.string :refer [split join]])

(defn digits [n]
  (let [toInt #(->> (str %) (read-string))]
  (map toInt (str n))))

(defn zeroIfEmpty [ls]
  (if (empty? ls) (list 0) ls))

(defn append-n-zeros [n ls]
  (concat ls (repeat n 0)))

(defn append-zeros [& ls]
  (let [lns (map count ls)
        mln (apply max lns)
        zCnts (map - (repeat mln) lns)]
  (map append-n-zeros zCnts ls)))

(defn byCarry [[lc & prv] n]
  (let [nn (+ n lc)
        cur (mod nn 10)
        nc  (quot nn 10)]
  (conj prv cur nc)))
       
(defn list-add [& ls]
  (->> (map reverse ls)
       (reduce append-zeros)
       (apply map +)
       (reduce byCarry [0])
       (drop-while zero?)
       (zeroIfEmpty)))

(defn str-add [& ls]
  (->> (map digits ls)
       (reduce list-add)
       (reduce str)))

(defn solve [T & nz]
  (->> (reduce str-add nz)
       (take 10)
       (reduce str)))

(defn interact [func]
  (let [joinIfSeq #(if (seq? %) (join "\n" %) %)]
  (->> (func (slurp *in*))
       (joinIfSeq)
       (println))))

(defn safeInt [n]
  (try (Integer/parseInt n)
  (catch Exception e n)))

(defn prepare [input]
  (let [words  #(split % #"\s")]
  (->> (words input)
       (map safeInt))))

(defn main []
  (let [program #(->> (prepare %) (apply solve))]
  (interact program)))

(main)