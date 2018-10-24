# clj-json-pointer

Converts [JSON Pointer strings](https://tools.ietf.org/html/rfc6901) into Clojure paths that work with core functions like `get-in`, `assoc-in`, etc.

## Usage

```clojure
=> (require '[clj-json-pointer.core :as jptr])
nil

=> (jptr/to-clj-ptr "/foo/bar/0")
["foo" "bar" 0]

=> (jptr/to-clj-ptr "/foo/bar/0" :keywordize-keys true)
[:foo :bar 0]
```

## License

Copyright © 2018 Richard Gebbia

Distributed under the BSD 2-Clause License
