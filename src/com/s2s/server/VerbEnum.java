package com.s2s.server;

public enum VerbEnum {
    POST {
        public String toString() {
            return "POST";
        }
    },
    GET {
        public String toString() {
            return "GET";
        }
    },
    PUT {
        public String toString() {
            return "PUT";
        }
    },
    DELETE {
        public String toString() {
            return "DELETE";
        }
    }
}

