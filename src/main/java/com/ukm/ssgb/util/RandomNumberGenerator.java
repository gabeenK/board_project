package com.ukm.ssgb.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.stream.Collectors;

public class RandomNumberGenerator {
    public static String generateCertNoString() {
        return Arrays.stream(generateCertNo())
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
    }

    private static int[] generateCertNo() {
        try {
            return SecureRandom.getInstance("SHA1PRNG")
                    .ints(6, 0, 10)
                    .toArray();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
