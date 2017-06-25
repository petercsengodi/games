(def gameCallback (reify hu.csega.game.clojure.ClojureWrapper
(run [this t]
  "Hello World!"
)))

(.start (new hu.csega.game.clojure.ClojureRunner) gameCallback) 