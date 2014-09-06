package com.pdfpdiff.diff;

import java.awt.image.BufferedImage;

/**
 * Created by mcorey on 9/4/14.
 */
public interface ImgDiff {
    public boolean doImagesMatch(BufferedImage img1, BufferedImage img2);
    public BufferedImage createImageDiff(BufferedImage img1, BufferedImage img2);
}
