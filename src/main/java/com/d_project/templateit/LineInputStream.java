package com.d_project.templateit;

import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * LineInputStream
 * @author kazuhiko arase
 */
public class LineInputStream extends FilterInputStream {

  private static final int LF = 0x0a;

  public LineInputStream(final InputStream in) {
    super(in);
  }

  public String readLine() throws IOException {

    final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

    try {
      int b;
      while ( (b = in.read() ) != -1) {
        buffer.write(b);
        if (b == LF) {
          break;
        }
      }
    } finally {
      buffer.close();
    }

    if (buffer.size() == 0) {
      return null;
    }

    return new String(buffer.toByteArray(), "ISO-8859-1");
  }
}
