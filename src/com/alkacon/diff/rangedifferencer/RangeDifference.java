/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package com.alkacon.diff.rangedifferencer;

/**
 * Description of a change between two or three ranges of comparable entities.<p>
 * 
 * <code>RangeDifference</code> objects are the elements of a compare result returned from
 * the <code>RangeDifferencer</code> <code>find* </code> methods.
 * Clients use these objects as they are returned from the differencer.
 * This class is not intended to be instantiated or subclassed.<p>
 * 
 * Note: A range in the <code>RangeDifference</code> object is given as a start index
 * and length in terms of comparable entities. However, these entity indices and counts
 * are not necessarily character positions. For example, if an entity represents a line
 * in a document, the start index would be a line number and the count would be in lines.</p>
 * 
 * <b>Please note:</b> This class has been modified by Alkacon from the original Eclipse source!
 * The source code has been reformatted and checkstyle / findbugs warnings where removed (AK).<p>
 *
 * @see RangeDifferencer
 */
public class RangeDifference {

    /**
     * Three-way change constant indicating the same change in both right and left,
     * that is only the ancestor is different.
     */
    public static final int ANCESTOR = 4;
    
    /** Two-way change constant indicating two-way change (same as <code>RIGHT</code>). */
    public static final int CHANGE = 2;

    /** Three-way change constant indicating a change in both right and left. */
    public static final int CONFLICT = 1;
    
    /** Constant indicating an unknown change kind. */
    public static final int ERROR = 5;
    
    /** Three-way change constant indicating a change in left. */
    public static final int LEFT = 3;
    
    /** Two-way change constant indicating no change. */
    public static final int NOCHANGE = 0;

    /** Three-way change constant indicating a change in right. */
    public static final int RIGHT = 2;

    /** the kind of change: NOCHANGE, CHANGE, LEFT, RIGHT, ANCESTOR, CONFLICT, ERROR. */
    private int m_fKind;

    /** (undocumented variable). */
    int m_fLeftLength;
    
    /** (undocumented variable). */
    int m_fLeftStart;
    
    /** (undocumented variable). */
    int m_fRightLength;
    
    /** (undocumented variable). */
    int m_fRightStart;
    
    /** (undocumented variable). */
    int m_lAncestorLength;
    
    /** (undocumented variable). */
    int m_lAncestorStart;

    /**
     * Creates a new range difference with the given change kind.
     *
     * @param changeKind the kind of change
     */
    /* package */RangeDifference(int changeKind) {

        m_fKind = changeKind;
    }

    /**
     * Creates a new <code>RangeDifference</code> with the given change kind
     * and left and right ranges.
     *
     * @param kind the kind of change
     * @param rightStart start index of entity on right side
     * @param rightLength number of entities on right side
     * @param leftStart start index of entity on left side
     * @param leftLength number of entities on left side
     */
    /* package */RangeDifference(int kind, int rightStart, int rightLength, int leftStart, int leftLength) {

        m_fKind = kind;
        m_fRightStart = rightStart;
        m_fRightLength = rightLength;
        m_fLeftStart = leftStart;
        m_fLeftLength = leftLength;
    }

    /**
     * Creates a new <code>RangeDifference</code> with the given change kind
     * and left, right, and ancestor ranges.
     *
     * @param kind the kind of change
     * @param rightStart start index of entity on right side
     * @param rightLength number of entities on right side
     * @param leftStart start index of entity on left side
     * @param leftLength number of entities on left side
     * @param ancestorStart start index of entity on ancestor side
     * @param ancestorLength number of entities on ancestor side
     */
    /* package */RangeDifference(
        int kind,
        int rightStart,
        int rightLength,
        int leftStart,
        int leftLength,
        int ancestorStart,
        int ancestorLength) {

        this(kind, rightStart, rightLength, leftStart, leftLength);
        m_lAncestorStart = ancestorStart;
        m_lAncestorLength = ancestorLength;
    }

    /**
     * Returns the end index of the entity range on the ancestor side.
     *
     * @return the end index of the entity range on the ancestor side
     */
    public int ancestorEnd() {

        return m_lAncestorStart + m_lAncestorLength;
    }

    /**
     * Returns the number of entities on the ancestor side.
     *
     * @return the number of entities on the ancestor side
     */
    public int ancestorLength() {

        return m_lAncestorLength;
    }

    /**
     * Returns the start index of the entity range on the ancestor side.
     *
     * @return the start index of the entity range on the ancestor side
     */
    public int ancestorStart() {

        return m_lAncestorStart;
    }

    /**
     * Returns the kind of difference.
     *
     * @return the kind of difference, one of
     * <code>NOCHANGE</code>, <code>CHANGE</code>, <code>LEFT</code>, <code>RIGHT</code>,
     * <code>ANCESTOR</code>, <code>CONFLICT</code>, <code>ERROR</code>
     */
    public int kind() {

        return m_fKind;
    }

    /**
     * Returns the end index of the entity range on the left side.
     *
     * @return the end index of the entity range on the left side
     */
    public int leftEnd() {

        return m_fLeftStart + m_fLeftLength;
    }

    /**
     * Returns the number of entities on the left side.
     *
     * @return the number of entities on the left side
     */
    public int leftLength() {

        return m_fLeftLength;
    }

    /**
     * Returns the start index of the entity range on the left side.
     *
     * @return the start index of the entity range on the left side
     */
    public int leftStart() {

        return m_fLeftStart;
    }

    /**
     * Returns the maximum number of entities in the left, right, and ancestor sides of this range.
     *
     * @return the maximum number of entities in the left, right, and ancestor sides of this range
     */
    public int maxLength() {

        return Math.max(m_fRightLength, Math.max(m_fLeftLength, m_lAncestorLength));
    }

    /**
     * Returns the end index of the entity range on the right side.
     *
     * @return the end index of the entity range on the right side
     */
    public int rightEnd() {

        return m_fRightStart + m_fRightLength;
    }

    /**
     * Returns the number of entities on the right side.
     *
     * @return the number of entities on the right side
     */
    public int rightLength() {

        return m_fRightLength;
    }

    /**
     * Returns the start index of the entity range on the right side.
     *
     * @return the start index of the entity range on the right side
     */
    public int rightStart() {

        return m_fRightStart;
    }
}
