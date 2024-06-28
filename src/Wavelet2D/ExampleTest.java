package Wavelet2D;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;

package Wavelet2D;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ExampleTest {

    @Test
    public void testAddition() {
        // 1. 2つの数値の加算をテストする例
        int sum = 1 + 1;
        assertEquals("Sum of 1 + 1 should be 2", 2, sum);
    }

    @Test
    public void testStringNotNull() {
        // 2. 文字列がnullでないことを確認する例
        String example = "JUnit test";
        assertNotNull("The example object should not be null", example);
    }

    @Test
    public void testCondition() {
        // 3. 条件が真であることを確認する例
        boolean condition = (5 > 1);
        assertTrue("5 is greater than 1", condition);
    }
}
