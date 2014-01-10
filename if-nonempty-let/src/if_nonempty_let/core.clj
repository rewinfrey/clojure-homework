(ns if-nonempty-let.core)

(defmacro if-nonempty-let [bindings then else]
  (try
    (if (even? (count bindings))
      (if (empty? (last bindings))
        `(let ~bindings ~else)
        `(let ~bindings ~then))
    (throw (IllegalArgumentException. "Not of correct form")))
    (catch Exception e (throw (IllegalArgumentException. "Cannot bind unbound let")))))
