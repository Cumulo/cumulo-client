
Cumulo Client
----

Use this with `cumulo/server`.

### Usage

[![Clojars Project](https://img.shields.io/clojars/v/cumulo/client.svg)](https://clojars.org/cumulo/client)

```clojure
[cumulo/client "0.1.1"]
```

```clojure
(defn configs {:url "ws://localhost:4010"
               :on-open! identity
               :on-close! identity})

(cumulo-client.core/setup-socket! store-ref configs)
```

### Develop

Based on https://github.com/mvc-works/stack-workflow

### License

MIT
