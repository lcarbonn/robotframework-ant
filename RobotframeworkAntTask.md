

# Introduction #
`RobotAnt` is a [ant](http://ant.apache.org/) task for using robotframework inside ant build scripts.

`RobotAnt` ant task inherits from the core [java](http://ant.apache.org/manual/Tasks/jar.html) task.

`RobotAnt` uses the same parameters and propose specific ones

[Download `RobotAnt` jar](http://code.google.com/p/robotframework-ant/downloads/list)

# Task Definition #
Ant task definition for `RobotAnt`:

```
<taskdef name="robotant"
   classname="org.lcx.robotframework.ant.RobotAnt"
   classpath="ant-robotframework-<version>.jar"/>

```

# Fork or unfork modes #
`RobotAnt` use the jar standelone launcher for robotframework (actual v2.5.4.1)

robotframework-2.5.4.1.jar must be at root folder of the ant basedir.

| **mode** | **comment** | **default** |
|:---------|:------------|:------------|
| fork | tests are run in another VM than Ant | X|
| unfork | tests are run inside ant VM |  |

## Fork mode ##
By default it uses the -jar option in fork mode (external VM)
```
<robotant data_sources="Test.html">
  <arg line="--help"/>
  <classpath>
   <pathelement location="lib/selenium-server.jar"/>
  </classpath>
</robotant>
```

This will do the same as :
`java -classpath lib/selenium-server.jar -jar robotframework-2.5.4.1.jar --help Test.html`

## Unfork mode ##
If fork is set to false, then class mode will be used
```
<robotant data_sources="Test.html" fork="false">
  <arg line="--help"/>
  <classpath>
   <pathelement location="lib/selenium-server.jar"/>
  </classpath>
</robotant>
```
This will do the same as :

`java -classpath lib/selenium-server.jar:robotframework-2.5.4.1.jar org.robotframework.RobotFramework --help Test.html`

## Using a specific jar ##
As per java task, you can specify the jar `RobotAnt` task should use to replace default ones.
Use "jar" task attribute
```
<robotant data_sources="Test.html" jar="path/myrobotjar.jar">
  <arg line="--help"/>
  <classpath>
   <pathelement location="lib/selenium-server.jar"/>
  </classpath>
</robotant>
```

also you could change in robotant.properties file inside `RobotAnt` jar file

# Task Options #
`RobotAnt` propose some Robotframework [options](http://robotframework.googlecode.com/svn/tags/robotframework-2.5.4/doc/userguide/RobotFrameworkUserGuide.html#configuring-execution) to be set as task attributes:

| **Attribute** | **Description** | **Required** | **available in version** |
|:--------------|:----------------|:-------------|:-------------------------|
| data\_sources | to use as data\_sources | No | 0.1 |
| name | set the --name option | No | 0.1 |
| suite | set the --suite option | No | 0.1 |
| test | set the --test option | No | 0.1 |
| variablefile | set the --variablefile option | No | 0.1 |
| outputdir | set the --outputdir option | No | 0.1 |
| loglevel | set the --loglevel option | No | 0.1 |
| debugfile | set the --debugfile option | No | 0.1 |

and inner elements:

| **Element** | **Attribute** | **Description** | **Required** | **available in version** |
|:------------|:--------------|:----------------|:-------------|:-------------------------|
| data\_source | file | add a data\_source to data\_sources | No | 0.2 |
| argumentfile | file | add a --argumentfile option | No | 0.2 |


Complete sample:
```
<robotant 
    jar="robotframework-selenium-2.5.4.1.jar"
    data_sources="TestSelenium.html"
    name="TestExecution Result"
    loglevel="TRACE"
    debugfile="jybot.log"
    outputdir="resultats"
    suite="TestSelenium"
    test="'Test search on google'"
    variablefile="path/vars.py"
    >
  <argumentfile file="first.txt"/>
  <data_source file="AnotherTest.html/>
  <classpath>
   <pathelement location="lib/selenium-server.jar"/>
  </classpath>
  <arg line="--variable str:Hello"/>
</robotant>

```

Other Robotframework options can be set using **arg with line** attribute:
```
   <arg line="--help"/>
   <arg line="--variable str:Hello"/>
```

## Fail on error ##
By default `RobotAnt`, like `java` task, doesn't stop on execution error.
If you want your build to stop in case of a test error, set `failonerror="true"`
See [Ant note](http://ant.apache.org/manual/Tasks/java.html#failonerror)