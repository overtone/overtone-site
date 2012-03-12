(ns overtone-site.views.about
  (:require [overtone-site.views.common :as common]
            [noir.content.getting-started])
  (:use [noir.core :only [defpage defpartial]]
        [hiccup.core :only [html]]
        ))

(defpartial link-to [link text]
	[:a {:href link} text])

(defpage "/about" []
	(common/layout
		[:div.hero-unit {:style "padding-top: 0px; padding-bottom: 0px"}
			[:center 
			[:br]
			[:br]
			[:br]
			[:br]
			[:h1 "( use 'overtone.live )"]
			[:br]
			[:p "Overtone is an open source audio environment being created to explore musical ideas from synthesis and sampling to instrument building, live-coding and collaborative jamming. We use the " (link-to "http://www.audiosynth.com/" "SuperCollider")  " synth server as the audio engine, with " (link-to "http://clojure.org/" "Clojure") " being used to develop the APIs and the application. Synthesizers, effects, analyzers and musical generators can be programmed in Clojure."]
			[:p "Come and join the " (link-to "http://groups.google.com/group/overtone" "Overtone Google Group") " if you want to get involved in the project or have any questions about how you can use Overtone to make cool sounds and music."]
			]
				]))

