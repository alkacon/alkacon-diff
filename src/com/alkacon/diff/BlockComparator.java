/*
 * File   : $Source: /alkacon/cvs/AlkaconDiff/src/com/alkacon/diff/BlockComparator.java,v $
 * Date   : $Date: 2006/11/27 09:32:25 $
 * Version: $Revision: 1.2 $
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

import com.alkacon.diff.rangedifferencer.I_RangeComparator;

import java.util.ArrayList;
import java.util.List;


/**
 * A Comparator for diffing corresponding changes resulting from a line-based diff.<p>
 * 
 * For example, if a line-based diff says "these lines were replaced by those lines",
 * then this comparator can be used to compare the content in those lines.<p>
 *
 * This comparator has also a little bit of special treatment for HTML/XML tags,
 * that is it tries to treat tags as single entities to be compared.
 */
class BlockComparator implements I_RangeComparator {

    private List m_tokens;

    /**
     * @param text should contain the lines of text concatenated with a "\n" in between them
     */
    public BlockComparator(StringBuffer text) {

        this.m_tokens = splitLineTokens(text);
    }

    /**
     * @see com.alkacon.diff.rangedifferencer.I_RangeComparator#getRangeCount()
     */
    public int getRangeCount() {

        return m_tokens.size();
    }

    /**
     * @see com.alkacon.diff.rangedifferencer.I_RangeComparator#rangesEqual(int, com.alkacon.diff.rangedifferencer.I_RangeComparator, int)
     */
    public boolean rangesEqual(int thisIndex, I_RangeComparator other, int otherIndex) {

        String thisToken = getToken(thisIndex);
        String otherToken = ((BlockComparator)other).getToken(otherIndex);

        // treating newlines and spaces the same gives a good effect
        if ((thisToken.equals(" ") && otherToken.equals("\n")) || (thisToken.equals("\n") && otherToken.equals(" "))) {
            return true;
        }

        return thisToken.equals(otherToken);
    }

    /**
     * @see com.alkacon.diff.rangedifferencer.I_RangeComparator#skipRangeComparison(int, int, com.alkacon.diff.rangedifferencer.I_RangeComparator)
     */
    public boolean skipRangeComparison(int length, int maxLength, I_RangeComparator other) {

        return false;
    }

    /**
     * Returns all the tokens stored in this comparator from the given position.<p> 
     * 
     * @param startToken the position to start
     * 
     * @return all the tokens stored in this comparator from the given position
     */
    public String substring(int startToken) {

        return substring(startToken, m_tokens.size());
    }

    /**
     * Returns all the tokens stored in this comparator in the given range.<p> 
     * 
     * @param startToken the position to start
     * @param endToken the position to end 
     * 
     * @return all the tokens stored in this comparator in the given range
     */
    public String substring(int startToken, int endToken) {

        if (startToken == endToken) {
            return (String)m_tokens.get(startToken);
        } else {
            StringBuffer result = new StringBuffer();
            for (int i = startToken; i < endToken; i++) {
                result.append((String)m_tokens.get(i));
            }
            return result.toString();
        }
    }

    /**
     * Returns the substring from the given position as an array of strings, each array entry corresponding to one line.<p>
     * 
     * The newlines themselves are also ntries in the array.<p>
     * 
     * @param startToken the position to start
     * 
     * @return the substring from the given position as an array of strings, each array entry corresponding to one line
     */
    public String[] substringSplitted(int startToken) {

        return substringSplitted(startToken, m_tokens.size());
    }

    /**
     * Returns the substring as an array of strings, each array entry corresponding to one line.<p>
     * 
     * The newlines themselves are also ntries in the array.<p>
     * 
     * @param startToken the position to start
     * @param endToken the position to end 
     * 
     * @return the substring as an array of strings, each array entry corresponding to one line
     */
    public String[] substringSplitted(int startToken, int endToken) {

        if (startToken == endToken) {
            return new String[] {(String)m_tokens.get(startToken)};
        } else {
            int resultPos = -1;
            String[] result = null;
            StringBuffer resultBuffer = new StringBuffer();
            for (int i = startToken; i < endToken; i++) {
                String token = (String)m_tokens.get(i);
                if (token.equals("\n")) {
                    if (resultBuffer.length() > 0) {
                        result = grow(result, 2);
                        result[++resultPos] = resultBuffer.toString();
                        result[++resultPos] = "\n";
                        resultBuffer.setLength(0);
                    } else {
                        result = grow(result, 1);
                        result[++resultPos] = "\n";
                    }
                } else {
                    resultBuffer.append(token);
                }
            }
            if (resultBuffer.length() > 0) {
                result = grow(result, 1);
                result[++resultPos] = resultBuffer.toString();
            } else if (result == null) {
                result = new String[0];
            }
            return result;
        }
    }

    private String getToken(int i) {

        if (i < m_tokens.size()) {
            return (String)m_tokens.get(i);
        }
        return "";
    }

    private String[] grow(String[] strings, int count) {

        if (strings == null) {
            return new String[count];
        } else {
            String[] result = new String[strings.length + count];
            System.arraycopy(strings, 0, result, 0, strings.length);
            return result;
        }
    }

    private ArrayList splitLineTokens(StringBuffer text) {

        ArrayList tokens = new ArrayList(100);
        StringBuffer currentWord = new StringBuffer(100);

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            switch (c) {
                case '<': // begin of a HTML/XML tag: let it stick to the next word
                    if (currentWord.length() > 0) {
                        tokens.add(currentWord.toString());
                        currentWord.setLength(0);
                    }
                    currentWord.append(c);
                    break;
                case '/':
                    // special handling for (possible) closing HTML/XML tag
                    if (currentWord.length() == 1 && currentWord.charAt(0) == '<') {
                        currentWord.append(c);
                        break;
                    }
                // else: no break so that code below gets executed
                case '>':
                    if (currentWord.length() > 2 && currentWord.charAt(0) == '<' && currentWord.charAt(1) == '/') {
                        currentWord.append(c);
                        break;
                    }
                case '.':
                case '!':
                case ',':
                case ';':
                case '?':
                case ' ':
                case '=':
                case '\'':
                case '"':
                case '\t':
                case '\r':
                case '\n':
                    if (currentWord.length() > 0) {
                        tokens.add(currentWord.toString());
                        currentWord.setLength(0);
                    }
                    tokens.add(String.valueOf(c));
                    break;
                default:
                    currentWord.append(c);
            }
        }

        if (currentWord.length() > 0) {
            tokens.add(currentWord.toString());
        }
        return tokens;
    }
}