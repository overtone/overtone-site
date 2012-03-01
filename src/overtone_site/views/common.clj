(ns overtone-site.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page-helpers :only [include-js include-css html5]]))

(def CATEGORY-PREFIX "/ugendocs/category/")

(defpartial main-layout [& content]
  (html5
    [:head
      [:title "overtone-site"]
      (include-css "/css/reset.css")
      (include-css "/bootstrap/css/bootstrap-responsive.css")
      (include-css "/bootstrap/css/bootstrap.css")
      (include-css "/css/styles.css")
     ]
    [:body
     [:div.container
      content]]))

(defpartial layout [& content]
  (main-layout
    [:div.fluid-row 
      [:div.span9 
        [:br]
        [:a.span9 {:href "/"} [:img {:src "/img/overtone-banner-white.png" :style "margin-bottom: 10px;"}]]]]
    [:div.fluid-row
      [:div.span9
        [:a.span1 {:href "/"} "Home"]
        [:a.span1 {:href "/exhibition"} "exhibition"]
        [:a.span1 {:href "/ugenlist"} "docs"]
        [:a.span1 {:href "/tutorials"} "tutorials"]
        [:a.span1 {:href "/download"} "download"]
        [:a.span1 {:href "/shop"} "shop"]
        [:a.span1 {:href "/about"} "about"]]]
    [:div.fluid-row
      [:div.span9 
        [:hr]]]
      content
    )
  )

