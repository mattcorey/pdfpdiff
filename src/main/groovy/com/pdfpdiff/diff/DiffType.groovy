package com.pdfpdiff.diff

import com.pdfpdiff.diff.impl.DiffGeneratorStrategy
import com.pdfpdiff.diff.impl.GreyScaleDiffGeneratorStrategy
import com.pdfpdiff.diff.impl.SingleColorOnMatchDiffGeneratorStrategy

/**
 * Created by mattcorey on 9/11/14.
 */
enum DiffType {
    RED_OVER_GREY_SCALE (new GreyScaleDiffGeneratorStrategy()),
    RED_OVER_WHITE (new SingleColorOnMatchDiffGeneratorStrategy(0x00FFFFFF)),
    RED_OVER_BLACK (new SingleColorOnMatchDiffGeneratorStrategy(0x00000000))

    private final DiffGeneratorStrategy strategy

    DiffType(DiffGeneratorStrategy strategy) {
        this.strategy = strategy
    }
}
