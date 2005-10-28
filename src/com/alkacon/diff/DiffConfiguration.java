/*
 * File   : $Source: /alkacon/cvs/AlkaconDiff/src/com/alkacon/diff/DiffConfiguration.java,v $
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

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Default Diff operation configuration class.<p>
 */
public class DiffConfiguration implements I_DiffConfiguration {

    private final int m_lines;
    private final Locale m_locale;
    private final String m_messageKey;
    private final ResourceBundle m_resourceBundle;

    /**
     * Creates a new configuration object.<p> 
     * 
     * @param lines the lines to show before skipping
     * @param messageBundle a resource bundle name to get the localized messages from
     * @param messageKey the key for a message like: '({0} equal skipped lines)'
     * @param locale the locale to use
     */
    protected DiffConfiguration(int lines, String messageBundle, String messageKey, Locale locale) {

        m_lines = lines;
        m_locale = locale;
        m_messageKey = messageKey;
        m_resourceBundle = ResourceBundle.getBundle(messageBundle, m_locale);
    }

    /**
     * @see com.alkacon.diff.I_DiffConfiguration#getLinesBeforeSkip()
     */
    public int getLinesBeforeSkip() {

        return m_lines;
    }

    /**
     * @see com.alkacon.diff.I_DiffConfiguration#getMessageEqualLinesSkipped(int)
     */
    public String getMessageEqualLinesSkipped(int lines) {

        String message = m_resourceBundle.getString(m_messageKey);
        Object[] params = new Object[] {new Integer(lines)};
        return new MessageFormat(message, m_locale).format(params);
    }
}