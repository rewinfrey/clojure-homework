(ns if-nonempty-let.core-spec
  (:require [speclj.core :refer :all]
            [if-nonempty-let.core :refer :all]))

(describe "if-nonempty-let"
  (context "empty bindings"
    (it "returns else expression for empty vector binding"
      (should= :empty
               (if-nonempty-let [xs []]
                                [:non-empty xs]
                                :empty)))

    (it "returns else expression for empty string binding"
      (should= :else
               (if-nonempty-let [x ""] x :else)))

    (it "returns else expression for empty map binding"
      (should= :else
               (if-nonempty-let [x {}] x :else)))

    (it "handles non-literal empties"
      (let [xs []]
        (should= :else
                 (if-nonempty-let [x xs] x :else))))

    (it "returns else expression for empty list binding"
      (should= :else
               (if-nonempty-let [x ()] x :else))))

  (context "non empty bindings"
    (it "returns a provided then expression when binding is a string" 
      (should= "ohai there"
               (if-nonempty-let [x "ohai"] (str x " there") :else)))

    (it "returns then condition when binding is a vector" 
      (should= [:non-empty [0 1 2 3 4]]
               (if-nonempty-let [xs (range 5)]
                                [:non-empty xs]
                                :empty)))

    (it "returns then condition when binding is a map" 
      (should= [:non-empty {0 1 2 3}]
               (if-nonempty-let [xs (apply hash-map (range 4))]
                                [:non-empty xs]
                                :empty)))
    )

  (context "invalid forms"

    (it "throws IllegalArgumentException when binding form does not contain an even number of forms"
      (should-throw IllegalArgumentException
                    (eval '(if-nonempty-let [xs]
                                            [:then]
                                            :empty))))

    (it "returns IllegalArgumentException when binding form contains a let binding that has not yet been set"
      (should-throw IllegalArgumentException
                    (eval '(if-nonempty-let [xs []
                                             xs xs]
                                            [:then]
                                            :empty))))
    )
)
