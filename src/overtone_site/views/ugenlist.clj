(ns overtone-site.views.ugenlist
(:require [overtone-site.views.common :as common]
          [noir.content.getting-started])
  (:use [overtone.sc.machinery.ugen defaults specs categories]
        [overtone.helpers.string :only [capitalize]]
        [overtone.util lib doc]
        [noir.core :only [defpage defpartial]]
        [hiccup.core :only [html]]))


; (def UGEN-CATEGORIES
;   {"osc-n"          [["Generators" "Deterministic"]]
;    "pack-fft"        [["FFT"]]
;    "logistic"        [["Generators" "Chaotic"]]
;    "pitch"           [["Analysis" "Pitch"] ["MachineListening"]]
;    "pan-b2"          [["Multichannel" "Ambisonics"]]
;    "dust2"           [["Generators" "Stochastic"]]
;    "free-verb"       [["Reverbs"]]
;    "pluck"           [["Generators" "Deterministic"]]
;    "tw-choose"       [["Multichannel" "Select"]]
;    "lin-exp"         [["Maths"]]
;    "lin-rand"        [["Generators" "Stochastic"] ["Generators" "Single-value"]]
;    "grain-buf"       [["Buffer"] ["Generators" "Granular"]]
;    "send-trig"       [["Triggers"]]
;    "pv-rand-comb"    [["FFT"]]
;    "comb-n"          [["Delays"]]
;    "spec-flatness"   [["Analysis"] ["FFT"]]
;    "done"            [["Synth control"]]
;    "var-saw"         [["Generators" "Deterministic"]]
;    "amplitude"       [["Analysis" "Amplitude"]]
;    "pause-self"      [["Synth control"]]
;    "t2a"             [["Conversion"]]
;    "f-sin-osc"       [["Generators" "Deterministic"]]
;    "pv-rect-comb"    [["FFT"]]
;    "formant"         [["Generators" "Deterministic"]]
;    "abstract-out"    [["InOut"]]
;    "chaos-gen"       [["Generators" "Chaotic"]]
;    "pv-mag-mul"      [["FFT"]]
;    "tap"             [["Buffer"]]
;    "key-state"       [["User interaction"]]
;    "rand-id"         [["Generators" "Stochastic"]]
;    "n-rand"          [["Generators" "Stochastic"] ["Generators" "Single-value"]]
;    "latch"           [["Triggers"]]
;    "delay-n"         [["Delays"]]
;    "info-u-gen-base" [["Info"]]
;    "dyn-klang"       [["Generators" "Deterministic"]]
;    "pulse-divider"   [["Triggers"]]
;    "dwhite"          [["Demand"]]
;    "pv-chain-u-gen"  [["FFT"]]
;    "g-verb"          [["Reverbs"]]
;    "normalizer"      [["Analysis" "Amplitude"]]
;    "timer"           [["Triggers"]]
;    "grain-sin"       [["Generators" "Granular"]]
;    "lf-pulse"        [["Generators" "Deterministic"]]
;    "pv-mag-squared"  [["FFT"]]
;    "bi-pan-b2"       [["Multichannel" "Ambisonics"]]
;    "zero-crossing"   [["Analysis" "Pitch"]]
;    "mantissa-mask"   [["Filters" "Nonlinear"]]
;    "coin-gate"       [["Generators" "Stochastic"]]
;    "select-x"        [["Multichannel" "Select"]]
;    "v-osc"           [["Generators" "Deterministic"]]
;    "pv-rand-wipe"    [["FFT"]]
;    "lf-noise0"       [["Generators" "Stochastic"]]
;    "x-line"          [["Envelopes"]]
;    "pv-phase-shift"  [["FFT"]]
;    "dyn-klank"       [["Generators" "Deterministic"] ["Filters" "Linear"]]
;    "loudness"        [["Analysis" "Amplitude"]]
;    "p-sin-grain"     [["Generators" "Deterministic"]]
;    "warp1"           [["Buffer"] ["Generators" "Granular"]]
;    "i-rand"          [["Generators" "Stochastic"] ["Generators" "Single-value"]]
;    "saw"             [["Generators" "Deterministic"]]
;    "sin-osc"         [["Generators" "Deterministic"]]
;    "median"          [["Filters" "Nonlinear"]]
;    "pv-hainsworth-foote"  [["FFT"]]
;    "buf-delay-n"          [["Delays"]]
;    "t-delay"              [["Triggers"] ["Delays"]]
;    "v-osc3"               [["Generators" "Deterministic"]]
;    "buf-info-u-gen-base"  [["Buffer" "Info"]]
;    "slew"                 [["Filters" "Nonlinear"]]
;    "pause-self-when-done" [["Synth control"]]
;    "demand"               [["Demand"]]
;    "ifft"                 [["FFT"]]
;    "gendy1"               [["Generators" "Stochastic"]]
;    "i-env-gen"            [["Envelopes"]]
;    "peak-follower"        [["Analysis" "Amplitude"]]
;    "amp-comp-a"           [["Analysis" "Amplitude"]]
;    "in-rect"              [["Maths"]]
;    "free"                 [["Synth control"]]
;    "sweep"                [["Triggers"]]
;    "compander-d"          [["Analysis" "Amplitude"]]
;    "buf-rd"               [["Buffer"]]
;    "freq-shift"           [["Filters" "Nonlinear"] ["Filters" "Pitch"]]
;    "gendy2"               [["Generators" "Stochastic"]]
;    "t2k"                  [["Conversion"]]
;    "spring"               [["Filters" "Nonlinear"]]
;    "free-self"            [["Synth control"]]
;    "phasor"               [["Triggers"] ["Buffer"]]
;    "demand-env-gen"       [["Demand"] ["Envelopes"]]
;    "gendy3"               [["Generators" "Stochastic"]]
;    "degree-to-key"        [["Conversion"]]
;    "t-exp-rand"           [["Generators" "Stochastic"]]
;    "delay1"               [["Delays"]]
;    "decode-b2"            [["Multichannel" "Ambisonics"]]
;    "check-bad-values"     [["Info"]]
;    "pv-rect-comb2"        [["FFT"]]
;    "pm-osc"               [["Generators" "Deterministic"]]
;    "pv-mag-div"           [["FFT"]]
;    "mouse-x"              [["User interaction"]]
;    "env-gen"              [["Envelopes"]]
;    "abstract-in"          [["InOut"]]
;    "onsets"               [["Analysis"]]
;    "hilbert"              [["Filters" "Nonlinear"]]
;    "index-l"              [["Generators" "Deterministic"]]
;    "x-fade"               [["Multichannel" "Select"]]
;    "t-grains"             [["Buffer"] ["Generators" "Granular"]]
;    "stepper"              [["Triggers"]]
;    "blip"                 [["Generators" "Deterministic"]]
;    "convolution2l"        [["FFT"]]
;    "free-verb2"           [["Reverbs"]]
;    "hilbert-fir"          [["Filters" "Nonlinear"]]
;    "index"                [["Buffer"]]
;    "dust"                 [["Generators" "Stochastic"]]
;    "pan-b"                [["Multichannel" "Ambisonics"]]
;    "select-x-focus"       [["Multichannel" "Select"]]
;    "spec-centroid"        [["Analysis"] ["FFT"]]
;    "crackle"              [["Generators" "Stochastic"]]
;    "du-gen"               [["Demand"]]
;    "beat-track"           [["Analysis"] ["FFT"]]
;    "pv-bin-wipe"          [["FFT"]]
;    "t-windex"             [["Generators" "Stochastic"]]
;    "fft"                  [["FFT"]]
;    "line"                 [["Envelopes"]]
;    "silent"               [["Generators" "Single-value"]]
;    "scope-out"            [["Buffer"]]
;    "buf-wr"               [["Buffer"]]
;    "impulse"              [["Generators" "Deterministic"]]
;    "unpack1fft"           [["FFT"]]
;    "pv-bin-shift"         [["FFT"]]
;    "lin-lin"              [["Maths"]]
;    "exp-rand"             [["Generators" "Stochastic"] ["Generators" "Single-value"]]
;    "free-self-when-done"  [["Synth control"]]
;    "in-range"             [["Maths"]]
;    "c-osc"                [["Generators" "Deterministic"]]
;    "compander"            [["Analysis" "Amplitude"]]
;    "disk-in"              [["InOut"] ["Buffer"]]
;    "trig1"                [["Triggers"]]
;    "pitch-shift"          [["Filters" "Pitch"]]
;    "pv-jensen-andersen"   [["FFT"]]
;    "white-noise"          [["Generators" "Stochastic"]]
;    "lf-saw"               [["Generators" "Deterministic"]]
;    "pause"                [["Synth control"]]
;    "duty"                 [["Demand"]]
;    "pv-diffuser"          [["FFT"]]
;    "sync-saw"             [["Generators" "Deterministic"]]
;    "rotate2"              [["Multichannel" "Ambisonics"] ["Multichannel" "Panners"]]
;    "record-buf"           [["Buffer"]]
;    "detect-silence"       [["Synth control"] ["Analysis" "Amplitude"]]
;    "grain-in"             [["Generators" "Granular"]]
;    "amp-comp"             [["Analysis" "Amplitude"]]
;    "ugen"                 [["Unclassified"]]
;    "pv-mag-freeze"        [["FFT"]]
;    "filter"               [["Filters" "Linear"]]
;    "convolution"          [["FFT"]]
;    "t-ball"               [["Filters" "Nonlinear"]]
;    "klang"                [["Generators" "Deterministic"] ["Filters" "Linear"]]
;    "toggle-ff"            [["Triggers"]]
;    "pv-conformal-map"     [["FFT"]]
;    "rand-seed"            [["Generators" "Stochastic"]]
;    "grain-fm"             [["Generators" "Granular"]]
;    "unpack-fft"           [["FFT"]]
;    "pulse"                [["Generators" "Deterministic"]]
;    "mfcc"                 [["Analysis"]]
;    "k2a"                  [["Conversion"]]
;    "play-buf"             [["Buffer"]]
;    "pv-bin-scramble"      [["FFT"]]
;    "most-change"          [["Maths"]]
;    "mouse-button"         [["User interaction"]]
;    "a2k"                  [["Conversion"]]
;    "linen"                [["Envelopes"]]
;    "last-value"           [["Triggers"]]
;    "convolution2"         [["FFT"]]
;    "pulse-count"          [["Triggers"]]
;    "hasher"               [["Filters" "Nonlinear"]]
;    "pv-mag-smear"         [["FFT"]]
;    "convolution3"         [["FFT"]]
;    "select"               [["Multichannel" "Select"]]
;    "buf-comb-n"           [["Delays"]]
;    "audio-in"             [["InOut"]]
;    "pv-mag-above"         [["FFT"]]
;    "spec-pcile"           [["Analysis"] ["FFT"]]
;    "key-track"            [["Analysis" "Pitch"]]
;    "ti-rand"              [["Generators" "Stochastic"]]
;    "klank"                [["Generators" "Deterministic"] ["Filters" "Linear"]]
;    "running-sum"          [["Maths"]]
;    "pv-brick-wall"        [["FFT"]]
;    "stereo-convolution2l" [["FFT"]]
;    "t-rand"               [["Generators" "Stochastic"]]
;    "beat-track2"          [["Analysis"] ["FFT"]]
;    "disk-out"             [["InOut"] ["Buffer"]]
;    "panner"               [["Multichannel" "Panners"]]
;    "dc"                   [["Generators" "Single-value"]]
;    "fft-trigger"          [["FFT"]]
;    "sin-osc-fb"           [["Generators" "Deterministic"]]
;    "ball"                 [["Filters" "Nonlinear"]]
;    "osc"                  [["Generators" "Deterministic"]]
;    "poll"                 [["Info"]]})



