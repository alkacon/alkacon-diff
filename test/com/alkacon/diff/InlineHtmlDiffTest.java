package com.alkacon.diff;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Locale;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class InlineHtmlDiffTest {

    @Test
    public void testInlineHtmlDiffSame() throws Exception {
        InlineHtmlDiffConfiguration inlineConf = getDefaultStyles();

        StringWriter writer = new StringWriter(4096);
        Diff.diffAsInlineHtml("abc", "abc", writer, inlineConf);

        assertEquals("", writer.toString());
    }

    @Test
    public void testInlineHtmlDiff1() throws Exception {
        InlineHtmlDiffConfiguration inlineConf = getDefaultStyles();

        StringWriter writer = new StringWriter(4096);
        Diff.diffAsInlineHtml("abc", "def", writer, inlineConf);

        assertEquals(getExpectedResult1(), writer.toString());
    }

    private String getExpectedResult1() {
        String result = "<div style=\"removedLine\"><span style=\"removedBlock\">abc</span></div>\r\n";
        result += "<div style=\"addedLine\"><span style=\"addedBlock\">def</span></div>\r\n";
        return result;
    }

    @Test
    public void testInlineHtmlDiff2() throws Exception {
        InlineHtmlDiffConfiguration inlineConf = getDefaultStyles();

        StringWriter writer = new StringWriter(4096);
        Diff.diffAsInlineHtml("abc\nfoo\nbar", "abc\nfOo\nbar", writer, inlineConf);

        assertEquals(getExpectedResult2(), writer.toString());
    }

    private String getExpectedResult2() {
        String result = "<div style=\"unchangedLine\"><span style=\"unchangedBlock\">abc</span></div>\r\n";
        result += "<div style=\"removedLine\"><span style=\"removedBlock\">foo</span></div>\r\n";
        result += "<div style=\"addedLine\"><span style=\"addedBlock\">fOo</span></div>\r\n";
        result += "<div style=\"unchangedLine\"><span style=\"unchangedBlock\">bar</span></div>\r\n";
        return result;
    }

    @Test
    public void testInlineHtmlDiff3() throws Exception {
        InlineHtmlDiffConfiguration inlineConf = getDefaultStyles();

        StringWriter writer = new StringWriter(4096);
        Diff.diffAsInlineHtml("What if I change a single word in here?", "What if I change a SINGLE word in here?",
            writer, inlineConf);

        assertEquals(getExpectedResult3(), writer.toString());
    }

    private String getExpectedResult3() {
        String result = "<div style=\"removedLine\"><span style=\"unchangedBlock\">What if I change a </span><span style=\"removedBlock\">single</span><span style=\"unchangedBlock\"> word in here?</span></div>\r\n";
        result += "<div style=\"addedLine\"><span style=\"unchangedBlock\">What if I change a </span><span style=\"addedBlock\">SINGLE</span><span style=\"unchangedBlock\"> word in here?</span></div>\r\n";
        return result;
    }

    @Test
    public void testBothDiffMethodsReturnTheSameResult() throws Exception {
        InlineHtmlDiffConfiguration inlineConf = getDefaultStyles();

        String in1, in2;
        in1 = readFile("com/alkacon/diff/testHtml_01a.html", "ISO-8859-1");
        in2 = readFile("com/alkacon/diff/testHtml_01b.html", "ISO-8859-1");

        StringWriter writer2 = new StringWriter(4096);
        Diff.diffAsInlineHtml(in1, in2, writer2, inlineConf);

        String result = Diff.diffAsInlineHtml(in1, in2, inlineConf);

        assertEquals(writer2.toString(), result);
    }

    @Test
    public void testInlineHtmlDiffNulls() throws Exception {
        InlineHtmlDiffConfiguration inlineConf = getDefaultStyles();

        StringWriter writer = new StringWriter(4096);
        try {
            Diff.diffAsInlineHtml(null, "", writer, inlineConf);
            fail("Should have thrown a NullPointerException");
        } catch (NullPointerException e) {
            // All good, moving on
        } catch (Exception e) {
            // Unexpected
            throw e;
        }

        try {
            Diff.diffAsInlineHtml("", null, writer, inlineConf);
            fail("Should have thrown a NullPointerException");
        } catch (NullPointerException e) {
            // All good, moving on
        } catch (Exception e) {
            // Unexpected
            throw e;
        }

        try {
            Diff.diffAsInlineHtml("", "", null, inlineConf);
            fail("Should have thrown a NullPointerException");
        } catch (NullPointerException e) {
            // All good, moving on
        } catch (Exception e) {
            // Unexpected
            throw e;
        }

        try {
            Diff.diffAsInlineHtml("", "", writer, null);
            fail("Should have thrown a NullPointerException");
        } catch (NullPointerException e) {
            // All good, moving on
        } catch (Exception e) {
            // Unexpected
            throw e;
        }
    }

    private InlineHtmlDiffConfiguration getDefaultStyles() {
        DiffConfiguration conf = new DiffConfiguration(
            1,
            "com.alkacon.diff.messages",
            "DIFF_EQUAL_LINES_SKIPPED_1",
            Locale.ENGLISH);

        InlineHtmlDiffConfiguration inlineConf = new InlineHtmlDiffConfiguration(conf);
        inlineConf.setBlockStyles("unchangedBlock", "addedBlock", "removedBlock", "skippedBlock");
        inlineConf.setLineStyles("unchangedLine", "addedLine", "removedLine");
        return inlineConf;
    }

    /**
     * <p>Reads a file with the given name from the RFS and returns the file content.</p>
     * 
     * @param filename the file to read
     * @return the read file content
     * 
     * @throws IOException in case of file access errors
     */
    private byte[] readFile(String filename) throws IOException {
        // create input and output stream
        InputStream in = TestDiff.class.getClassLoader().getResourceAsStream(filename);
        if (in == null) {
            throw new FileNotFoundException(filename);
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        // read the file content
        int c;
        while ((c = in.read()) != -1) {
            out.write(c);
        }

        in.close();
        out.close();

        return out.toByteArray();
    }

    /**
     * <p>Reads a file from the RFS and converts it to a String with the specified encoding.</p>
     * 
     * @param filename the file to read
     * @param encoding the encoding to use when converting the file content to a String
     * @return the read file convered to a String
     * @throws IOException in case of file access errors
     */
    private String readFile(String filename, String encoding) throws IOException {
        return new String(readFile(filename), encoding);
    }
}
