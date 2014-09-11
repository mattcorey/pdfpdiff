package com.pdfpdiff.diff

import java.awt.image.BufferedImage

/**
 * Created by mattcorey on 9/11/14.
 */
public interface ImgDiff {
    boolean doImagesMatch(BufferedImage img1, BufferedImage img2)
    BufferedImage createImageDiff(BufferedImage img1, BufferedImage img2)
}