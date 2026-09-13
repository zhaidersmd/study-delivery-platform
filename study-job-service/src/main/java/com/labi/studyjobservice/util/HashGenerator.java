package com.labi.studyjobservice.util;

import com.labi.studyjobservice.dto.CreateStudyJobRequest;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;

public class HashGenerator {

    public static String generateRequestHash(String customerId, CreateStudyJobRequest request, String idempotencyKey) {

        String value = String.join("|", customerId, idempotencyKey, request.deliveryId(), request.recipientId(), request.studyId());

        try {

            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(hash);

        } catch (Exception e) {
            throw new IllegalStateException("Unable to generate request hash", e);
        }
    }
}
