package com.pdfpdiff.conversion.impl

/**
 * Created by mattcorey on 9/10/14.
 */
class BlowyUpyInputStream extends InputStream {
    private boolean onAvailable = true
    private boolean onRead = true

    BlowyUpyInputStream() {

    }

    BlowyUpyInputStream(boolean onAvailable, boolean onRead) {
        this.onAvailable = onAvailable
        this.onRead = onRead
    }

    public int available() throws IOException {
        if(onAvailable) {
            bang()
        }

        return 1
    }
    public int read() throws IOException {
        if(onRead) {
            bang()
        }

        return 1
    }

    private def bang() {
        throw new IOException("This is the blowy upy part.  BANG!")
    }
}
