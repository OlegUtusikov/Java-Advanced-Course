package ru.ifmo.rain.utusikov.walk;

class HashFNV {
    private final int MOD = 0x01000193;
    private final int DEFAULT = 0x811c9dc5;
    private int hash = 0;

    HashFNV() {
        hash = DEFAULT;
    }

    void compute(byte b) {
        hash = (hash * MOD) ^ (b & 0xff);
    }
    void compute(final byte[] bytes, int len) {
        for(int i = 0; i < len; ++i) {
            compute(bytes[i]);
        }
    }

    int getHash() {
        return hash;
    }
}
