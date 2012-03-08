(ns  overtone-site.models.webdocs
  (:require [overtone.sc.examples demand osc trig compander audio-in]
            [clojure.string :as string])
  (:use [overtone.util doc lib]
        [overtone.sc.machinery defexample]
        [overtone.repl examples]
        [overtone.sc.machinery.ugen defaults specs]
        [overtone.helpers.string :only [capitalize]]))

(defn- args-str
  "Returns a string representing the arguments of the ugen spec"
  [spec]
  (let [args (:args spec)
        name-vals (map #(str (:name %) " " (get % :default ":none")) args)
        line (apply str (interpose ", " name-vals))]
    (str "  [" line "]")))

(defn- categories-str
  "Returns a string representing the categories of a ugen spec"
  [spec]
  (apply str (interpose ", " (map (fn [cat] (apply str (interpose " -> " cat))) (:categories spec)))))

(defn- rates-str
  "Returns a string representing the rates of a ugen spec"
  [spec]
  (let [rates (sort-by UGEN-RATE-SORT-FN (:rates spec))]
    (str "[ " (apply str (interpose ", " rates)) " ]")))

(defn- longest-example-key
  [examples]
  (let [example-keys (flatten (map (fn [[k v]] (keys v)) examples))]
    (length-of-longest-string example-keys)))

(defn- longest-gen-example-key
  [gen-examples]
  (let [example-keys (keys gen-examples)]
    (if example-keys
      (length-of-longest-string example-keys)
      0)))

(defn- arg-doc-str
  "Returns a string representing the arg docs of a ugen spec"
  [spec]
  (let [args (:args spec)
        name:doc (fn [arg] [(or (:name arg) "NAME MISSING!")  (or (capitalize (:doc arg)) "DOC MISSING!")])
        arg-doc (map name:doc args)
        doc-map (into {} arg-doc)
        arg-max-key-len (length-of-longest-key doc-map)
        indentation (+ 5 arg-max-key-len)]
    (apply str (map (fn [[name docs]]
                      (str "  "
                           name
                           (gen-padding (inc (- arg-max-key-len (.length name))) " ")
                           "- "
                           (indented-str-block docs DOC-WIDTH indentation)
                           "\n"))
                    arg-doc))))

(defn- arg-doc-map
  "Returns a string representing the arg docs of a ugen spec"
  [spec]
  (let [args (:args spec)
        name:doc (fn [arg] [(or (:name arg) "NAME MISSING!")  (or (capitalize (:doc arg)) "DOC MISSING!")])
        arg-doc (map name:doc args)
        doc-map (into {} arg-doc)
        arg-max-key-len (length-of-longest-key doc-map)
        indentation (+ 5 arg-max-key-len)]
        arg-doc))

(defn- full-doc-str
  "Returns a string representing the full documentation for the given ugen spec"
  [spec]
  (let [doc    (or (:doc spec) "No documentation has been defined for this ugen.")
        doc    (capitalize doc)
        g-name (overtone-ugen-name (name (:name spec)))]
    (str
     (when (:summary spec)
       (str "\n  " (indented-str-block (:summary spec) (+ 10 DOC-WIDTH) 2) "\n"))

     "\n"
     (args-str spec)
     "\n\n"

     (arg-doc-str spec)
     "\n"
     (str "  " (indented-str-block doc  (+ 10 DOC-WIDTH) 2))
     "\n"
     (if (:src-str spec)
       (str "\n  Source:\n" (:src-str spec) "\n"))
     "\n"
     (str "  Categories: " (categories-str spec))
     "\n"
     (str "  Rates: " (rates-str spec))
     "\n"
     (str "  Default rate: " (:default-rate spec))
     (if (:contributor spec)
       (str "\n  Contributed by: " (:contributor spec))
       ""))))

(defn- merge-arg-doc-default
  "Adds default doc to arg if doc string isn't present."
  [arg]
  (let [last-resort {:doc NO-ARG-DOC-FOUND}
        default-str (get DEFAULT-ARG-DOCS (:name arg))
        default (if default-str
                  {:doc default-str}
                  {})]
    (merge last-resort default arg)))

(defn with-arg-defaults
  "Manipulates the spec's arg key to add documentation strings for each
  argument. If the doc string is present it is left unmodified. Otherwise it
  will look up the argument in a list of default doc strings for common keys.
  If a default is present it will add it otherwise last resort default
  NO-ARG-DOC-FOUND will be used."
  [spec]
  (let [new-args (map merge-arg-doc-default (:args spec))]
    (assoc spec :args new-args)))

(defn with-full-doc
  "Adds an extra key to the spec representing the full documentation string
   specifically prepared to be the final ugen fn's docstring for printing on
   the REPL"
  [spec]
  (assoc spec :full-doc (full-doc-str spec)))


(defn- ns-publics-list [ns] (#(list (ns-name %) (map first (ns-publics %))) ns))

(def OVERTONE-VERSION "0.6.0/")

(defn get-current-directory []
  (. (java.io.File. ".") getCanonicalPath))

(defn- example-map
  "Print out examples for a specific gen. If passed a gen and a key will list
  the full example documentation. If passed no arguments will list out all
  available examples.
  (examples)          ;=> print out all examples
  (examples foo)      ;=> print out examples for gen foo
  (examples foo :bar) ;=> print out doc for example :bar for gen foo"
  ([]
     (let [all-examples    @examples*]
       all-examples))
  ([gen]
     (let [exs @examples*
           gen-name (resolve-gen-name gen)
           example (get examples gen-name)
           longest-key-len (inc (longest-gen-example-key exs))]
       (str exs "" longest-key-len))))

(defn- print-gen-examples
  ([gen-examples] (print-gen-examples gen-examples "" 0))
  ([gen-examples indent-str desc-indent-len]
     (if (empty? gen-examples)
       (str "Sorry, no examples for this generator have been contributed.\n Please consider submitting one.")
       (dorun
        (for [orig-key (keys gen-examples)]
          (let [key             (str indent-str orig-key)
                key-len         (.length key)
                desc-indent-len (+ desc-indent-len (.length indent-str))
                key             (if (< key-len desc-indent-len)
                                  (gen-padding key (- desc-indent-len key-len) " ")
                                  key)
                full-key        (str key " (" (:rate (get gen-examples orig-key)) ") - ")
                full-key-len    (.length full-key)
                indented-desc   (indented-str-block (:summary (get gen-examples orig-key)) DOC-WIDTH full-key-len)]
            (str full-key indented-desc)))))))

(defn- args-str
  "Returns a string representing the arguments of the ugen spec"
  [spec]
  (let [args (:args spec)
        name-vals (map #(str (:name %) " " (get % :default ":none")) args)
        line (apply str (interpose ", " name-vals))]
    (str "  [" line "]")))

(defn- categories-str
  "Returns a string representing the categories of a ugen spec"
  [spec]
  (apply str (interpose ", " (map (fn [cat] (apply str (interpose " -> " cat))) (:categories spec)))))

(defn- rates-str
  "Returns a string representing the rates of a ugen spec"
  [spec]
  (let [rates (sort-by UGEN-RATE-SORT-FN (:rates spec))]
    (str "[ " (apply str (interpose ", " rates)) " ]")))

(defn- arg-doc-str
  "Returns a string representing the arg docs of a ugen spec"
  [spec]
  (let [args (:args spec)
        name:doc (fn [arg] [(or (:name arg) "NAME MISSING!")  (or (string/capitalize (:doc arg)) "DOC MISSING!")])
        arg-doc (map name:doc args)
        doc-map (into {} arg-doc)
        arg-max-key-len (length-of-longest-key doc-map)
        indentation (+ 5 arg-max-key-len)]
    (apply str (map (fn [[name docs]]
                      (str "  "
                           name
                           (gen-padding (inc (- arg-max-key-len (.length name))) " ")
                           "- "
                           (indented-str-block docs DOC-WIDTH indentation)
                           "\n"))
                    arg-doc))))

(defn- arg-doc-map
  "Returns a string representing the arg docs of a ugen spec"
  [spec]
  (let [args (:args spec)
        name:doc (fn [arg] [(or (:name arg) "NAME MISSING!")  (or (string/capitalize (:doc arg)) "DOC MISSING!")])
        arg-doc (map name:doc args)
        doc-map (into {} arg-doc)
        arg-max-key-len (length-of-longest-key doc-map)
        indentation (+ 5 arg-max-key-len)]
        arg-doc))

(defn- full-doc-str
  "Returns a string representing the full documentation for the given ugen spec"
  [spec]
  (let [doc    (or (:doc spec) "No documentation has been defined for this ugen.")
        doc    (string/capitalize doc)
        g-name (overtone-ugen-name (name (:name spec)))]
    (str
     (when (:summary spec)
       (str "\n  " (indented-str-block (:summary spec) (+ 10 DOC-WIDTH) 2) "\n"))

     "\n"
     (args-str spec)
     "\n\n"

     (arg-doc-str spec)
     "\n"
     (str "  " (indented-str-block doc  (+ 10 DOC-WIDTH) 2))
     "\n"
     (if (:src-str spec)
       (str "\n  Source:\n" (:src-str spec) "\n"))
     "\n"
     (str "  Categories: " (categories-str spec))
     "\n"
     (str "  Rates: " (rates-str spec))
     "\n"
     (str "  Default rate: " (:default-rate spec))
     (if (:contributor spec)
       (str "\n  Contributed by: " (:contributor spec))
       ""))))

(defn- merge-arg-doc-default
  "Adds default doc to arg if doc string isn't present."
  [arg]
  (let [last-resort {:doc NO-ARG-DOC-FOUND}
        default-str (get DEFAULT-ARG-DOCS (:name arg))
        default (if default-str
                  {:doc default-str}
                  {})]
    (merge last-resort default arg)))

(defn with-arg-defaults
  "Manipulates the spec's arg key to add documentation strings for each
  argument. If the doc string is present it is left unmodified. Otherwise it
  will look up the argument in a list of default doc strings for common keys.
  If a default is present it will add it otherwise last resort default
  NO-ARG-DOC-FOUND will be used."
  [spec]
  (let [new-args (map merge-arg-doc-default (:args spec))]
    (assoc spec :args new-args)))

(defn with-full-doc
  "Adds an extra key to the spec representing the full documentation string
   specifically prepared to be the final ugen fn's docstring for printing on
   the REPL"
  [spec]
  (assoc spec :full-doc (full-doc-str spec)))

(defn web-examples
  "Print out examples for a specific gen. If passed a gen and a key will list
  the full example documentation. If passed no arguments will list out all
  available examples.
  (examples)          ;=> print out all examples
  (examples foo)      ;=> print out examples for gen foo
  (examples foo :bar) ;=> print out doc for example :bar for gen foo"
  ([]
     (let [all-examples    @examples*
           longest-key-len (inc (longest-example-key all-examples))]
       (dorun
        (for [[gen-name examples] all-examples]
          (do
            (str (name gen-name)
            (print-gen-examples examples "  " longest-key-len)
            ""))))))
  ([gen]
     (let [all-examples @examples*
           gen-name (resolve-gen-name gen)
           examples (get all-examples gen-name)
           longest-key-len (inc (longest-gen-example-key examples))]
       (print-gen-examples examples "" longest-key-len)))
  ([gen key]
     (let [examples @examples*
           gen-name (resolve-gen-name gen)
           example (get-in examples [gen-name key])]
       (if example
         (str (:full-doc example))
         (str "Sorry, no example could be found for the" (name gen-name) "gen with key" key)))))
