package org.lcx.robotframework.ant;

import java.io.File;

import junit.framework.TestCase;

public class RobotAntTest extends TestCase {
 
	public String path="";

	@Override
	protected void setUp() throws Exception {
		path = new File("").getAbsolutePath()+"\\";
	}

	public void testDefault() {
		RobotAnt rf = new RobotAnt();
		// case nothing is given
		rf.checkJVMArguments();
		
		// jar is null
		assertNull(rf.getCommandLine().getJar());
		// class is default
		assertEquals(RobotAnt.RFCLASS, rf.getCommandLine().getClassname());
		// command classpath contain default jar
		assertNotNull(rf.getCommandLine().getClasspath());
		String expected = path+"libext\\"+RobotAnt.RFJAR;
		System.out.println(rf.getCommandLine().getClasspath());
		assertEquals(expected, rf.getCommandLine().getClasspath().toString());
	}
		
	public void testClasspath() {
		RobotAnt rf = new RobotAnt();
		rf.createClasspath().createPath().setPath("libext/robotframework-2.5.5.jar");
		// case classpath is given
		rf.checkJVMArguments();
		
		// jar is null
		assertNull(rf.getCommandLine().getJar());
		// class is default
		assertEquals(RobotAnt.RFCLASS, rf.getCommandLine().getClassname());
		// command classpath contain default jar
		assertNotNull(rf.getCommandLine().getClasspath());
		String expected = path+"libext\\robotframework-2.5.5.jar";
		System.out.println(rf.getCommandLine().getClasspath());
		assertEquals(expected, rf.getCommandLine().getClasspath().toString());
	}

}
