(ns madstap.re-frame.formspree-fx.specs
  (:require
   [clojure.string :as str]
   [clojure.spec.alpha :as s]))

(s/def ::email (s/and string? (complement str/blank?)))
(s/def ::params (s/map-of (s/or :str string?
                                :kw simple-keyword?)
                          string?))
(s/def ::re-frame-dispatch
  (s/and vector? (s/cat :k keyword? :args (s/* any?))))
(s/def ::on-failure ::re-frame-dispatch)
(s/def ::on-success ::re-frame-dispatch)
(s/def ::formspree
  (s/keys :req-un [::email ::params]
          :opt-un [::on-success ::on-failure]))
