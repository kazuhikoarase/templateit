package com.d_project.templateit;

import java.io.File;
import java.io.PrintWriter;


/**
 * CreateConfig
 * @author kazuhiko arase
 */
public class CreateConfig {

  public static void main(final String[] args)
  throws Exception {

    final String configProps = args[0];
    final String textFiles = args[1];
    final String projectDir = args[2];
    final String templateFile = args[3];
    final String templatePackageName = args[4];

    // check naming.
    if (!Util.isValidPackageName(templatePackageName) ) {
      throw new IllegalArgumentException(
          "bad template package name:" + templatePackageName);
    }

    final String textExts = textFiles.replaceAll("\\s+", "\u0020");

    final String projectName = new File(projectDir).getName();

    if (!Util.isValidProjectName(projectName) ) {
      throw new IllegalArgumentException(
          "bad project name:" + projectName);
    }

    // output config.properties
    final PrintWriter out = new PrintWriter(configProps, "UTF-8");
    try {

      out.print("text-file-extensions=");
      out.println(textExts);
      out.print("template-project-name=");
      out.println(projectName);
      out.print("template-file=");
      out.println(templateFile);
      out.print("template-package-name=");
      out.println(templatePackageName);

    } finally {
      out.close();
    }
  }
}
