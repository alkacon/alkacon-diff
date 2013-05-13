package com.alkacon.diff;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Default Inline HTML Diff operation configuration class.</p>
 * 
 * @author jroel
 */
public class InlineHtmlDiffConfiguration implements I_InlineHtmlDiffConfiguration {

    /**
     * <p>Contains the default configuration.</p>
     */
    private final I_DiffConfiguration baseConf;
    private Map<DiffLineType, String> blockStyles = new HashMap<DiffLineType, String>();
    private Map<DiffLineType, String> lineStyles = new HashMap<DiffLineType, String>();

    /**
     * <p>Creates a new configuration object.</p>
     * 
     * @param baseConf base configuration (cannot be null)
     * @throws NullPointerException in case baseConf is null
     */
    public InlineHtmlDiffConfiguration(I_DiffConfiguration baseConf) {
        if (null == baseConf) {
            throw new NullPointerException("baseConf cannot be null");
        }
        this.baseConf = baseConf;
    }

    /**
     * @see com.alkacon.diff.I_DiffConfiguration#getLinesBeforeSkip()
     */
    @Override
    public int getLinesBeforeSkip() {
        return baseConf.getLinesBeforeSkip();
    }

    /**
     * @see com.alkacon.diff.I_DiffConfiguration#getMessageEqualLinesSkipped(int)
     */
    @Override
    public String getMessageEqualLinesSkipped(int lines) {
        return baseConf.getMessageEqualLinesSkipped(lines);
    }

    /**
     * @see I_InlineHtmlDiffConfiguration#getLineStyles(DiffLineType)
     */
    @Override
    public String getLineStyles(DiffLineType type) {
        String result = lineStyles.get(type);
        if (null == result) {
            return "";
        }
        return result;
    }

    /**
     * @see I_InlineHtmlDiffConfiguration#getBlockStyles(DiffLineType)
     */
    @Override
    public String getBlockStyles(DiffLineType type) {
        String result = blockStyles.get(type);
        if (null == result) {
            return "";
        }
        return result;
    }

    /**
     * <p>Sets all span style names.</p>
     * 
     * @param unchanged style for unchanged text
     * @param added style for added text
     * @param removed style for removed text
     */
    public void setLineStyles(String unchanged, String added, String removed) {
        if (unchanged != null) {
            lineStyles.put(DiffLineType.UNCHANGED, unchanged);
        }
        lineStyles.put(DiffLineType.ADDED, added);
        lineStyles.put(DiffLineType.REMOVED, removed);
        lineStyles = Collections.unmodifiableMap(lineStyles);
    }

    /**
     * <p>Sets all div style names.</p>
     * 
     * @param unchanged style for unchanged lines
     * @param added style for added lines
     * @param removed style for removed lines
     * @param skipped style for skipped lines
     */
    public void setBlockStyles(String unchanged, String added, String removed, String skipped) {
        blockStyles.put(DiffLineType.UNCHANGED, unchanged);
        blockStyles.put(DiffLineType.ADDED, added);
        blockStyles.put(DiffLineType.REMOVED, removed);
        blockStyles.put(DiffLineType.SKIPPED, skipped);
        blockStyles = Collections.unmodifiableMap(blockStyles);
    }
}
