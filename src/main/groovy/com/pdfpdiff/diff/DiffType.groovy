package com.pdfpdiff.diff

import com.pdfpdiff.diff.impl.GreyScaleDiffGeneratorStrategy

/**
 * Created by mattcorey on 9/11/14.
 */
enum DiffType {
    RED_OVER_GREY_SCALE (GreyScaleDiffGeneratorStrategy)

    private final Class strategy

    DiffType(Class strategy) {
        this.strategy = strategy
    }
}
