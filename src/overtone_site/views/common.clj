(ns overtone-site.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page-helpers :only [include-js include-css html5]]))

(defpartial css [] 
  (include-css "/bootstrap/css/bootstrap.css")
  (include-css "/bootstrap/css/bootstrap-responsive.css")
  (include-css "/bootstrap/css/docs.css")
  (include-css "/css/pretify.css")
  (include-css "/css/styles.css"))

(defpartial js [] 
  (include-js "/js/widgets.js")
  (include-js "/js/jquery.js")
  (include-js "/js/pretify.js")
  (include-js "/bootstrap/js/bootstrap-transition.js")
  (include-js "/bootstrap/js/bootstrap-alert.js")
  (include-js "/bootstrap/js/bootstrap-modal.js")
  (include-js "/bootstrap/js/bootstrap-dropdown.js")
  (include-js "/bootstrap/js/bootstrap-scrollspy.js")
  (include-js "/bootstrap/js/bootstrap-tab.js")
  (include-js "/bootstrap/js/bootstrap-tooltip.js")
  (include-js "/bootstrap/js/bootstrap-popover.js")
  (include-js "/bootstrap/js/bootstrap-button.js")
  (include-js "/bootstrap/js/bootstrap-collapse.js")
  (include-js "/bootstrap/js/bootstrap-carousel.js")
  (include-js "/bootstrap/js/bootstrap-typeahead.js")
  (include-js "/js/application.js"))

(defpartial link []
  [:link {:rel "shortcut icon" :href "ico/favicon.ico"}]
  [:link {:rel "apple-touch-icon" :href "ico/apple-touch-icon.png"}]
  [:link {:rel "apple-touch-icon" :sizes "72x72" :href "ico/apple-touch-icon-72x72.png"}]
  [:link {:rel "apple-touch-icon" :sizes "114x114" :href "ico/apple-touch-icon-114x114.png"}])

(defpartial navbar []
  [:div.navbar.navbar-fixed-bottom
    [:div.navbar-inner
      [:div.container
        [:a.btn.btn-navbar{ :data-toggle  "collapse", :data-target  ".nav-collapse"}
          [:span.icon-bar]
          [:span.icon-bar]
          [:span.icon-bar]
          ]
        [:a.brand{:href  "/"} "( o v e r t o n e )"] 
        [:div.nav-collapse
          [:ul.nav
            [:li
              [:a {:href  "/"} "Home"]]
            [:li
              [:a {:href  "/about"} "about"]]
            [:li
              [:a {:href  "/ugen-list"} "docs"]]
            [:li
              [:a {:href  "https://github.com/overtone/overtone"} "download"]]
            [:li
              [:a {:href  "https://github.com/overtone/overtone/tree/master/examples"} "examples"]]
            [:li
              [:a {:href  "/exhibition"} "exhibition"]]]]]]])


(defpartial footer []
  [:div.row-fluid
  [:hr]

    [:p 
      (str "&copy " "Overtone")
      (str " | ")
      [:a {:href "/about"} "about"]
      (str " | ")
      [:a {:href "/ugen-list"} "docs"]
      (str " | ")
      [:a {:href "https://github.com/overtone/overtone"} "download"]
      (str " | ")
      [:a {:href "https://github.com/overtone/overtone/tree/master/examples"} "examples"]
      (str " | ")
      [:a {:href "/exhibition"} "exhibition"]
      ]])

(defpartial layout [& content]
  (html5
    [:head
      [:title "overtone-site"] (css) (link)]
    [:body
      (navbar)
      [:div.container {:style "margin-bottom: 150px"}
        content
        ; (footer)
        ]
        
    (js)]))
