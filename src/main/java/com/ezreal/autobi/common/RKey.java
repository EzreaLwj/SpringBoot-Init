package com.ezreal.autobi.common;

public enum RKey {
    ;

    public static enum UserKey {
        USER_SESSION("user:session:");

        UserKey(String key) {
            this.key = key;
        }

        private final String key;

        public String getKey() {
            return key;
        }
    }
}
