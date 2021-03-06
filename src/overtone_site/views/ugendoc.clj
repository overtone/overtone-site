(ns
  ^{:doc "Functions used to manipulate and generate the documentation for ugens and template them in html for web docs"
    :author "Sam Aaron, Jeff Rose & Jon Rose"}
     overtone-site.views.ugendoc
(:require [overtone-site.views.common :as common]
          [clojure.repl :as repl]
          )
  (:use 
          [overtone-site.models.webdocs]
    [overtone.util doc]
        [overtone.core]
        [overtone.sc.machinery.ugen defaults specs]
        [noir.core :only [defpage defpartial]]
        [hiccup.core :only [html]
        ]))



(defpartial param-to-list [param-map]
  [:div.row-fluid
    [:div.span2 [:strong (nth (nth (seq param-map) 0) 0)]]
    [:div.span9 [:p (nth (nth (seq param-map) 0) 1)]]])


(defn ns-publics-list [ns] (#(list (ns-name %) (map first (ns-publics %))) ns))


(defpartial doc-example [ugen] 
    (web-examples ugen))

(defpartial doc-row [l r]
  [:div.row-fluid
    [:div.span2 [:strong.pull-right l]]
    [:div.span9 r]])

(defpage "/ugen-doc/:ugen-name" {:keys [ugen-name]}
	(let [exists? (not (nil? (get-ugen ugen-name)))
        ugen    (get-ugen ugen-name)]
    (common/layout

      (let [summary (if (:summary ugen)
                    (do (str (:summary ugen)))
                    [:p " "])]
        (if exists? 
          (do
            [:div {:style "margin-top: 40px;"}
              (doc-row "name" [:i [:h1#ugen-doc-title ugen-name]])
              [:br]
              (doc-row 
                "syntax"
                [:p (str "( " ) [:i#ugen-doc-args-title ugen-name]
                  (map 
                    (fn [[k v]] 
                      [:strong
                      (str " " k)])
                      (arg-doc-map ugen))
                  (str " )")])

                (doc-row "args" (args-str ugen))
                [:br]

                (map (fn [[k v]] 
                        (doc-row (str "&nbsp;")
                          [:div.container
                            [:div.span2 {:style "margin-left: 0px;"}
                              [:p [:i#ugen-doc-title (str k)]]]
                            [:div.span7
                              (str v)]])) 
                        (arg-doc-map ugen))

                (doc-row "rates" (rates-str ugen))

                (if (:summary ugen)
                  (doc-row "summary" summary))

                (doc-row "description" (:doc ugen))

                (doc-row "categories" (categories-str ugen))

                [:br]

                ; (doc-row "examples" (str  (web-examples)))
                ]


                )
          (do 
            [:div.span9  {:style "margin-top: 100px;"}
              [:div.row-fluid.span2
                [:p (str "Sorry we can't find the Ugen doc for " ugen-name ".")]
                [:p "Back to " [:a {:href "/ugen-list"} "ugen list"]]]]))))))

