(ns overtone-site.views.overtone-docs
(:require [overtone-site.views.common :as common]
          [clojure.repl :as repl]
          )
  (:use 
        [overtone-site.models.webdocs]
        [overtone.util doc]
        [overtone.live]
        [overtone.sc.machinery.ugen defaults specs]
        [noir.core :only [defpage defpartial]]
        [hiccup.core :only [html]
        ]))

(defn ns-publics-list [ns] (#(list (ns-name %) (map first (ns-publics %))) ns))


; ; (def overtone-keys (ns-publics-list 'overtone.live))


; (defpartial item-row [item]
;   [:div.row
;     [:div.span3
;       [:p item]]
;     [:div.span9
;       [:p (meta #'(eval (str "overtone.live/" (first item))))]]
;       ])

; (defpartial docs [] 
;   (map #(item-row %) (sort (second (ns-publics-list 'overtone.live)))))

; (defpage "/docs" []
;   (common/layout
;     (docs)))

; (defn get-functions []
;   (map 
;     (fn [k] (if (not (nil? (get #'k (ns-publics 'overtone.live))))
;                 (pprint (str (meta #'(get #'k (ns-publics 'overtone.live)))))
;                 (pprint "no info for this function")))
;     (keys (ns-publics 'overtone.live))))