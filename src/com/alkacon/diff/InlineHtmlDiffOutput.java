package com.alkacon.diff;

import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.AttributesImpl;

/**
 * <p>Outputs the diff result as HTML elements to a SAX ContentHandler.</p>
 * 
 * <p>This is heavily based on HtmlDiffOutput, with the noted change that this class
 * does <strong>NOT</strong> generate CSS classes, but <strong>inline CSS styles</strong>.
 * 
 * @author jroel
 * 
 */
public class InlineHtmlDiffOutput implements I_DiffOutput {

    /** The XML content handler for the output. */
    private ContentHandler m_handler;

    /** The type of the current line. */
    private DiffLineType m_currentLineType;

    /** The configuration to use. */
    private I_InlineHtmlDiffConfiguration m_config;

    private static final String ATTRIBUTE_NAME = "style";

    /**
     * Creates a new HTML Diff output based on the given XML content handler.<p>
     * 
     * @param handler the content handler to use
     * @param config the configuration to use
     */
    public InlineHtmlDiffOutput(ContentHandler handler, I_InlineHtmlDiffConfiguration config) {

        m_handler = handler;
        m_config = config;
    }

    /**
     * @see com.alkacon.diff.I_DiffOutput#addChangedText(java.lang.String)
     */
    public void addChangedText(String text) throws Exception {

        AttributesImpl attrs = new AttributesImpl();
        // span.diff-[added|removed]
        attrs.addAttribute("", ATTRIBUTE_NAME, ATTRIBUTE_NAME, "CDATA", m_config.getBlockStyles(m_currentLineType));
        m_handler.startElement("", "span", "span", attrs);
        m_handler.characters(text.toCharArray(), 0, text.length());
        m_handler.endElement("", "span", "span");
    }

    /**
     * @see com.alkacon.diff.I_DiffOutput#addUnchangedText(java.lang.String)
     */
    public void addUnchangedText(String text) throws Exception {

        if (m_config.getBlockStyles(DiffLineType.UNCHANGED) != null) {
            AttributesImpl attrs = new AttributesImpl();
            // span.diff-[unchanged]
            attrs.addAttribute("", ATTRIBUTE_NAME, ATTRIBUTE_NAME, "CDATA",
                m_config.getBlockStyles(DiffLineType.UNCHANGED));
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
        attrs.addAttribute("", ATTRIBUTE_NAME, ATTRIBUTE_NAME, "CDATA", m_config.getLineStyles(DiffLineType.SKIPPED));
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
        attrs.addAttribute("", ATTRIBUTE_NAME, ATTRIBUTE_NAME, "CDATA", m_config.getLineStyles(type));
        m_handler.startElement("", "div", "div", attrs);
    }
}
