package com.pdfpdiff.conversion.impl

/**
 * Created by mattcorey on 9/10/14.
 */
class BlowyUpyInputStream extends InputStream {
    private final boolean onAvailable = true
    private final boolean onRead = true

    BlowyUpyInputStream() {

    }

    BlowyUpyInputStream(boolean onAvailable, boolean onRead) {
        this.onAvailable = onAvailable
        this.onRead = onRead
    }

    int available() throws IOException {
        if (onAvailable) {
            bang()
        }

        1
    }
    int read() throws IOException {
        if (onRead) {
            bang()
        }

        1
    }

    private bang() {
        throw new IOException('This is the blowy upy part.  BANG!')
    }
}
