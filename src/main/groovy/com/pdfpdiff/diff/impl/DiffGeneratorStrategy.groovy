package com.pdfpdiff.diff.impl

import java.awt.image.BufferedImage

/**
 * Created by mattcorey on 9/11/14.
 */
interface DiffGeneratorStrategy {
    BufferedImage produceDiff(BufferedImage img1, BufferedImage img2)
}
