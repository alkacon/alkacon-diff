/*
 * File   : $Source: /alkacon/cvs/AlkaconDiff/src/com/alkacon/diff/DiffLineType.java,v $
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
 * Provides the basic result types for the diff output.<p>
 */
public final class DiffLineType {

    /** Result type: Content added. */
    public static final DiffLineType ADDED = new DiffLineType("added");

    /** Result type: Content removed. */
    public static final DiffLineType REMOVED = new DiffLineType("removed");

    /** Result type: Content unchanged. */
    public static final DiffLineType UNCHANGED = new DiffLineType("unchanged");

    /** Result type: Lines skipped. */
    public static final DiffLineType SKIPPED = new DiffLineType("skipped");

    /** The String identifier for the name. */
    private String m_name;

    /**
     * Creates a new basic result type with the given name.<p>
     * 
     * @param name the name for the new result type
     */
    private DiffLineType(String name) {

        m_name = name;
    }

    /**
     * Returns the name of the basic result types for the diff output.<p>
     * 
     * @see java.lang.Object#toString()
     * 
     * @return the name of the basic result types for the diff output
     */
    public String toString() {

        return m_name;
    }
}