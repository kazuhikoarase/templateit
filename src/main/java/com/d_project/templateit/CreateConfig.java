package com.d_project.templateit;

import java.io.File;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * CreateConfig
 * @author kazuhiko arase
 */
public class CreateConfig {

  public static void main(String[] args)
  throws Exception {

    final String configProps = args[0];
    final String configXml = args[1];
    final String projectDir = args[2];
    final String templatePackageName = args[3];
    final String templateFile = args[4];

    // load config
    final Document config = DocumentBuilderFactory.newInstance().
      newDocumentBuilder().parse(new File(configXml) );
    final String textExts = ( (Element)config.
        getElementsByTagName("text-files").
        item(0) ).
        getTextContent().replaceAll("\\s+", "\u0020");

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
