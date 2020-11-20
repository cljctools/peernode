(ns cljctools.peernode.spec
  #?(:cljs (:require-macros [cljctools.peernode.spec]))
  (:require
   [clojure.spec.alpha :as s]))

(s/def ::id string?)

(s/def ::from ::id)
(s/def ::seqno string?)
(s/def ::data any?)
(s/def ::topic-id string?)
(s/def ::topic-ids (s/coll-of ::topic-id))

