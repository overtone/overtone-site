(ns overtone-site.views.navbar
  (:require [overtone-site.views.common :as common]
            [noir.content.getting-started])
  (:use [noir.core :only [defpage defpartial]]
        [hiccup.core :only [html]]))


(defpartial navabar []
	[:div.navbar-inner
		[:div.container-fluid
			[:a.btn.btn-navbar{ :data-toggle  "collapse", :data-target  ".nav-collapse"}]
				[:span.icon-bar]
				[:span.icon-bar]
				[:span.icon-bar]
			[:a.brand{:href  "/"} "Overtone" ]
			[:div.nav-collapse
				[:ul.nav
					[:li
						[:a {:href  "/"} "Home"]]
					[:li
						[:a {:href  "/about"} "About"]]
					[:li
						[:a {:href  "/register"} "Register"]]
					[:li
						[:a {:href  "/contact"} "Contact"]]]
				[:p.navbar-text.pull-right
					[:a {:href  "/login"} "Login"]]]]])