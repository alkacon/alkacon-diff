/*
 * File   : $Source: /alkacon/cvs/AlkaconDiff/src/com/alkacon/diff/HtmlDiffOutput.java,v $
 * Date   : $Date: 2007/11/20 15:59:08 $
 * Version: $Revision: 1.4 $
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

    /** The configuration to use. */
    private I_HtmlDiffConfiguration m_config;

    /**
     * Creates a new HTML Diff output based on the given XML content handler.<p>
     * 
     * @param handler the content handler to use
     * @param config the configuration to use
     */
    public HtmlDiffOutput(ContentHandler handler, I_HtmlDiffConfiguration config) {

        m_handler = handler;
        m_config = config;
    }

    /**
     * @see com.alkacon.diff.I_DiffOutput#addChangedText(java.lang.String)
     */
    public void addChangedText(String text) throws Exception {

        AttributesImpl attrs = new AttributesImpl();
        // span.diff-[added|removed]
        attrs.addAttribute("", "class", "class", "CDATA", m_config.getSpanStyleName(m_currentLineType));
        m_handler.startElement("", "span", "span", attrs);
        m_handler.characters(text.toCharArray(), 0, text.length());
        m_handler.endElement("", "span", "span");
    }

    /**
     * @see com.alkacon.diff.I_DiffOutput#addUnchangedText(java.lang.String)
     */
    public void addUnchangedText(String text) throws Exception {

        if (m_config.getSpanStyleName(DiffLineType.UNCHANGED) != null) {
            AttributesImpl attrs = new AttributesImpl();
            // span.diff-[unchanged]
            attrs.addAttribute("", "class", "class", "CDATA", m_config.getSpanStyleName(DiffLineType.UNCHANGED));
            m_handler.startElement("", "span", "span", attrs);
            if ((text == null) || (text.length() == 0)) {
                text = " ";
            }
            m_handler.characters(text.toCharArray(), 0, text.length());
            m_handler.endElement("", "span", "span");
        } else {
            m_handler.characters(text.toCharArray(), 0, text.length());
        }
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
        // div.diff-skipped
        attrs.addAttribute("", "class", "class", "CDATA", m_config.getDivStyleName(DiffLineType.SKIPPED));
        String nbsp = " ";
        m_handler.startElement("", "div", "div", attrs);
        m_handler.characters(nbsp.toCharArray(), 0, nbsp.length());
        m_handler.endElement("", "div", "div");
        m_handler.startElement("", "div", "div", attrs);
        String message = m_config.getMessageEqualLinesSkipped(linesSkipped);
        m_handler.characters(message.toCharArray(), 0, message.length());
        m_handler.endElement("", "div", "div");
        m_handler.startElement("", "div", "div", attrs);
        m_handler.characters(nbsp.toCharArray(), 0, nbsp.length());
        m_handler.endElement("", "div", "div");
    }

    /**
     * @see com.alkacon.diff.I_DiffOutput#startLine(com.alkacon.diff.DiffLineType)
     */
    public void startLine(DiffLineType type) throws Exception {

        m_currentLineType = type;
        AttributesImpl attrs = new AttributesImpl();
        // div.diff-[unchanged|added|removed]
        attrs.addAttribute("", "class", "class", "CDATA", m_config.getDivStyleName(type));
        m_handler.startElement("", "div", "div", attrs);
    }
}