(def CATEGORY-PREFIX "/ugen-list/category/")
(def UGEN-PREFIX "/ugen-doc/")


(defn xform [ic]
  (reduce (fn [result [k vs]]
        (reduce (fn [r v]
          (let [sub-category (if (nil? (second v))
                                (str "General")
                                (second v))
                main-key (if (nil? (first v))
                                (str "General")
                                (first v))]
                (assoc-in r [main-key sub-category] (cons k (get-in r [main-key sub-category])))))
            result vs)
        )
      {} ic))

(def t (xform UGEN-CATEGORIES))
; (pprint (xform2 UGEN-CATEGORIES))

(defn- categories-str
  "Returns a string representing the categories of a ugen spec"
  [spec]
    (apply str 
      (interpose ", " 
        (map 
          (fn [cat] 
            (apply str (interpose " -> " (cond 
                                          (= 1 (count cat)) (assoc cat "General")
                                          (= 0 (count cat)) "General")
                                          )))) 
          (:categories spec))))


(defpartial ugen-row
  [ugen-name category]
  [:div.row-fluid
    [:div.span12
      [:div.span2
        [:strong 
          [:a {:href (str "/ugendoc/" ugen-name)} ugen-name]]]
      [:div.span6
        [:p (categories-str (get-ugen ugen-name))]]]])

