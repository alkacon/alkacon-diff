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

import java.util.ArrayList;
import java.util.List;

/**
 * A custom iterator to iterate over a List of <code>RangeDifferences</code>.
 * It is used internally by the <code>RangeDifferencer</code>.
 * 
 * <b>Please note:</b> This class has been modified by Alkacon from the original Eclipse source!
 * The source code has been reformatted and checkstyle / findbugs warnings where removed (AK).<p>
 */
class DifferencesIterator {

    /** (undocumented variable). */
    RangeDifference[] m_fArray;

    /** (undocumented variable). */
    RangeDifference m_fDifference;

    /** (undocumented variable). */
    int m_fIndex;

    /** (undocumented variable). */
    List m_fRange;

    /**
     * Creates a differences iterator on an array of <code>RangeDifference</code>s.
     * 
     * @param differenceRanges (undocumented) 
     */
    DifferencesIterator(RangeDifference[] differenceRanges) {

        m_fArray = differenceRanges;
        m_fIndex = 0;
        m_fRange = new ArrayList();
        if (m_fIndex < m_fArray.length) {
            m_fDifference = m_fArray[m_fIndex++];
        } else {
            m_fDifference = null;
        }
    }

    /**
     * Returns the number of RangeDifferences.<p>
     * 
     * @return the number of RangeDifferences
     */
    int getCount() {

        return m_fRange.size();
    }

    /**
     * Appends the edit to its list and moves to the next <code>RangeDifference</code>.
     */
    void next() {

        m_fRange.add(m_fDifference);
        if (m_fDifference != null) {
            if (m_fIndex < m_fArray.length) {
                m_fDifference = m_fArray[m_fIndex++];
            } else {
                m_fDifference = null;
            }
        }
    }

    /**
     * Difference iterators are used in pairs.<p>
     * 
     * This method returns the other iterator.<p>
     * 
     * @param right (undocumented) 
     * @param left (undocumented) 
     * 
     * @return the other iterator
     */
    DifferencesIterator other(DifferencesIterator right, DifferencesIterator left) {

        if (this == right) {
            return left;
        }
        return right;
    }

    /**
     * Removes all <code>RangeDifference</code>s.<p>
     */
    void removeAll() {

        m_fRange.clear();
    }
}