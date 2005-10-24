
package com.alkacon.diff;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import junit.framework.TestCase;

/** 
 * Test case for <code>{@link com.alkacon.diff.Diff}</code>.<p>
 * 
 * @author Alexander Kandzior 
 * 
 * @version $Revision: 1.2 $
 * 
 * @since 6.2.0
 */
public class TestCmsDiff extends TestCase {

    /**
     * Default JUnit constructor.<p>
     * 
     * @param arg0 JUnit parameters
     */
    public TestCmsDiff(String arg0) {

        super(arg0);
    }

    /**
     * Reads a file with the given name from the RFS and returns the file content.<p> 
     * 
     * @param filename the file to read 
     * @return the read file content
     * 
     * @throws IOException in case of file access errors
     */
    public static byte[] readFile(String filename) throws IOException {

        // create input and output stream
        InputStream in = TestCmsDiff.class.getClassLoader().getResourceAsStream(filename);
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
     * Reads a file from the RFS and converts it to a String with the specified encoding.<p> 
     * 
     * @param filename the file to read 
     * @param encoding the encoding to use when converting the file content to a String
     * @return the read file convered to a String
     * @throws IOException in case of file access errors
     */
    public static String readFile(String filename, String encoding) throws IOException {

        return new String(readFile(filename), encoding);
    }

    /**
     * Simple test for the diff function.<p>
     * 
     * @throws Exception if the test fails
     */
    public void testDiff1() throws Exception {

        String in1, in2;
        in1 = readFile("com/alkacon/diff/testHtml_01a.html", "ISO-8859-1");
        in2 = readFile("com/alkacon/diff/testHtml_01b.html", "ISO-8859-1");

        String result1 = Diff.diffAsHtml(in1, in2);
        System.out.println(result1);

        String result2 = Diff.diffAsText(in1, in2);
        System.out.println(result2);
    }
    
    /**
     * Simple test for the diff function with indentation output.<p>
     * 
     * @throws Exception if the test fails
     */
    public void testDiffWithIndent() throws Exception {
        
        String in1, in2;
        in1 = readFile("com/alkacon/diff/testHtml_01a.html", "ISO-8859-1");
        in2 = readFile("com/alkacon/diff/testHtml_01b.html", "ISO-8859-1");
        
        XmlSaxWriter saxWriter = new XmlSaxWriter(new StringWriter());
        saxWriter.setIndentXml(true);
        I_DiffOutput output = new HtmlDiffOutput(saxWriter);
                
        Diff.diff(in1, in2, output, -1);
        
        String result1 = saxWriter.getWriter().toString();
        System.out.println(result1);
    }
}