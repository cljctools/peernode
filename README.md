# peernode
a program that runs inside forked js-ipfs daemon and exposes pubsub over rsocket

- this program is used by https://github.com/DeathStarGame/DeathStarGame, but is generic
- as noted here https://github.com/sergeiudris/deathstar.lab/blob/cd56a2f3a77e14951106131a1b87bbf756b0eeb4/docs/design/design.md#ipfs-node-pubsub-stream-cannot-be-consumed-from-jvm-app-either-use-libp2p-or-fork-modify-js-ipfs-node
- it runs inside the fork of js-ipfs https://github.com/sergeiudris/js-ipfs
- a jvm game app and peernode (running on nodejs) talk over rsocket via same clojure common interface (cljctools/rsocket), only different implementations (jvm and generic node/browser)
  - example works on both jvm/node/browser https://github.com/cljctools/cljctools/blob/0a34a2da45b4d9b22b2259ce393d47dc75707a5b/rsocket-meta/src/cljctools/rsocket/examples.cljc

- IPFS project
  - https://ipfs.io/
  - http://github.com/ipfs
- RSocket protocol
  - http://rsocket.io/
  - http://github.com/rsocket