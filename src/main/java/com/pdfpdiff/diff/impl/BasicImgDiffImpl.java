package com.pdfpdiff.diff.impl;

import com.pdfpdiff.diff.ImgDiff;

import java.awt.image.BufferedImage;

/**
 * Created by mcorey on 9/4/14.
 */
public class BasicImgDiffImpl implements ImgDiff {
    @Override
    public boolean doImagesMatch(BufferedImage img1, BufferedImage img2) {
        if (img1.getWidth() == img2.getWidth() && img1.getHeight() == img2.getHeight()) {
            for (int x = 0; x < img1.getWidth(); x++) {
                for (int y = 0; y < img1.getHeight(); y++) {
                    if (img1.getRGB(x, y) != img2.getRGB(x, y))
                        return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public BufferedImage createImageDiff(BufferedImage img1, BufferedImage img2) {
        if (img1.getWidth() != img2.getWidth() ||
                img1.getHeight() != img2.getHeight()) {
            System.out.println("Images are different sizes, cannot produce visual diff");

            return null;
        }

        BufferedImage img = new BufferedImage(img1.getWidth(),
                img1.getHeight(),
                img1.getType());

        for (int y = 0; y < img1.getHeight(); ++y) {
            for (int x = 0; x < img1.getWidth(); ++x) {
                int pixelA = img1.getRGB(x, y);
                int pixelB = img2.getRGB(x, y);
//                int pixelMod = pixelA ^ pixelB;
                int pixelMod = 0xFF00000;
                if(pixelA == pixelB)
                    pixelMod = convertToGrayscale(pixelA);

                img.setRGB(x, y, pixelMod);
            }
        }

        return img;
    }

    private int convertToGrayscale(int rgb) {
        //Based on the following weighed algorithm:  0.299r + 0.587g + 0.114b
        int red = (rgb >> 16) & 0x000000FF;
        int green = (rgb >>8 ) & 0x000000FF;
        int blue = (rgb) & 0x000000FF;

        int target = (int)((red * 0.299) + (green * 0.587) + (blue * 0.114));


        return (0x00 << 32) + (target << 16) + (target << 8) + (target);
    }
}
