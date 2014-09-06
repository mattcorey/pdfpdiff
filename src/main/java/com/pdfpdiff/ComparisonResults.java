package com.pdfpdiff;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcorey on 9/3/14.
 */
public class ComparisonResults {
    private boolean match;

    private List<BufferedImage> diffs = new ArrayList<BufferedImage>();

    public ComparisonResults(boolean match) {
        this.match = match;
    }

    public boolean pdfsMatch() {
        return match;
    }

    public List<BufferedImage> getDiffs() {
        return diffs;
    }
}
