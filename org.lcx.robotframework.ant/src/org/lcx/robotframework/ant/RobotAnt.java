/*
 * Copyright 2011 L. Carbonnaux
 */
package org.lcx.robotframework.ant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Java;
import org.apache.tools.ant.types.Path;

public class RobotAnt extends Java {
	
	public final static String RFJAR	= Messages.getString("rfjar"); //$NON-NLS-1$
	public final static String RFCLASS	= Messages.getString("rfclass"); //$NON-NLS-1$
	public final static String QUOTE	= Messages.getString("separator"); //$NON-NLS-1$
	
	private String data_sources			= null;
	private String name					= null;
	private String suite				= null;
	private String test					= null;
	private String outputdir			= null;
	private String loglevel				= null;
	private String debugfile			= null;
	private String variablefile			= null;

	private String maxpermsize			= null;

	private List<Argumentfile> argumentfiles	= new ArrayList<Argumentfile>();
	private List<Data_Source> data_sources_list	= new ArrayList<Data_Source>();
	
	public RobotAnt() {
		super();
	}

	public RobotAnt(Task owner) {
		super(owner);
	}
	
    /**
     * Do the execution.
     * @throws BuildException if failOnError is set to true and the application
     * returns a nonzero result code.
     */
	@Override
	public void execute() throws BuildException {
		this.checkJVMArguments();
		this.createCommandLine();
		super.checkConfiguration();
		super.execute();
	}
	
	/**
	 * Set the JVM parameters from the given ant arguments
	 */
	protected void checkJVMArguments() throws BuildException {

		// Reject if classname attribute received
        if (getCommandLine().getClassname() != null) {
            throw new BuildException("Cannot set 'classname' attribute in robotant task.");
        }

//		// Reject if jar attribute received and fork is false
//        if (!localfork && getCommandLine().getJar() != null) {
//            throw new BuildException("Cannot execute a jar in non-forked mode."
//                                     + " Please set fork='true'. ");
//        }

//        // if jar received, force fork
//        if(this.getCommandLine().getJar()!=null) {
//			this.setLocalFork(true);
//		}

        // if nothing received,
        // set classname to default
        // search jar in classpath or folders
		if(this.getCommandLine().getJar()==null &&
				this.getCommandLine().getClassname()==null) {
			this.setClassname(RFCLASS);
			// search if class is in classpath
			try {
				Class.forName(RFCLASS);
			} catch (ClassNotFoundException e) {
				// if not in classpath and classpath not received
				// , search for jar in default folders
				// to add in classpath
//				if(this.getCommandLine().getClasspath()==null) {
					Path p = new Path(this.getProject(), getRFJar());
					this.createClasspath().append(p);
//				}
//				this.setJar(new File(getRFJar()));
			}
		}
	
	}

	/**
	 * Set the RF parameters from the given ant arguments
	 */
	protected void createCommandLine() throws BuildException {
		if(getName()!=null && getName().trim().length() >0 ){
			this.createLineQuotedArg("name", getName());
		}

		if(getVariablefile()!=null && getVariablefile().trim().length() >0 ){
			this.createLineArg("variablefile", getVariablefile());
		}

		if(getLoglevel()!=null && getLoglevel().trim().length() >0 ){
			this.createLineArg("loglevel", getLoglevel());
		}

		if(getDebugfile()!=null && getDebugfile().trim().length() >0) {
			this.createLineArg("debugfile", getDebugfile());
		}

		if(getOutputdir()!=null && getOutputdir().trim().length() >0) {
			this.createLineArg("outputdir", getOutputdir());
		}

		if(getSuite()!=null && getSuite().trim().length() >0) {
			this.createLineQuotedArg("suite", getSuite());
		}

		if(getTest()!=null && getTest().trim().length() >0) {
			this.createLineQuotedArg("test", getTest());
		}

		if(getArgumentfiles().length>0) {
			for(String s : getArgumentfiles()) {
				this.getCommandLine().createArgument().setLine(s);
			}
		}
		
		if(getMaxpermsize()!=null && getMaxpermsize().length()>0) {
			this.getCommandLine().createVmArgument().setValue(Messages.getString("MaxPermSize")+"="+getMaxpermsize());
		}

		// always at the end for data_sources
		if(getData_Sources()!=null && getData_Sources().length()>0) {
			this.getCommandLine().createArgument().setLine(getData_Sources());
		}
		if(getData_Sources_List().length>0) {
			for(String s : getData_Sources_List()) {
				this.getCommandLine().createArgument().setLine(QUOTE + s + QUOTE);
			}
		}

	}
	
