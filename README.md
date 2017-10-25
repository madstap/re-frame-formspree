# re-frame-formspree

A re-frame effects handler for [formspree](https://formspree.io/).

For when you want a no-setup contact form in your re-frame thing.

Uses [re-frame-http-fx](https://github.com/Day8/re-frame-http-fx).

## Usage

`[madstap/re-frame-formspree "0.1.0-SNAPSHOT"]`

```clojure
(ns app.events
  (:require
   ;; Seems unused, but registers the fx as a side effect.
   [madstap.re-frame.formspree-fx]
   [re-frame.core :as rf]))

(rf/reg-event-fx :contact-form/submit
  [rf/trim-v]
  (fn [_ [email msg]]
    {:formspree {:email "customerservice@example.com"
                 :params {:_replyto email, :msg msg}}})
```

## License

Copyright © 2017 Aleksander Madland Stapnes

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
