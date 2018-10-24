(ns clj-json-pointer.core
  (:require [clojure.string :as str]
            [cheshire.core :as json]))

(defn try-parse-number-key
  [key & {:keys [keywordize-keys] :as options}]
  (try
    (Integer/parseInt key)
    (catch NumberFormatException _ (if keywordize-keys (keyword key) key))))

(defn to-clj-ptr
  [json-ptr & {:keys [keywordize-keys] :as options}]
  (if (= "" json-ptr) []
      (do (when-not (str/starts-with? json-ptr "/")
            (throw (ex-info "Invalid JSON Pointer. JSON Pointer must either be an empty string or start with '/'."
                            {:json-ptr json-ptr})))
          (let [last-char (get json-ptr (dec (count json-ptr)))
                parts (->> (str/split json-ptr #"/")
                           (rest) ;; ignore the first one, it should always be ""
                           (map #(str/replace % #"~1" "/"))
                           (map #(str/replace % #"~0" "~"))
                           (mapv #(try-parse-number-key % :keywordize-keys keywordize-keys)))]
            (if (= \/ last-char)
              (conj parts "")
              parts)))))

(defn json-ptr-get
  [json json-ptr]
  (let [decoded (json/decode json)
        clj-ptr (to-clj-ptr json-ptr)]
    (get-in decoded clj-ptr)))
