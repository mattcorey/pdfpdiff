package com.pdfpdiff.util

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

/**
 * Created by mattcorey on 9/13/14.
 */
class ImageLoadUtil {
    static BufferedImage loadImage(String name) {
        if (name == null) {
            return null
        }

        ImageIO.read(ImageLoadUtil.classLoader.getResourceAsStream(name))
    }
}
