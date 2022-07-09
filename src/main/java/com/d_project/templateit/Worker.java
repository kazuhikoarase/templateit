package com.d_project.templateit;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


/**
 * Worker
 * @author kazuhiko arase
 */
public class Worker {

  private final PrintStream logOut = System.out;

  private String templateFile;

  private String templatePackageName;

  private String packageName;

  private String outputDir;

  private String textFiles;

  private Pattern textFilePattern;

  private String replaceFrom;

  private String replaceTo;

  private String packageNameFrom;

  private String packageNameTo;

  public Worker() {
  }

  public void start(
    final String packageName,
    final String projectName,
    final Properties props
  ) throws Exception {

    this.outputDir = props.getProperty("o", projectName);
    this.templateFile = props.getProperty("template-file");
    this.templatePackageName = props.getProperty("template-package-name");
    this.packageName = packageName;
    this.textFiles = props.getProperty("text-file-extensions");
    this.replaceFrom = props.getProperty("template-project-name");
    this.replaceTo = projectName;

    trace();
    trace(String.format("templateit %s", getVersion() ) );
    trace();
    trace(String.format("output dir   : %s", this.outputDir) );
    trace(String.format("template package name : %s",
        this.templatePackageName) );
    trace(String.format("package name : %s", this.packageName) );
    trace();

    int unpackCount = execute();

    trace();
    trace(String.format("%d file(s) unpacked.", unpackCount) );
  }

  public int execute() throws Exception {

    setup();

    final ZipInputStream in = new ZipInputStream(
        getClass().getResourceAsStream("/" + templateFile) );
    try {
      int unpackCount = 0;
      ZipEntry entry;
      while ( (entry = in.getNextEntry() ) != null) {
        if (entry.isDirectory() ) {
          continue;
        }
        unpackFile(in, entry);
        unpackCount += 1;
      }
      return unpackCount;
    } finally {
      in.close();
    }
  }

  private void setup() {

    // packageName for replace
    packageNameFrom = templatePackageName.replaceAll("\\.", "(\\\\W{1})");
    packageNameTo = packageName.replaceAll("\\.", "\\$1");

    // parse text extensions
    textFilePattern = Util.buildTextFilePattern(textFiles);
  }

  private void unpackFile(final ZipInputStream zipIn, final ZipEntry entry)
  throws Exception {

    final File dstFile = new File(outputDir, convertText(entry.getName() ) );
    if (!dstFile.getParentFile().exists() ) {
      dstFile.getParentFile().mkdirs();
    }

    final boolean textFile = isTextFile(
        new File(entry.getName() ).getName() );
    final String message = String.format("unpacking[%s] %s to %s ...",
        textFile? "t" : "b",
        entry.getName(), dstFile.getPath() );
    trace(message);

    final OutputStream out = new BufferedOutputStream(
        new FileOutputStream(dstFile) );
    try {
      
      if (textFile) {
        outputText(zipIn, out);
      } else {
        outputBinary(zipIn, out);
      }

    } finally {
      out.close();
    }
  }

  private void outputText(InputStream srcIn, OutputStream dstOut)
  throws Exception {
    final LineInputStream in = new LineInputStream(
        new BufferedInputStream(srcIn) );
    String line;
    while ( (line = in.readLine() ) != null) {
      line = convertText(line);
      dstOut.write(line.getBytes("ISO-8859-1") );
    }
  }

  private void outputBinary(InputStream srcIn, OutputStream dstOut)
  throws Exception {
    final BufferedInputStream in = new BufferedInputStream(srcIn);
    final byte[] buffer = new byte[8192];
    int length;
    while ( (length = in.read(buffer) ) != -1) {
      dstOut.write(buffer, 0, length);
    }
  }

  private String convertText(final String s) {
    return s.replaceAll(packageNameFrom, packageNameTo).
      replaceAll(replaceFrom, replaceTo);
  }

  private void trace() {
    logOut.println();
  }

  private void trace(Object o) {
    logOut.println(o);
  }

  private boolean isTextFile(final String name) {
    return textFilePattern.matcher(name).matches();
  }

  private String getVersion() throws Exception {
    final BufferedReader in = new BufferedReader(new InputStreamReader(
        getClass().getResourceAsStream("/version"), "ISO-8859-1") );
    try {
      return in.readLine();
    } finally {
      in.close();
    }
  }
}
