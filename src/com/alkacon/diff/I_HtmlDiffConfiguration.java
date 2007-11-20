/*
 * File   : $Source: /alkacon/cvs/AlkaconDiff/src/com/alkacon/diff/I_HtmlDiffConfiguration.java,v $
 * Date   : $Date: 2007/11/20 15:59:08 $
 * Version: $Revision: 1.3 $
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

/**
 * Configurates a Html Diff operation.<p>
 * 
 * Additionally provides styles names.
 */
public interface I_HtmlDiffConfiguration extends I_DiffConfiguration {

    /**
     * Returns the style name to format a whole line.<p>
     * 
     * @param type the line type, can be any {@link DiffLineType}
     * 
     * @return the style name for the given line type
     */
    String getDivStyleName(DiffLineType type);

    /**
     * Returns the style name to format a block in a line.<p>
     * 
     * @param type the line type, can not be {@link DiffLineType#SKIPPED}
     * 
     * @return the style name for the given line type
     */
    String getSpanStyleName(DiffLineType type);
}