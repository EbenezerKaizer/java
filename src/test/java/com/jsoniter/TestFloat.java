package com.jsoniter;

import junit.framework.TestCase;
import org.junit.experimental.categories.Category;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class TestFloat extends TestCase {

    private boolean isStreaming;

    public void test_positive_negative() throws IOException {
        // positive
        assertEquals(12.3f, parseFloat("12.3,"));
        assertEquals(729212.0233f, parseFloat("729212.0233,"));
        assertEquals(12.3d, parseDouble("12.3,"));
        assertEquals(729212.0233d, parseDouble("729212.0233,"));
        // negative
        assertEquals(-12.3f, parseFloat("-12.3,"));
        assertEquals(-12.3d, parseDouble("-12.3,"));
    }

    public void test_long_double() throws IOException {
        double d = JsonIterator.deserialize("4593560419846153055", double.class);
        assertEquals(4593560419846153055d, d, 0.1);
    }

    public void test_ieee_754() throws IOException {
        assertEquals(0.00123f, parseFloat("123e-5,"));
        assertEquals(0.00123d, parseDouble("123e-5,"));
    }

    public void test_decimal_places() throws IOException {
        assertEquals(Long.MAX_VALUE, parseFloat("9223372036854775807,"), 0.01f);
        assertEquals(Long.MAX_VALUE, parseDouble("9223372036854775807,"), 0.01f);
        assertEquals(9923372036854775807f, parseFloat("9923372036854775807,"), 0.01f);
        assertEquals(9923372036854775807d, parseDouble("9923372036854775807,"), 0.01f);
        assertEquals(720368.54775807f, parseFloat("720368.54775807,"), 0.01f);
        assertEquals(720368.54775807d, parseDouble("720368.54775807,"), 0.01f);
        assertEquals(72036.854775807f, parseFloat("72036.854775807,"), 0.01f);
        assertEquals(72036.854775807d, parseDouble("72036.854775807,"), 0.01f);
        assertEquals(720368.54775807f, parseFloat("720368.547758075,"), 0.01f);
        assertEquals(720368.54775807d, parseDouble("720368.547758075,"), 0.01f);
    }

    @Category(StreamingCategory.class)
    public void test_streaming() throws IOException {
        isStreaming = true;
        test_positive_negative();
        test_decimal_places();
    }

    private float parseFloat(String input) throws IOException {
        if (isStreaming) {
            return JsonIterator.parse(new ByteArrayInputStream(input.getBytes()), 2).readFloat();
        } else {
            return JsonIterator.parse(input).readFloat();
        }
    }

    private double parseDouble(String input) throws IOException {
        if (isStreaming) {
            return JsonIterator.parse(new ByteArrayInputStream(input.getBytes()), 2).readDouble();
        } else {
            return JsonIterator.parse(input).readDouble();
        }
    }
}
