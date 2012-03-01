(ns overtone-site.views.ugenlist
(:require [overtone-site.views.common :as common]
          [noir.content.getting-started])
  (:use [overtone.sc.machinery.ugen defaults specs categories]
        [overtone.helpers.string :only [capitalize]]
        [overtone.util lib doc]
        [noir.core :only [defpage defpartial]]
        [hiccup.core :only [html]]))

(def CATEGORY-PREFIX "/ugendocs/category/")
(def UGEN-PREFIX "/ugendoc/")

(defn xform [ic]
  (reduce (fn [result [k [vs]]]
        (reduce (fn [r v]
              (assoc r v (if-let [rv (r v)]
                   (cons k rv)
                   [k])))
            result vs))
      {} ic))

(defn- categories-str
  "Returns a string representing the categories of a ugen spec"
  [spec]
    (apply str 
      (interpose ", " 
        (map 
          (fn [cat] 
            (apply str (interpose " -> " (cond 
                                          (= 1 (count cat)) (assoc cat "General")
                                          (= 0 (count cat)) "General")
                                          )))) 
          (:categories spec)))))


(defpartial ugen-row
  [ugen-name category]
  [:div.row-fluid
    [:div.span12
      [:div.span2
        [:strong 
          [:a {:href (str "/ugendoc/" ugen-name)} ugen-name]]]
      [:div.span6
        [:p (categories-str (get-ugen ugen-name))]]]])

(defpartial ugen-categories
  [categories]
    [:div.row-fluid
     [:p categories]
      (map 
        (fn [c] 
          [:div.span1 
            [:a {:href (str CATEGORY-PREFIX c)} c]]) categories)
      ])

(def overtone-ugen-names (zipmap
   (map overtone-ugen-name (keys UGEN-CATEGORIES))
   (vals UGEN-CATEGORIES)))

(def xmap (xform UGEN-CATEGORIES))

(defpartial xform-to-partial 
  [xf]
  (map 
    (fn [keyvals]
    (let [category (first keyvals)
          ugens    (sort (second keyvals))]
      [:div.span6.row-fluid
          [:div.row-fluid
              [:p [:strong category]]]
          [:div.row-fluid
            [:p (interpose " / " (map (fn [ugen] [:a {:href (str UGEN-PREFIX ugen)} ugen]) ugens))]]])) 
    (sort xf)))

(defpage "/ugenlist" []
         (common/layout
         	[:div.span8 {:style "margin-bottom: 100px;"}
            [:div.span2.row-fluid [:h2 "Categories"][:br]]
              (xform-to-partial xmap)]))
