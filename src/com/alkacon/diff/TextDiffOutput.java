/*
 * File   : $Source: /alkacon/cvs/AlkaconDiff/src/com/alkacon/diff/TextDiffOutput.java,v $
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

import java.io.Writer;

/**
 * Text based implementation for a Diff output.<p>
 * 
 * The generated output will be plain text.<p>
 */
public class TextDiffOutput implements I_DiffOutput {

    /** The current content line. */
    private final StringBuffer m_contentLine = new StringBuffer();

    /** Indicates if the current line has changed text. */
    private boolean m_currentLineHasChangedText = false;

    /** The current marked line. */
    private final StringBuffer m_markLine = new StringBuffer();

    /** Indicates if lines should be marked. */
    private boolean m_markLines;

    /** The writer to write the output to. */
    private Writer m_writer;

    /** The message to show when skipping lines. */
    private String m_lineMessage;

    /**
     * Creates a new text based diff output.<p>
     * 
     * @param writer the writer to write the result to
     * @param markLines if <code>true</code>, changes in lines will be marked with a <code>'^'</code> char 
     */
    public TextDiffOutput(Writer writer, boolean markLines) {

        this(writer, markLines, "equal lines skipped");
    }

    /**
     * Creates a new text based diff output.<p>
     * 
     * @param writer the writer to write the result to
     * @param markLines if <code>true</code>, changes in lines will be marked with a <code>'^'</code> char
     * @param lineMessage a (loclized) String to output when skipping some lines
     */
    public TextDiffOutput(Writer writer, boolean markLines, String lineMessage) {

        m_writer = writer;
        m_markLines = markLines;
        m_lineMessage = lineMessage;
    }

    /**
     * @see com.alkacon.diff.I_DiffOutput#addChangedText(java.lang.String)
     */
    public void addChangedText(String text) throws Exception {

        if (m_markLines) {
            if (!m_currentLineHasChangedText) {
                m_currentLineHasChangedText = true;
                for (int i = 0; i < m_contentLine.length() - 4; i++) {
                    // minus 4 for the margin width
                    m_markLine.append(' ');
                }
            }
            for (int i = 0; i < text.length(); i++) {
                m_markLine.append('^');
            }
        }
        m_contentLine.append(text);
    }

    /**
     * @see com.alkacon.diff.I_DiffOutput#addUnchangedText(java.lang.String)
     */
    public void addUnchangedText(String text) throws Exception {

        if (m_markLines) {
            if (m_currentLineHasChangedText) {
                for (int i = 0; i < text.length(); i++) {
                    m_markLine.append(' ');
                }
            }
        }
        m_contentLine.append(text);
    }

    /**
     * @see com.alkacon.diff.I_DiffOutput#endLine()
     */
    public void endLine() throws Exception {

        m_writer.write(m_contentLine.toString());
        m_writer.write('\n');
        if (m_markLines && m_currentLineHasChangedText) {
            if (m_markLine.length() > 0) {
                m_writer.write("    ");
                m_writer.write(m_markLine.toString());
                m_writer.write('\n');
            }
        }
    }

    /**
     * @see com.alkacon.diff.I_DiffOutput#skippedLines(int)
     */
    public void skippedLines(int linesSkipped) throws Exception {

        m_writer.write("(" + linesSkipped + ' ' + m_lineMessage + ")\n");
    }

    /**
     * @see com.alkacon.diff.I_DiffOutput#startLine(com.alkacon.diff.DiffLineType)
     */
    public void startLine(DiffLineType type) throws Exception {

        m_contentLine.setLength(0);
        if (type == DiffLineType.UNCHANGED) {
            m_contentLine.append("    ");
        } else if (type == DiffLineType.ADDED) {
            m_contentLine.append("+++ ");
        } else if (type == DiffLineType.REMOVED) {
            m_contentLine.append("--- ");
        }
        m_currentLineHasChangedText = false;
        if (m_markLines) {
            m_markLine.setLength(0);
        }
    }
}