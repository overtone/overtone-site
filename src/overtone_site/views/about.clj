(ns overtone-site.views.about
  (:require [overtone-site.views.common :as common]
            [noir.content.getting-started])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]))

(defpage "/about" []
         (common/layout
	     	[:div.span12
	     		[:div.span9
		     		[:h3 "About"]
		     		[:br]
					[:p "Overtone is an open source audio environment being created to explore musical ideas from synthesis and sampling to instrument building, live-coding and collaborative jamming. We use the SuperCollider synth server as the audio engine, with Clojure being used to develop the APIs and the application. Synthesizers, effects, analyzers and musical generators can be programmed in Clojure."]
		     		[:br]
					[:p "Come and join the Overtone Google Group if you want to get involved in the project or have any questions about how you can use Overtone to make cool sounds and music."]
		     		[:br]
					[:h3 "Cross Platform"]
		     		[:br]
					[:p "Overtone runs on Linux, OSX, and Windows. The only core dependency is Java. In most cases Overtone will provide a version of SuperCollider as part of its dependencies. However, you are also able to connect to an existing external version obtained here. Finally Linux users should have a version of the jack audio server installed"]
		     		[:br]
					[:h3 "Future Plans"]
		     		[:br]
					[:p "We’ve got big ideas for Project Overtone, and we can use all the help we can get. For example, we plan to build a visual data flow editor similar to PureData or Max/MSP. Also, we’d like integrate with a peer-to-peer network that allowing for collaborative composition and jam sessions over the internet and sharing of instruments and effects. Checkout the roadmap for more information about our future plans."]
		     		[:br]
					[:p "Please get in touch and start a conversation if you are interested in joining in."]
		     		[:br]
					[:h3 "Download"]
		     		[:br]
					[:p "You can clone the project with Git by running:"]
					[:p "$ git clone git://github.com/overtone/overtone.git"]
					[:p "You can also download the source package in a zip file."]
				]]))
	           
