package com.manya;


import java.util.UUID;

public final class UuidUtil {
    public static final int BYTES = Long.BYTES * 2;
    private UuidUtil() {}
    public static byte[] toByteArray(UUID uuid) {
        byte[] result = new byte[BYTES];
        toByteArray(uuid, result, 0);
        // hello
        return result;
    }
    public static void toByteArray(UUID uuid, byte[] byteArray, int offset) {
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();

        for (int i = 7; i >= 0; i--) {
            byteArray[offset + i] = (byte) (msb & 0xffL);
            msb >>= 8;
        }
        for (int i = 15; i >= 8; i--) {
            byteArray[offset + i] = (byte) (lsb & 0xffL);
            lsb >>= 8;
        }
    }

    public static UUID fromByteArray(byte[] byteArray) {
        return fromByteArray(byteArray, 0);
    }
    public static UUID fromByteArray(byte[] byteArray, int offset) {
        return new UUID(
                longFromBytes(
                        byteArray[offset], byteArray[offset + 1],
                        byteArray[offset + 2], byteArray[offset + 3],
                        byteArray[offset + 4], byteArray[offset + 5],
                        byteArray[offset + 6], byteArray[offset + 7]),
                longFromBytes(
                        byteArray[offset + 8], byteArray[offset + 9],
                        byteArray[offset + 10], byteArray[offset + 11],
                        byteArray[offset + 12], byteArray[offset + 13],
                        byteArray[offset + 14], byteArray[offset + 15]));
    }
    private static long longFromBytes(byte b1, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7, byte b8) {
        return (b1 & 0xffL) << 56 | (b2 & 0xffL) << 48 | (b3 & 0xffL) << 40 | (b4 & 0xffL) << 32 | (b5 & 0xffL) << 24
                | (b6 & 0xffL) << 16 | (b7 & 0xffL) << 8 | (b8 & 0xffL);
    }



}
