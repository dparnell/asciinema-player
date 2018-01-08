(ns asciinema.player.stream
  (:require [asciinema.player.core :as p]
            [goog.Uri :as uri]
            [goog.dom :as dom]))

(defonce options {:speed 1
                  :auto-play true
                  :preload false
                  :loop false
                  ;; :poster "data:text/plain,\n\r  test \u001b[1;32msnapshot"
                  ;; :title "Something cool"
                  ;; :author "sickill"
                  ;; :author-url "http://ku1ik.com/"
                  ;; :author-img-url "https://gravatar.com/avatar/2807e23da22a140cf573ea75b37d11f6?s=128&d=retro"
                  })

(def session
  (-> js/location.href uri/parse .getQueryData (.get "session")))

(js/console.log "SESSION" session)

(defonce player-state (p/make-player-ratom (str "/stream/" session) (assoc options :type :stream)))

(defn ^:export -main []
  (js/console.log "HELLO")
  (let [dom-node (. js/document (getElementById "player"))]
    (if session
      (p/mount-player-with-ratom player-state dom-node)
      (goog/createDom "h1" "Please provide session id"))))

