package com.d_project.templateit;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;


/**
 * Util
 * @author kazuhiko arase
 */
public class Util {

	private Util() {
	}

	public static boolean isValidPackageName(String packageName) {
		return packageName.matches("^\\w+(\\.\\w+)*$");
	}

	public static boolean isValidProjectName(String projectName) {
		return projectName.matches("^\\w[\\w\\-]*$");
	}

	public static String[] parseArguments(String[] args, Properties props) {

		List<String> plainArgs = new ArrayList<String>();

		for (String arg : args) {
			if (arg.startsWith("-") ) {
				// pop an option
				arg = arg.substring(1);
				int index = arg.indexOf("=");
				if (index != -1) {
					props.setProperty(
						arg.substring(0, index),
						arg.substring(index + 1) );
				} else {
					props.setProperty(arg, "");
				}
			} else {
				plainArgs.add(arg);
			}
		}
		
		return plainArgs.toArray(new String[plainArgs.size() ] );
	}
	
	public static Pattern buildTextFilePattern(String textFiles) {
		StringBuilder regex = new StringBuilder();
		regex.append("^(");
		String[] files = textFiles.split("\\s+");
		for (int fi = 0; fi < files.length; fi += 1) {
			if (fi > 0) {
				regex.append('|');
			}
			String file = files[fi];
			for (int ci = 0; ci < file.length(); ci += 1) {
				char c = file.charAt(ci);
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