(defpartial ugen-categories
  [categories]
    [:div.row-fluid
     [:p categories]
      (map 
        (fn [c] 
          [:div.span1 
            [:a {:href (str CATEGORY-PREFIX c)} c]]) categories)
      ])

(def overtone-ugen-names (zipmap
   (map overtone-ugen-name (keys UGEN-CATEGORIES))
   (vals UGEN-CATEGORIES)))

(def xmap (xform UGEN-CATEGORIES))

(defpartial category-block [category]
  (map (fn [[k vs]]
      [:div.span11
        [:div.row [:i k]]
        [:div.row
          (map (fn [v]
              [:a {:href (str UGEN-PREFIX v)} (str v) [:span " / "] ]) vs)][:br]]) category))

(defpartial xform-to-partial 
  [xf]
  (map (fn [[k vs]]
    [:div.accordion {:id (str k "-accordion")}
      [:div.accordion-group
        [:div.accordion-heading
          [:a.accordion-toggle {:data-toggle "collapse" 
                                :data-parent (str "#" k "-accordion") 
                                :href (str "#" k "-collapse")}
            [:h3 (str k)]]]
        [:div.accordion-body.collapse.in {:id (str "#" k "-collapse")}
          [:div.accordion-inner
          (category-block vs)]]]]) 
    (sort xf)))

(defpage "/ugen-list" []
         (common/layout
            [:div.row 
                [:div.hero-unit 
                  [:h1 "Ugens (API)"]
                  [:p#ugen-doc-args-title "Overtone is an open 
                        source audio environment being created 
                        to explore musical ideas from synthesis 
                        and sampling to instrument building, 
                      live-coding and collaborative jamming"]]]
            [:div.row 
              [:div.span12
                (xform-to-partial (sort-by count xmap))]]))
