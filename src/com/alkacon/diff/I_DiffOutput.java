/*
 * File   : $Source: /alkacon/cvs/AlkaconDiff/src/com/alkacon/diff/I_DiffOutput.java,v $
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

/**
 * A DiffOutput instance must be provided to the {@link Diff} to handle
 * the result of the diff process.<p>
 */
public interface I_DiffOutput {

    /**
     * Adds some changed text to this output.<p>
     * 
     * @param text the changed text to add
     * 
     * @throws Exception in case something goes wrong
     */
    void addChangedText(String text) throws Exception;

    /**
     * Adds some unchanged text to this output.<p>
     * 
     * @param text the unchanged text to add
     * 
     * @throws Exception in case something goes wrong
     */
    void addUnchangedText(String text) throws Exception;

    /**
     * Ends the current output line.<p>
     * 
     * @throws Exception in case something goes wrong
     */
    void endLine() throws Exception;

    /**
     * Adds an indicator that some lines have been skipped.<p>
     * 
     * @param linesSkipped the number of skipped lines
     * 
     * @throws Exception in case something goes wrong
     */
    void skippedLines(int linesSkipped) throws Exception;

    /**
     * Starts a new line of the given type.<p>
     * 
     * @param type the type of line to add
     * 
     * @throws Exception in case something goes wrong
     */
    void startLine(DiffLineType type) throws Exception;
}