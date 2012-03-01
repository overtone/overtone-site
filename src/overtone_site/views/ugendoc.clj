(ns
  ^{:doc "Functions used to manipulate and generate the documentation for ugens and template them in html for web docs"
    :author "Sam Aaron, Jeff Rose & Jon Rose"}
     overtone-site.views.ugendoc
(:require [overtone-site.views.common :as common]
          [noir.content.getting-started])
  (:use [overtone.sc.machinery.ugen defaults specs]
        [overtone.helpers.string :only [capitalize]]
        [overtone.util lib doc]
        [noir.core :only [defpage defpartial]]
        [hiccup.core :only [html]]))

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

(defpartial param-to-list [param-map]
  [:div.row-fluid
    [:div.span2 [:strong (nth (nth (seq param-map) 0) 0)]]
    [:div.span9 [:p (nth (nth (seq param-map) 0) 1)]]])


(defn- ns-publics-list [ns] (#(list (ns-name %) (map first (ns-publics %))) ns))

(defpage "/ugendoc/:ugen-name" {:keys [ugen-name]}
	(let [exists? (not (nil? (get-ugen ugen-name)))
        ugen    (get-ugen ugen-name)]
    (common/layout
      (let [summary (if (:summary ugen)
                    (do (str (:summary ugen)))
                    [:p " "])]
        (if exists? 
          (do
            [:div.span9
              [:div.row-fluid
                [:div.span2
                  [:strong.pull-right "name"]]
                [:div.span9 [:h1 ugen-name]]]
              [:br ]
              [:div.row-fluid
                [:div.span2
                  [:strong.pull-right "syntax"]]
                [:div.span9
                  [:p (str "( " ) ugen-name
                    (map 
                      (fn [[k v]] 
                        [:strong
                        (str " " k)])
                        (arg-doc-map ugen))
                    (str " )")]]]
              [:br ]
              [:div.row-fluid
                [:div.span2
                    [:strong.pull-right "args"]]
                [:div.span9 (args-str ugen)]]
                [:br]
                (map (fn [[k v]] 
                        [:div.row-fluid
                          [:div.span2
                            [:p (str "&nbsp;")]]
                          [:div.span9.row-fluid
                            [:div.span3 {:style "margin-left: 0px;"}
                              [:p (str k)]]
                            [:div.span9
                              (str v)]]]) 
                        (arg-doc-map ugen))
              [:br ]
              [:div.row-fluid
                [:div.span2
                  [:strong.pull-right "rates"]]
                [:div.span9 (rates-str ugen)]]
              [:br ]
              (if (:summary ugen)
                (do
                  [:div.row-fluid
                    [:div.span2
                      [:strong.pull-right "summary"]]
                    [:div.span9 summary]]))
              [:div.row-fluid
                [:div.span2
                  [:strong.pull-right "description"]]
                [:div.span9 (:doc ugen)]]
              [:br ]
              [:div.row-fluid {:style "margin-bottom: 300px;"}
                [:div.span2
                  [:strong.pull-right "categories"]]
                [:div.span6 (categories-str ugen)]]
                [:hr]]
               )
          (do 
            [:div.span9
              [:div.row-fluid.span2
                [:p (str "Sorry we can't find the Ugen doc for " ugen-name ".")]
                [:p "Back to " [:a {:href "/ugenlist"} "ugen list"]]]]))))))

