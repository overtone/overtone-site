(ns overtone-site.views.main
  (:require [overtone-site.views.common :as common]
            [noir.content.getting-started])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]))


; (defpage "/" []
; 	(common/layout
; 		[:div.fluid-row
; 			[:div.span6
; 				[:br]
; 				[:a {:href "/https://github.com/overtone/overtone"} [:h1 "Download Overtone"]]
; 				[:br]
; 				[:a {:href "/try"} [:h1 "Try Overtone Online"]]
; 				[:br]
; 				[:a {:href "/tutorials"} [:h1 "Browse Interactive Tutorials"]]
; 				[:br]
; 				[:br]
; 				[:p
; 				"Overtone is an open source programming language and environment
; 				for people who want to create images, animations, and interactions.
; 				Initially developed to serve as a software sketchbook and to teach
; 				fundamentals of computer programming within a visual context, 
; 				Overtone also has evolved into a tool for generating finished
; 				professional work. Today, there are tens of thousands of students,
; 				artists, designers, researchers, and hobbyists who use Overtone 
; 				for learning, prototyping, and production."]
; 				[:ul
; 				[:li
; 				[:p "Free to download and open source"]]
; 				[:li
; 				[:p "Interactive programs using 2D, 3D or PDF output"]]
; 				[:li
; 				[:p "OpenGL integration for accelerated 3D"]]
; 				[:li
; 				[:p "For GNU/Linux, Mac OS X, and Windows"]]
; 				[:li
; 				[:p "Projects run online or as double-clickable applications"]]
; 				[:li
; 				[:p "Over 100 libraries extend the software into sound, video, computer vision, and more..."]]
; 				[:li
; 				[:p "Well documented, with many books available"]]]]
; 			[:div.span3
; 				[:div.fluid-row
; 					[:h3 "Exhibition"]]
; 				[:div.fluid-row
; 					[:img {:src "/img/creators.jpg"}]
; 					[:p {:style "margin-bottom: 0px;"} "a link to this project"]
; 					[:p "by Mr. Edison"]
; 					[:br]]
; 				[:div.fluid-row
; 					[:img {:src "/img/maxplanck.jpg"}]
; 					[:p {:style "margin-bottom: 0px;"}"a link to this project"]
; 					[:p "by George Washington"]
; 					[:br]]
; 				[:div.fluid-row
; 					[:img {:src "/img/soundmachines.jpg"}]
; 					[:p {:style "margin-bottom: 0px;"}"a link to this project"]
; 					[:p "by Thomas Jefferson"]
; 					[:br]]
; 				[:div.fluid-row
; 					[:img {:src "/img/visualeditions.jpg"}]
; 					[:br]
; 					[:p {:style "margin-bottom: 0px;"}"a link to this project"]
; 					[:p "by Barrack Obama"]
; 					[:br]]]]))



(defpage "/" []
  (common/layout
		[:div.hero-unit {:style "padding-top: 0px; padding-bottom: 0px"}
			[:image {:src "img/overtone-banner-white.png"}]
			[:br]
			[:br]
			[:br]
			[:p "Overtone is an open source audio environment being created 
					to explore musical ideas from synthesis and sampling to 
					instrument building, live-coding and collaborative jamming. 
					We use the SuperCollider synth server as the audio engine, 
					with Clojure being used to develop the APIs and the application. 
					Synthesizers, effects, analyzers and musical generators can be 
					programmed in Clojure."]]
					[:hr]
		
		[:div.fluid-row
			[:h4 "Exhibition"]
			[:br]]
		[:div.row
			[:div.span3
				[:img {:src "/img/creators.jpg"}]
				[:p {:style "margin-bottom: 0px;"} "a link to this project"]
				[:p "by Mr. Edison"]
				[:br]]
			[:div.span3
				[:img {:src "/img/maxplanck.jpg"}]
				[:p {:style "margin-bottom: 0px;"}"a link to this project"]
				[:p "by George Washington"]
				[:br]]
			[:div.span3
				[:img {:src "/img/soundmachines.jpg"}]
				[:p {:style "margin-bottom: 0px;"}"a link to this project"]
				[:p "by Thomas Jefferson"]
				[:br]]
			[:div.span3
				[:img {:src "/img/visualeditions.jpg"}]
				[:br]
				[:p {:style "margin-bottom: 0px;"}"a link to this project"]
				[:p "by Barrack Obama"]]]
				[:hr]


		[:div.row
			[:div.span3				
				[:h3 "cross platform"]
				[:p "Overtone runs on Linux, OSX, and Windows. The only core dependency is Java. In most cases Overtone will provide a version of SuperCollider as part of its dependencies. However, you are also able to connect to an existing external version obtained here. Finally Linux users should have a version of the jack audio server installed"]]
			[:div.span3	
				[:h3 "future plans"]
				[:p "We’ve got big ideas for Project Overtone, and we can use 
						all the help we can get. For example, we plan to build a 
						visual data flow editor similar to PureData or Max/MSP. 
						Also, we’d like integrate with a peer-to-peer network that 
						allowing for collaborative composition and jam sessions 
						over the internet and sharing of instruments and effects. 
						Checkout the roadmap for more information about our future 
						plans."]]
			[:div.span3	
				[:h3 "download overtone"]
				[:p "You can clone the project with Git by running:"]
				[:p "$ git clone git://github.com/overtone/overtone.git"]
				[:p "You can also download the source package in a zip file."]]
			[:div.span3	
				[:h3 "learn clojure"]
				[:p "Clojure is a dialect of Lisp, and shares with Lisp the 
						code-as-data philosophy and a powerful macro system. 
						Clojure is predominantly a functional programming language, 
						and features a rich set of immutable, persistent data 
						structures. When mutable state is needed, Clojure offers a 
						software transactional memory system and reactive Agent 
						system that ensure clean, correct, multithreaded designs."]]]))



