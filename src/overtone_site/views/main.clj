(ns overtone-site.views.main
  (:require [overtone-site.views.common :as common]
            [noir.content.getting-started])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]
        [hiccup.page-helpers :only [include-js include-css]]))

(defpage "/" []
  (common/layout

  	(include-js "http://widgets.twimg.com/j/2/widget.js")
		[:div.hero-unit {:style "padding-top: 0px; padding-bottom: 0px"}
			[:center [:image {:src "img/overtone-banner-white.png"}]]
			[:br]
			[:br]
			[:br]
			[:center
				[:p "Overtone is an open source audio environment being created
					to explore musical ideas from synthesis and sampling to
					instrument building, live-coding and collaborative jamming.
					We use the SuperCollider synth server as the audio engine,
					with Clojure being used to develop the APIs and the application.
					Synthesizers, effects, analyzers and musical generators can be
					programmed in Clojure."]]]
        [:hr]

		[:div.span12#exhibition-pane
			[:div.row
				[:div.span6

					[:h3 "Exhibition"]
					[:br]]]
			[:div.row
				  [:div.span6
					[:center
						[:img {:src "/img/sam-conj11.png"}]
						[:p {:style "margin-bottom: 0px;"} "Overtone at Clojure/Conj '11"]
						[:p "by Sam Aaron"]
						[:br]]]
				[:div.span6
                    [:center
                        [:a {:href "http://www.chris-granger.com/2012/02/20/overtone-and-clojurescript/"}
                         [:img {:src "/img/overtone-cljs-controller.png"}]
                         [:p {:style "margin-bottom: 0px;"} "Overtone controller in ClojureScript."]]
                         [:p "by Chris Granger"]
						[:br]]]]
			[:div.row
				[:div.span6
					[:center
						[:a {:href "https://gist.github.com/2018154"}
                         [:img {:src "/img/code-bar.png"}]
                         [:p {:style "margin-bottom: 0px;"} "A short piano piece."]]
                        [:p "by Chris Ford"]
						[:br]]]
				[:div.span6
					[:center
				[:img {:src "/img/soundmachines.jpg"}]
				[:p {:style "margin-bottom: 0px;"}"a link to this project"]
				[:p "by Thomas Jefferson"]
				[:br]]]
    ]]

			[:div.span7
				[:p [:h3 "Cross platform"]]
				[:p "Overtone runs on Linux, OSX, and Windows. The only core dependency is Java. In most cases Overtone will provide a version of SuperCollider as part of its dependencies. However, you are also able to connect to an existing external version obtained here. Finally Linux users should have a version of the jack audio server installed"]

				[:p [:h3 "Download"]]
				[:p "You can clone the project with Git by running:"]
				[:p "$ git clone git://github.com/overtone/overtone.git"]
				[:p "You can also download the source package in a zip file."]

            [:h3 "Clojure"]
                [:div#logo
                 [:a {:href "http://clojure.org/"}
                 [:image {:src "img/clojure_logo.png"}]]]

				[:p "Clojure is a dialect of Lisp, and shares with Lisp the
					 code-as-data philosophy and a powerful macro system.
					 Clojure is predominantly a functional programming language,
					 and features a rich set of immutable, persistent data
					 structures. When mutable state is needed, Clojure offers a
					 software transactional memory system and reactive Agent
					 system that ensure clean, correct, multithreaded designs."]]

			[:div.span3
				[:br]
				[:h3 "Twitter"]
			  	(include-js "js/twitter.js")]))



