# re-frame-formspree

A re-frame effects handler for [formspree](https://formspree.io/).

For when you want a no-setup contact form in your re-frame app.

Uses [re-frame-http-fx](https://github.com/Day8/re-frame-http-fx).

## Usage

Add this to your dependencies vector:

`[madstap/re-frame-formspree "0.1.0"]`

Then use the effect like this:

```clojure
(ns app.events
  (:require
   ;; Looks unused, but registers the fx as a side effect.
   [madstap.re-frame.formspree-fx]
   [re-frame.core :as rf]))

(rf/reg-event-fx :contact-form/submit
  [rf/trim-v]
  (fn [_ [email msg]]
    {:formspree {:email "customerservice@example.com"
                 :params {:email email
                          :msg msg}
                 :on-success [:contact-form/succeeded]
                 :on-failure [:contact-form/failed]}}))
```

`:params` is what is sent to formspree. They have some special keywords that
are documented on [their site](https://formspree.io/).

`:on-success` and `:on-failure` are optional and work exactly like in re-frame-http-fx.

## License

Copyright © 2017 Aleksander Madland Stapnes

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
