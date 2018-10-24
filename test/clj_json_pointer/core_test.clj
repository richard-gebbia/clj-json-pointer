(ns clj-json-pointer.core-test
  (:require [clojure.test :refer :all]
            [midje.sweet :refer :all]
            [clj-json-pointer.core :refer :all]))

(facts "to-clj-ptr"
       (fact "Examples from the RFC (6901)"
             (to-clj-ptr "")       => []
             (to-clj-ptr "/foo")   => ["foo"]
             (to-clj-ptr "/foo/0") => ["foo" 0]
             (to-clj-ptr "/")      => [""]
             (to-clj-ptr "/a~1b")  => ["a/b"]
             (to-clj-ptr "/c%d")   => ["c%d"]
             (to-clj-ptr "/e^f")   => ["e^f"]
             (to-clj-ptr "/g|h")   => ["g|h"]
             (to-clj-ptr "/i\\j")  => ["i\\j"]
             (to-clj-ptr "/k\"l")  => ["k\"l"]
             (to-clj-ptr "/ ")     => [" "]
             (to-clj-ptr "/m~0n")  => ["m~n"]
             (to-clj-ptr "/ /")    => [" " ""])
       (fact "keywordize keys"
             (to-clj-ptr "/foo" :keywordize-keys true) => [:foo]
             (to-clj-ptr "/foo/0" :keywordize-keys true) => [:foo 0]))

(fact "JSON Pointer in Clojure"
      (json-ptr-get "{\"foo\": { \"bar\": 3 }}" "/foo/bar") => 3
      (json-ptr-get "{\"foo\": { \"bar\": 3 }}" "/foo/baz") => nil
      (json-ptr-get "{\"foo\": [{\"bar\": 3 }]}" "/foo/0/bar") => 3)
