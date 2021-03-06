/*
 * File   : $Source: /alkacon/cvs/AlkaconDiff/src/com/alkacon/diff/I_TextDiffConfiguration.java,v $
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
 * Configurates a Text Diff operation.<p>
 * 
 * Additionally provides a changed char marker.
 */
public interface I_TextDiffConfiguration extends I_DiffConfiguration {

    /**
     * Returns the marker to use to highlight changed chars.<p>
     * 
     * Use <code>' '</code> to do not hightlight changed chars 
     * 
     * @return the marker to use to highlight changed chars
     */
    char getChangedCharMarker();
}