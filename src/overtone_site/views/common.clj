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
  (include-js "js/widgets.js")
  (include-js "js/jquery.js")
  (include-js "js/pretify.js")
  (include-js "bootstrap/js/basic/bootstrap-transition.js")
  (include-js "bootstrap/js/basic/bootstrap-alert.js")
  (include-js "bootstrap/js/basic/bootstrap-modal.js")
  (include-js "bootstrap/js/basic/bootstrap-dropdown.js")
  (include-js "bootstrap/js/basic/bootstrap-scrollspy.js")
  (include-js "bootstrap/js/basic/bootstrap-tab.js")
  (include-js "bootstrap/js/basic/bootstrap-tooltip.js")
  (include-js "bootstrap/js/basic/bootstrap-popover.js")
  (include-js "bootstrap/js/basic/bootstrap-button.js")
  (include-js "bootstrap/js/basic/bootstrap-collapse.js")
  (include-js "bootstrap/js/basic/bootstrap-carousel.js")
  (include-js "bootstrap/js/basic/bootstrap-typeahead.js")
  (include-js "js/application.js"))

(defpartial link []
  [:link {:rel "shortcut icon" :href "ico/favicon.ico"}]
  [:link {:rel "apple-touch-icon" :href "ico/apple-touch-icon.png"}]
  [:link {:rel "apple-touch-icon" :sizes "72x72" :href "ico/apple-touch-icon-72x72.png"}]
  [:link {:rel "apple-touch-icon" :sizes "114x114" :href "ico/apple-touch-icon-114x114.png"}])

(defpartial navbar []
  [:div.navbar.navbar-fixed-top
    [:div.navbar-inner
      [:div.container
        [:a.btn.btn-navbar{ :data-toggle  "collapse", :data-target  ".nav-collapse"}
          [:span.icon-bar]
          [:span.icon-bar]
          [:span.icon-bar]
          ]
        [:a.brand{:href  "/"} "Overtone"] 
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

(defpartial layout [& content]
  (html5
    [:head
      [:title "overtone-site"] (css) (link)]
    [:body
     (navbar)
     [:div.container content]
    (js)]))
