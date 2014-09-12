package com.pdfpdiff.diff

import java.awt.image.BufferedImage

/**
 * Created by mattcorey on 9/11/14.
 */
interface ImageComparer {
    boolean compareImages(BufferedImage img1, BufferedImage img2)
}
