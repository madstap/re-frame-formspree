(ns madstap.re-frame.formspree-fx
  (:require
   [clojure.spec.alpha :as s]
   [clojure.string :as str]
   [re-frame.core :as rf]
   [day8.re-frame.http-fx] ;; For side effects!
   [ajax.core :as ajax]))

(s/def ::email (s/and string? (complement str/blank?)))
(s/def ::params map?)
(s/def ::re-frame-dispatch
  (s/and vector? (s/cat :k keyword? :args (s/* any?))))
(s/def ::on-failure ::re-frame-dispatch)
(s/def ::on-success ::re-frame-dispatch)
(s/def ::args
  (s/keys :req-un [::email ::params]
          :opt-un [::on-success ::on-failure]))

(rf/reg-event-fx ::send
  [rf/trim-v]
  (fn [_ [{:keys [email params on-success on-failure]
           :or {on-success [::no-on-success]
                on-failure [::no-on-failure]}}]]
    {:http-xhrio {:method :post
                  :uri (str "https://formspree.io/" email)
                  :params params
                  :on-success on-success
                  :on-failure on-failure
                  :format          (ajax/json-request-format)
                  :response-format (ajax/json-response-format {:keywords? true})
                  :headers {"Accept" "application/json"}}}))

;; Default success and failure handlers are noops
(rf/reg-event-fx ::no-on-success (constantly {}))
(rf/reg-event-fx ::no-on-failure (constantly {}))

(rf/reg-fx :formspree
  (fn [args]
    (s/assert ::args args)
    (rf/dispatch [::send args])))
