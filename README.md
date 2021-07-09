# peernode
a program that runs inside forked js-ipfs daemon and exposes pubsub over rsocket

> the program is useful as an example and a reference, but turned out redundant:    
> https://github.com/sergeiudris/deathstar.lab/blob/5cd3256b6509bfb894f251a4030d9837449c0060/docs/design/design.md#no-need-for-peernode-and-ipfs-node-as-a-container-is-optional

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
- cljctools/rsocket abstraction over rsocket-java and rsocket-js
  - https://github.com/cljctools/cljctools/blob/main/rsocket-java/src/cljctools/rsocket/impl.clj
  - https://github.com/cljctools/cljctools/blob/main/rsocket-js/src/cljctools/rsocket/impl.cljs


## run

- requires docker installed (probably 19.03+)
- but all installation and build happen inside a container, so it will compile

```bash
# in the same dir clone repos and checkout git commits 
# (select this whole command and run it)
git clone https://github.com/cljctools/peernode \
&& cd peernode \
&& git checkout 3cfdd777786eecda4f43d9bc00bbffd43f657b75 \
&& cd ../ \
&& git clone https://github.com/cljctools/cljctools \
&& cd cljctools \
&& git checkout 0a34a2da45b4d9b22b2259ce393d47dc75707a5b \
&& cd ../ \
&& git clone https://github.com/sergeiudris/js-ipfs \
&& cd js-ipfs \
&& git checkout 36d504f05d626feea52396d61cb1a33a17c5b596 \
&& cd ../

# open two terminals, in the first: 
# build and run the container 
cd peernode/bin
bash f dock # now we are inside the container
bash f dev # run shadow-cljs dev server 
# we should see  [:main] Build completed. (147 files, 1 compiled, 0 warnings, 0.83s)

# in the second terminal: 
# open another bash into the same container
# navigate to js-ipfs and install it's deps, it takes a minute 
# (but all happens inside container, so it'll work)
cd peernode/bin
bash f into peernode
cd /ctx/js-ipfs
npm i && ./node_modules/.bin/lerna bootstrap
# finally, run the dameon and peernode inside it
cd packages/ipfs
bash init-and-daemon.sh # we'll logs telling ipfs is running

# open REPL into the peernode: 
#   connect to localhost:8899 
#   open peernode/src/impl/src/cljctools/peernode/main.cljs

# an example of REPLing with VScode and Calva 2.0.91: 
#  open VScode and peernode/bin/shadow-cljs.edn
#  press ctrl+alt+c+c (c twice), 
#  select 'shadow-cljs' project type
#  enter localhost:8899 (it will popup itself, if shadow-cljs.edn is open)
#  select :main build, repl tabs will open
#  open file peernode/src/impl/src/cljctools/peernode/main.cljs, 
#  REPL into js-ipfs daemon works: there is comment block to eval some expressions

# cleanup: use sudo 
# docker container by default uses USER=0:0 (not 1000:1000)
# (or change peernode/bin/f by providing -u option to docker run)
sudo rm -rf peernode js-ipfs

```