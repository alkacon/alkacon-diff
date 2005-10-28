/*
 * File   : $Source: /alkacon/cvs/AlkaconDiff/src/com/alkacon/diff/TextDiffConfiguration.java,v $
 * Date   : $Date: 2005/10/28 08:55:38 $
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


/**
 * Default Text Diff operation configuration class.<p>
 */
public class TextDiffConfiguration implements I_TextDiffConfiguration {

    private final I_DiffConfiguration m_baseConf;
    private final char m_marker;

    /**
     * Creates a new configuration object.<p> 
     * 
     * @param baseConf base configuration
     * @param marker the marker to highlight changed chars
     */
    public TextDiffConfiguration(I_DiffConfiguration baseConf, char marker) {

        m_baseConf = baseConf;
        m_marker = marker;
    }

    /**
     * @see com.alkacon.diff.I_TextDiffConfiguration#getChangedCharMarker()
     */
    public char getChangedCharMarker() {

        return m_marker;
    }

    /**
     * @see com.alkacon.diff.I_DiffConfiguration#getLinesBeforeSkip()
     */
    public int getLinesBeforeSkip() {

        return m_baseConf.getLinesBeforeSkip();
    }

    /**
     * @see com.alkacon.diff.I_DiffConfiguration#getMessageEqualLinesSkipped(int)
     */
    public String getMessageEqualLinesSkipped(int lines) {

        return m_baseConf.getMessageEqualLinesSkipped(lines);
    }

}