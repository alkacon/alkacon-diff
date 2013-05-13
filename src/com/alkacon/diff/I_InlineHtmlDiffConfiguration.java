package com.alkacon.diff;

/**
 * <p>Configures an Inline HTML Diff operation.</p>
 * 
 * <p>This interface specifically adds methods to add the line and block styles that should be applied.</p>
 * 
 * @author jroel
 */
public interface I_InlineHtmlDiffConfiguration extends I_DiffConfiguration {
    /**
     * <p>Returns the inline styles to format a whole line.</p>
     * 
     * @param type the line type, can be any {@link DiffLineType}
     * @return the inline styles for the given line type
     */
    String getLineStyles(DiffLineType type);

    /**
     * <p>Returns the inline styles to format a block in a line.</p>
     * 
     * @param type the line type, can not be {@link DiffLineType#SKIPPED}
     * @return the inline styles for the given line type
     */
    String getBlockStyles(DiffLineType type);
}