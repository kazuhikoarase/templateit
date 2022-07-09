package com.d_project.templateit;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;


/**
 * Main
 * @author kazuhiko arase
 */
public class Main {

  public static void main(final String[] args) 
  throws Exception {

    final Properties props = loadConfig();

    // check argument count.
    if (args.length != 2) {
      System.err.println(Arrays.asList(args).toString() );
      System.err.println(String.format(
          "arguments: [-o=outputDir] packageName projectName") );
      return;
    }

    final String packageName = args[0];
    final String projectName = args[1];

    // check naming.
    if (!Util.isValidPackageName(packageName) ) {
      throw new IllegalArgumentException(
          "bad package name:" + packageName);
    }
    if (!Util.isValidProjectName(projectName) ) {
      throw new IllegalArgumentException(
          "bad project name:" + projectName);
    }

    // start
    final Worker worker = new Worker();
    worker.start(packageName, projectName, props);
  }

  private static Properties loadConfig() 
  throws IOException {
    final InputStream in =
        Main.class.getResourceAsStream("/config.properties");
    try {
      final Properties props = new Properties();
      props.load(in);
      return props;
    } finally {
      in.close();
    }
  }
}
