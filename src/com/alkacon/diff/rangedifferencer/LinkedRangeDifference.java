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
 * <b>Please note:</b> This class has been modified by Alkacon from the original Eclipse source!
 * The source code has been reformatted and checkstyle / findbugs warnings where removed (AK).<p>
 */
class LinkedRangeDifference extends RangeDifference {

    /** Delete constant. */
    static final int DELETE = 1;
    
    /** Insert constant. */
    static final int INSERT = 0;

    /** (undocumented variable). */
    LinkedRangeDifference m_fNext;

    /**
     * Creates a LinkedRangeDifference an initializes it to the error state.<p>
     */
    LinkedRangeDifference() {

        super(ERROR);
        m_fNext = null;
    }

    /**
     * Constructs and links a LinkeRangeDifference to another LinkedRangeDifference.<p>
     * 
     * @param next (undocumented)
     * @param operation (undocumented)
     */
    LinkedRangeDifference(LinkedRangeDifference next, int operation) {

        super(operation);
        m_fNext = next;
    }

    /**
     * Follows the next link.<p>
     * 
     * @return the result (undocumented)
     */
    LinkedRangeDifference getNext() {

        return m_fNext;
    }

    /**
     * Returns <code>true</code> if the kind is DELETE.<p>
     * 
     * @return <code>true</code> if the kind is DELETE
     */
    boolean isDelete() {

        return kind() == DELETE;
    }

    /**
     * Returns <code>true</code> if the kind is INSERT.<p>
     * 
     * @return <code>true</code> if the kind is INSERT
     */
    boolean isInsert() {

        return kind() == INSERT;
    }

    /**
     * Sets the next link of this LinkedRangeDifference.<p>
     * 
     * @param next (undocumented)
     */
    void setNext(LinkedRangeDifference next) {

        m_fNext = next;
    }
}
