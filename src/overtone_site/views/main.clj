(ns overtone-site.views.main
  (:require [overtone-site.views.common :as common]
            [noir.content.getting-started])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]))


(defpage "/" []
         (common/layout
        	[:div.fluid-row
        		[:div.span6
        			[:br]
					[:a {:href "/download"} [:h1 "Download Overtone"]]
					[:br]
					[:a {:href "/try"} [:h1 "Try Overtone Online"]]
					[:br]
					[:a {:href "/tutorials"} [:h1 "Browse Interactive Tutorials"]]
					[:br]
					[:br]
					[:p
						"Overtone is an open source programming language and environment
						 for people who want to create images, animations, and interactions.
						  Initially developed to serve as a software sketchbook and to teach
						   fundamentals of computer programming within a visual context, 
						   Overtone also has evolved into a tool for generating finished
						    professional work. Today, there are tens of thousands of students,
						     artists, designers, researchers, and hobbyists who use Overtone 
						     for learning, prototyping, and production."]
					[:ul
						[:li
							[:p "Free to download and open source"]]
						[:li
							[:p "Interactive programs using 2D, 3D or PDF output"]]
						[:li
							[:p "OpenGL integration for accelerated 3D"]]
						[:li
							[:p "For GNU/Linux, Mac OS X, and Windows"]]
						[:li
							[:p "Projects run online or as double-clickable applications"]]
						[:li
							[:p "Over 100 libraries extend the software into sound, video, computer vision, and more..."]]
						[:li
							[:p "Well documented, with many books available"]]]]
				[:div.span3
					[:div.fluid-row
						[:h3 "Exhibition"]]
					[:div.fluid-row
						[:img {:src "/img/creators.jpg"}]
						[:p {:style "margin-bottom: 0px;"} "a link to this project"]
						[:p "by Mr. Edison"]
						[:br]]
					[:div.fluid-row
						[:img {:src "/img/maxplanck.jpg"}]
						[:p {:style "margin-bottom: 0px;"}"a link to this project"]
						[:p "by George Washington"]
						[:br]]
					[:div.fluid-row
						[:img {:src "/img/soundmachines.jpg"}]
						[:p {:style "margin-bottom: 0px;"}"a link to this project"]
						[:p "by Thomas Jefferson"]
						[:br]]
					[:div.fluid-row
						[:img {:src "/img/visualeditions.jpg"}]
						[:br]
						[:p {:style "margin-bottom: 0px;"}"a link to this project"]
						[:p "by Barrack Obama"]
						[:br]]]]))