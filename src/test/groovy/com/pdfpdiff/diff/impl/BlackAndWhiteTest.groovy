package com.pdfpdiff.diff.impl

import com.pdfpdiff.diff.DiffType
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by mattcorey on 9/13/14.
 */
class BlackAndWhiteTest extends Specification {
    @Unroll
    def "Colors #color1 and #color2 are passed through the #generatorClass strategy, and should return #expectedColor" (
            DiffType generatorType, String color1, String color2, String expectedColor) {
        setup:
            def intColor1 = Integer.decode(color1)
            def intColor2 = Integer.decode(color2)
            def intExpected = Integer.decode(expectedColor)

        expect:
            generatorType.strategy.produceColor(intColor1, intColor2) == intExpected

        where:
            generatorType           | color1       | color2       | expectedColor
            DiffType.RED_OVER_WHITE | '0x00000000' | '0x00000000' | '0x00FFFFFF'
            DiffType.RED_OVER_WHITE | '0x00FFFFFF' | '0x00FFFFFF' | '0x00FFFFFF'
            DiffType.RED_OVER_WHITE | '0x00004E00' | '0x00004E00' | '0x00FFFFFF'
            DiffType.RED_OVER_WHITE | '0x007FBA80' | '0x007FBA80' | '0x00FFFFFF'
            DiffType.RED_OVER_WHITE | '0x000762B0' | '0x000762B0' | '0x00FFFFFF'
            DiffType.RED_OVER_WHITE | '0x00FFFFFF' | '0x00000000' | '0x00FF0000'
            DiffType.RED_OVER_WHITE | '0x00FFFFFF' | '0x00067500' | '0x00FF0000'
            DiffType.RED_OVER_WHITE | '0x00FFFFFF' | '0x00004E00' | '0x00FF0000'
            DiffType.RED_OVER_WHITE | '0x00FFFFFF' | '0x007FBA80' | '0x00FF0000'
            DiffType.RED_OVER_WHITE | '0x00FFFFFF' | '0x000762B0' | '0x00FF0000'

            DiffType.RED_OVER_BLACK | '0x00000000' | '0x00000000' | '0x00000000'
            DiffType.RED_OVER_BLACK | '0x00FFFFFF' | '0x00FFFFFF' | '0x00000000'
            DiffType.RED_OVER_BLACK | '0x00004E00' | '0x00004E00' | '0x00000000'
            DiffType.RED_OVER_BLACK | '0x007FBA80' | '0x007FBA80' | '0x00000000'
            DiffType.RED_OVER_BLACK | '0x000762B0' | '0x000762B0' | '0x00000000'
            DiffType.RED_OVER_BLACK | '0x00FFFFFF' | '0x00000000' | '0x00FF0000'
            DiffType.RED_OVER_BLACK | '0x00FFFFFF' | '0x00067500' | '0x00FF0000'
            DiffType.RED_OVER_BLACK | '0x00FFFFFF' | '0x00004E00' | '0x00FF0000'
            DiffType.RED_OVER_BLACK | '0x00FFFFFF' | '0x007FBA80' | '0x00FF0000'
            DiffType.RED_OVER_BLACK | '0x00FFFFFF' | '0x000762B0' | '0x00FF0000'

    }
}
