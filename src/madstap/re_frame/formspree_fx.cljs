(ns madstap.re-frame.formspree-fx
  (:require
   [re-frame.core :as rf]
   [day8.re-frame.http-fx] ;; For side effects!
   [ajax.core :as ajax]))

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
    (rf/dispatch [::send args])))
