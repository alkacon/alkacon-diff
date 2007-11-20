/*
 * File   : $Source: /alkacon/cvs/AlkaconDiff/test/com/alkacon/diff/TestDiff.java,v $
 * Date   : $Date: 2007/11/20 15:59:08 $
 * Version: $Revision: 1.1 $
 *
 * Copyright (c) 2007 Alkacon Software GmbH (http://www.alkacon.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * For further information about Alkacon Software GmbH, please see the
 * company website: http://www.alkacon.com
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.alkacon.diff;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Locale;

import junit.framework.TestCase;

/** 
 * Test case for <code>{@link com.alkacon.diff.Diff}</code>.<p>
 * 
 * @author Alexander Kandzior 
 * 
 * @version $Revision: 1.1 $
 * 
 * @since 6.2.0
 */
public class TestDiff extends TestCase {

    /**
     * Default JUnit constructor.<p>
     * 
     * @param arg0 JUnit parameters
     */
    public TestDiff(String arg0) {

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

    private I_HtmlDiffConfiguration getHtmlDiffConfiguration() {

        DiffConfiguration conf = new DiffConfiguration(
            1,
            "com.alkacon.diff.messages",
            "DIFF_EQUAL_LINES_SKIPPED_1",
            Locale.ENGLISH);
        HtmlDiffConfiguration htmlConf = new HtmlDiffConfiguration(conf);
        htmlConf.setDivStyleNames("unchanged", "added", "removed", "skipped");
        htmlConf.setSpanStyleNames(null, "added", "removed");
        return htmlConf;
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

        I_HtmlDiffConfiguration conf = getHtmlDiffConfiguration();
        String result1 = Diff.diffAsHtml(in1, in2, conf);
        System.out.println(result1);

        I_TextDiffConfiguration textConf = new TextDiffConfiguration(conf, '^');
        String result2 = Diff.diffAsText(in1, in2, textConf);
        System.out.println(result2);
    }

    /**
     * Simple test for the diff function.<p>
     * 
     * @throws Exception if the test fails
     */
    public void testDiff2() throws Exception {

        String in1, in2;
        in1 = "";
        in2 = "nonempty string";

        I_HtmlDiffConfiguration conf = getHtmlDiffConfiguration();
        String result1 = Diff.diffAsHtml(in1, in2, conf);
        // difference should not be empty
        assertFalse("".equals(result1));
        System.out.println(result1);

        I_TextDiffConfiguration textConf = new TextDiffConfiguration(conf, '^');
        String result2 = Diff.diffAsText(in1, in2, textConf);
        assertFalse("".equals(result2));
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
        I_HtmlDiffConfiguration conf = getHtmlDiffConfiguration();
        I_DiffOutput output = new HtmlDiffOutput(saxWriter, conf);

        Diff.diff(in1, in2, output, conf);

        String result1 = saxWriter.getWriter().toString();
        System.out.println(result1);
    }
}