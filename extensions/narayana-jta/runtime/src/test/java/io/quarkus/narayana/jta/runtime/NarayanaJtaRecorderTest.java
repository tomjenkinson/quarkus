package io.quarkus.narayana.jta.runtime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class NarayanaJtaRecorderTest {

    @Test
    void testByteLength() {
        TransactionManagerConfiguration transactions = new TransactionManagerConfiguration();
        transactions.shortenNodeNameIfNecessary = true;
        String originalNodeName = "dfe2420d-b12e-4ec3-92c0-ee7c4";
        assertTrue(originalNodeName.getBytes(StandardCharsets.UTF_8).length > 28);
        NarayanaJtaRecorder r = new NarayanaJtaRecorder();
        transactions.nodeName = originalNodeName;
        r.setNodeName(transactions);
        int numberOfBytes = transactions.nodeName.getBytes(StandardCharsets.UTF_8).length;
        assertEquals(28, numberOfBytes,
                "node name bytes was not 28 bytes limit, number of bytes is " + numberOfBytes);
    }

    @Test
    void testPredictableConversion() {
        TransactionManagerConfiguration transactions = new TransactionManagerConfiguration();
        transactions.shortenNodeNameIfNecessary = true;
        String originalNodeName = "dfe2420d-b12e-4ec3-92c0-ee7c4";
        assertTrue(originalNodeName.getBytes(StandardCharsets.UTF_8).length > 28);
        NarayanaJtaRecorder r = new NarayanaJtaRecorder();
        transactions.nodeName = originalNodeName;
        r.setNodeName(transactions);
        int numberOfBytes = transactions.nodeName.getBytes(StandardCharsets.UTF_8).length;
        assertEquals(28, numberOfBytes,
                "node name bytes was not 28 bytes limit, number of bytes is " + numberOfBytes);
        String firstConversion =transactions.nodeName;
        transactions.nodeName = originalNodeName;
        r.setNodeName(transactions);
        String secondConversion = transactions.nodeName;
        numberOfBytes = transactions.nodeName.getBytes(StandardCharsets.UTF_8).length;
        assertEquals(28, numberOfBytes,
                "node name bytes was not 28 bytes limit, number of bytes is " + numberOfBytes);
        assertEquals(firstConversion, secondConversion,
                "Node names were shortened differently: " + firstConversion + " " + secondConversion);
    }

}
