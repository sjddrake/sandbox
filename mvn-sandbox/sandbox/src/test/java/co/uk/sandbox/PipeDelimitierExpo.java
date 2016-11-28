package co.uk.sandbox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PipeDelimitierExpo {

    private final static String PIPE_DELIMITER = "|";

    @Test
    public void testRangeOfSingleDigits() {
        final String startValue = "1";
        final String endValue = "9";
        final String expectedValue = "1|2|3|4|5|6|7|8|9";
        final String actualValue = doIt(startValue, endValue);

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testRangeOfDoubleDigits() {
        final String startValue = "23";
        final String endValue = "36";
        final String expectedValue = "23|24|25|26|27|28|29|30|31|32|33|34|35|36";
        final String actualValue = doIt(startValue, endValue);

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testRangeOfOnePage() {
        final String startValue = "12";
        final String endValue = "12";
        final String expectedValue = "12";
        final String actualValue = doIt(startValue, endValue);

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testInvalidRange() {

        final String startValue = "12";
        final String endValue = "6";

        String exceptionMessage = null;
        try {
            doIt(startValue, endValue);
        } catch (final Exception e) {
            exceptionMessage = e.getMessage();
        }

        assertNotNull("Expected an exception to have been caught!", exceptionMessage);
        assertTrue("Not the expected message!", exceptionMessage.contains("12"));
    }

    private String doIt(final String startValue, final String endValue) {

        final String errorMessageSeed = "Failed to convert %s to a number. Value was: ";

        int start = 0;
        try {
            start = Integer.valueOf(startValue);
        } catch (final Exception e) {
            final String message = String.format(errorMessageSeed, "scooby start field name");
            throw new RuntimeException(message + startValue);
        }

        int end = 0;
        try {
            end = Integer.valueOf(endValue);
        } catch (final Exception e) {
            final String message = String.format(errorMessageSeed, "scooby end field name");
            throw new RuntimeException(message + startValue);
        }

        final String pagesValue = buildPagesValue(start, end);
        return pagesValue;
    }

    private String buildPagesValue(final int startValue, final int endValue) {

        // quick sanity check
        if (endValue < startValue) {
            final String message = String.format("The page range values are invalid. "
                    + "Values given were start = %s and end = %s", startValue, endValue);
            throw new IllegalArgumentException(message);
        }

        final StringBuilder buf = new StringBuilder();
        boolean firstValueIsProcessed = false;
        for (int i = startValue; i < (endValue + 1); i++) {
            if (firstValueIsProcessed) {
                buf.append(PIPE_DELIMITER);
            } else {
                firstValueIsProcessed = true;
            }
            buf.append(i);
        }
        return buf.toString();
    }
}