	/**
	 * Create an argument in the command line for given key and value
	 * @param key
	 * @param value
	 */
	protected void createLineArg(String key, String value) {
		this.getCommandLine().createArgument().setLine(Messages.getString(key)+" "+value);
	}

	/**
	 * Create an argument in the command line for given key and "value"
	 * @param key
	 * @param value
	 */
	protected void createLineQuotedArg(String key, String value) {
		this.getCommandLine().createArgument().setLine(Messages.getString(key)+" "+ QUOTE +value + QUOTE);
		//this.createLineArg(Messages.getString(key), QUOTE +value + QUOTE);
	}

	/**
     * Set the data sources to use.
     *
     * @param the data sources to use.
     *
     */
	public void setData_sources(String s) {
		this.data_sources = s;
	}
	
	public String getData_Sources() {
		return this.data_sources;
	}

	/**
	 * Accessor to the suite parameter
	 * @return the suite
	 */
	public String getSuite() {
		return suite;
	}

	/**
	 * Set the test suites to run by name
	 * @param suite the suite to set
	 */
	public void setSuite(String suite) {
		this.suite = suite;
	}

	/**
	 * Accesor to test parameter
	 * @return the test
	 */
	public String getTest() {
		return test;
	}

	/**
	 * Set test cases to run by name
	 * @param test the test to set
	 */
	public void setTest(String test) {
		this.test = test;
	}

	/**
	 * Accessor to the outputdir parameter
	 * @return the outputdir
	 */
	public String getOutputdir() {
		return outputdir;
	}

	/**
	 * Set where to create output files
	 * @param outputdir the outputdir to set
	 */
	public void setOutputdir(String outputdir) {
		this.outputdir = outputdir;
	}

	/**
	 * Accessor to the loglevel parameter
	 * @return the loglevel
	 */
	public String getLoglevel() {
		return loglevel;
	}

	/**
	 * Set the threshold level for logging. Available levels:
	 * 		TRACE, DEBUG, INFO (default), WARN, NONE (no logging)
	 * @param loglevel the loglevel to set
	 */
	public void setLoglevel(String loglevel) {
		this.loglevel = loglevel;
	}

	/**
	 * Accessor to the debugfile parameter
	 * @return the debugfile
	 */
	public String getDebugfile() {
		return debugfile;
	}

	/**
	 * Set the debug file written during execution
	 * @param debugfile the debugfile to set
	 */
	public void setDebugfile(String debugfile) {
		this.debugfile = debugfile;
	}

	/**
	 * Accessor to the name parameter
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the top level test suite
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Accessor to the variablefile parameter
	 * @return the variablefile
	 */
	public String getVariablefile() {
		return variablefile;
	}

	/**
	 * Set the file to read variables from (e.g. 'path/vars.py').
	 * @param variablefile the variablefile to set
	 */
	public void setVariablefile(String variablefile) {
		this.variablefile = variablefile;
	}

	/**
	 * Create a argument file to the argument file list
	 */
	public Argumentfile createArgumentfile() {
		Argumentfile af = new Argumentfile();
		argumentfiles.add(af);
		return af;
	}

	/**
	 * @return the argumentfiles
	 */
	public String[] getArgumentfiles() {
		String[] args = new String[argumentfiles.size()];
		int i = 0;
		for(Argumentfile argf : argumentfiles) {
			args[i] = Messages.getString("argumentfile")+" "+argf.getFile();
			i++;
		}
		return args;
	}

	/**
	 * Create a data source file to the data source list
	 */
	public Data_Source createData_source() {
		Data_Source af = new Data_Source();
		data_sources_list.add(af);
		return af;
	}

	/**
	 * @return the data_sources
	 */
	public String[] getData_Sources_List() {
		String[] args = new String[data_sources_list.size()];
		int i = 0;
		for(Data_Source argf : data_sources_list) {
			args[i] = argf.getFile();
			i++;
		}
		return args;
	}

	public void setMaxpermsize(String s) {
		this.maxpermsize = s;
	}

	/**
	 * @return the maxpermsize
	 */
	public String getMaxpermsize() {
		return maxpermsize;
	}

	private String getRFJar() {
		if(new File(RFJAR).canRead()) {
			return RFJAR;
		} else if (new File("lib/"+RFJAR).canRead()) {
			return ("lib/"+RFJAR);
		} else if (new File("libext/"+RFJAR).canRead()) {
			return ("libext/"+RFJAR);
		}  else {
            throw new BuildException("Cannot find "+RFJAR+" jar in classpath, rfjarpath, ./, ./lib, ./libext folders");
		}
	}

}
