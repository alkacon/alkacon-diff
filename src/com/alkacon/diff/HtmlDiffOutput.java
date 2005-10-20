/*
 * File   : $Source: /alkacon/cvs/AlkaconDiff/src/com/alkacon/diff/HtmlDiffOutput.java,v $
 * Date   : $Date: 2005/10/20 07:32:38 $
 * Version: $Revision: 1.1 $
 *
 * This library is part of OpenCms -
 * the Open Source Content Mananagement System
 *
 * Copyright (c) 2005 Alkacon Software GmbH (http://www.alkacon.com)
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
 * For further information about OpenCms, please see the
 * project website: http://www.opencms.org
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.alkacon.diff;

import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Outputs the diff result as HTML elements to a SAX ContentHandler.<p>
 */
public class HtmlDiffOutput implements I_DiffOutput {

    /** The XML content handler for the output. */
    private ContentHandler m_handler;

    /** The type of the current line. */
    private DiffLineType m_currentLineType;

    /**
     * Creates a new HTML Diff output based on the given XML content handler.<p>
     * 
     * @param handler the content handler to use
     */
    public HtmlDiffOutput(ContentHandler handler) {

        this.m_handler = handler;
    }

    /**
     * @see com.alkacon.diff.I_DiffOutput#addChangedText(java.lang.String)
     */
    public void addChangedText(String text) throws Exception {

        AttributesImpl attrs = new AttributesImpl();
        attrs.addAttribute("", "class", "class", "CDATA", "diff-" + m_currentLineType.toString());
        m_handler.startElement("", "span", "span", attrs);
        m_handler.characters(text.toCharArray(), 0, text.length());
        m_handler.endElement("", "span", "span");
    }

    /**
     * @see com.alkacon.diff.I_DiffOutput#addUnchangedText(java.lang.String)
     */
    public void addUnchangedText(String text) throws Exception {

        m_handler.characters(text.toCharArray(), 0, text.length());
    }

    /**
     * @see com.alkacon.diff.I_DiffOutput#endLine()
     */
    public void endLine() throws Exception {

        m_handler.endElement("", "div", "div");
    }

    /**
     * @see com.alkacon.diff.I_DiffOutput#skippedLines(int)
     */
    public void skippedLines(int linesSkipped) throws Exception {

        AttributesImpl attrs = new AttributesImpl();
        attrs.addAttribute("", "class", "class", "CDATA", "diff-skipped");
        m_handler.startElement("", "div", "div", attrs);
        String message = "(" + linesSkipped + " empty lines skipped)";
        m_handler.characters(message.toCharArray(), 0, message.length());
        m_handler.endElement("", "div", "div");
    }

    /**
     * @see com.alkacon.diff.I_DiffOutput#startLine(com.alkacon.diff.DiffLineType)
     */
    public void startLine(DiffLineType type) throws Exception {

        this.m_currentLineType = type;
        AttributesImpl attrs = new AttributesImpl();
        attrs.addAttribute("", "class", "class", "CDATA", "diff-" + type.toString());
        m_handler.startElement("", "div", "div", attrs);
    }
}