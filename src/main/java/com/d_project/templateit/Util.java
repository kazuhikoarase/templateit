package com.d_project.templateit;

import java.util.regex.Pattern;


/**
 * Util
 * @author kazuhiko arase
 */
public class Util {

  private Util() {
  }

  public static boolean isValidPackageName(final String packageName) {
    return packageName.matches("^\\w+(\\.\\w+)*$");
  }

  public static boolean isValidProjectName(final String projectName) {
    return projectName.matches("^\\w[\\w\\-]*$");
  }

  public static String toPackageNamePattern(final String s) {
    final StringBuilder buf = new StringBuilder();
    boolean firstPeriod = false;
    for (int i = 0; i < s.length(); i += 1) {
      final char c = s.charAt(i);
      if (c == '.') {
        if (!firstPeriod) {
          buf.append("(\\W)");
          firstPeriod = true;
        } else {
          buf.append("\\1");
        }
      } else {
        buf.append(c);
      }
    }
    return buf.toString();
  }

  public static Pattern buildTextFilePattern(final String textFiles) {
    final StringBuilder regex = new StringBuilder();
    regex.append("^(");
    final String[] files = textFiles.split("\\s+");
    for (int i = 0; i < files.length; i += 1) {
      if (i > 0) {
        regex.append('|');
      }
      final String file = files[i];
      for (int j = 0; j < file.length(); j += 1) {
        final char c = file.charAt(j);
        if ('A' <= c && c < 'Z' || 'a' <= c && c < 'z' ||
            '0' <= c && c < '9') {
          regex.append(c);
        } else if (c == '*') {
          // any string
          regex.append(".*");
        } else if (c == '?') {
          // any character
          regex.append(".");
        } else {
          regex.append('\\');
          regex.append(c);
        }
      }
    }
    regex.append(")$");
    return Pattern.compile(regex.toString() );
  }
}
