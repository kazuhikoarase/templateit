package com.d_project.templateit;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * Main
 * @author kazuhiko arase
 */
public class Main {

  public static void main(String[] args) 
  throws Exception {

    Properties props = loadConfig();
    args = Util.parseArguments(args, props);

    // check argument count.
    if (args.length != 2) {
      System.err.println(String.format(
          "arguments: [-o outputDir] packageName projectName",
          Main.class.getName() ) );
      return;
    }

    String packageName = args[0];
    String projectName = args[1];

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
    Worker worker = new Worker();
    worker.start(packageName, projectName, props);
  }

  private static Properties loadConfig() 
  throws IOException {
    InputStream in = Main.class.getResourceAsStream("/config.properties");
    try {
      Properties props = new Properties();
      props.load(in);
      return props;
    } finally {
      in.close();
    }
  }
}
