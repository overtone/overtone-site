(ns overtone-site.views.footer
  (:use [noir.core :only [defpartial]]
        [hiccup.page-helpers :only [include-js include-css html5]]))



(defpartial footer []
	[:div.row-fluid
		[:p (str "&copy" "Overtone")]
		[:a {:href "/about"} "about"]
		[:a {:href "/exhibition"} "exhibition"]
		[:a {:href "/ugen-list"} "docs"]]
		"vlieubieugbeqiugqeugnqeugnqeugnenu")