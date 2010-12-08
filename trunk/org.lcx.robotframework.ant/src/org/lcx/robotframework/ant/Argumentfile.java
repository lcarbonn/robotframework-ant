package org.lcx.robotframework.ant;


public class Argumentfile {

	String file = null;
	
	public Argumentfile() {
		
	}

	/**
	 * Set the file attribute of the argument file option
     * @param s file attribute
	 */
	public void setFile(String s) {
		this.file = s;
	}

	/**
	 * @return the argumentfile file attribute
	 */
	public String getFile() {
		return this.file;
	}

	
}
