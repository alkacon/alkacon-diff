/*
 * File   : $Source: /alkacon/cvs/AlkaconDiff/src/com/alkacon/diff/HtmlDiffConfiguration.java,v $
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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Default Html Diff operation configuration class.<p>
 */
public class HtmlDiffConfiguration implements I_HtmlDiffConfiguration {

    private final I_DiffConfiguration m_baseConf;
    private Map m_divStyles = new HashMap();
    private Map m_spanStyles = new HashMap();

    /**
     * Creates a new configuration object.<p> 
     * 
     * @param baseConf base configuration
     */
    public HtmlDiffConfiguration(I_DiffConfiguration baseConf) {

        m_baseConf = baseConf;
    }

    /**
     * @see com.alkacon.diff.I_HtmlDiffConfiguration#getDivStyleName(com.alkacon.diff.DiffLineType)
     */
    public String getDivStyleName(DiffLineType type) {

        return m_divStyles.get(type).toString();
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

    /**
     * @see com.alkacon.diff.I_HtmlDiffConfiguration#getSpanStyleName(com.alkacon.diff.DiffLineType)
     */
    public String getSpanStyleName(DiffLineType type) {

        if (type != DiffLineType.UNCHANGED) {
            // throw NPE if missing
            return m_spanStyles.get(type).toString();
        } else {
            return (String)m_spanStyles.get(type);
        }
    }

    /**
     * Sets all div style names.<p>
     * 
     * @param unchanged style for unchanged lines
     * @param added style for added lines
     * @param removed style for removed lines
     * @param skipped style for skipped lines
     */
    public void setDivStyleNames(String unchanged, String added, String removed, String skipped) {

        m_divStyles.put(DiffLineType.UNCHANGED, unchanged);
        m_divStyles.put(DiffLineType.ADDED, added);
        m_divStyles.put(DiffLineType.REMOVED, removed);
        m_divStyles.put(DiffLineType.SKIPPED, skipped);
        m_divStyles = Collections.unmodifiableMap(m_divStyles);
    }

    /**
     * Sets all span style names.<p>
     * 
     * @param unchanged style for unchanged text
     * @param added style for added text
     * @param removed style for removed text
     */
    public void setSpanStyleNames(String unchanged, String added, String removed) {

        if (unchanged != null) {
            m_spanStyles.put(DiffLineType.UNCHANGED, unchanged);
        }
        m_spanStyles.put(DiffLineType.ADDED, added);
        m_spanStyles.put(DiffLineType.REMOVED, removed);
        m_spanStyles = Collections.unmodifiableMap(m_spanStyles);
    }
}