/*
 * Copyright 2010 L. Carbonnaux
 */
package org.lcx.robotframework.ant;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	private static final String BUNDLE_NAME = "robotant"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private Messages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	
	public static boolean getBoolean(String key) {
		try {
			return Boolean.parseBoolean(RESOURCE_BUNDLE.getString(key));
		} catch (MissingResourceException e) {
			return false;
		}
	}
	
}
