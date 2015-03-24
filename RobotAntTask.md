

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
From v0.6 `RobotAnt` use the class org.robotframework.RobotFramework from the standelone jar launcher for robotframework (actual v2.5.6)

robotframework-2.5.6.jar should be placed in lib folder or if in a specific folder should be added into the classpath.

| **mode** | **comment** | **default** |
|:---------|:------------|:------------|
| fork | tests are run in another VM than Ant | X|
| unfork | tests are run inside ant VM |  |

## Unfork mode ##
This is the default execution mode since v0.6. Class mode is used in unfork mode.
```
  <robotant data_sources="Test.html"/>
```
This will do the same as :

`java -classpath lib/robotframework-2.5.6.jar org.robotframework.RobotFramework Test.html`

## Fork mode ##
It uses the -jar option in fork mode (external VM)
```
<robotant data_sources="Test.html"
  fork="true">
  <arg line="--help"/>
  <classpath>
   <pathelement location="lib/selenium-server.jar"/>
  </classpath>
</robotant>
```

This will do the same as :
`java -classpath lib/selenium-server.jar -jar robotframework-2.5.4.1.jar --help Test.html`

## Using a specific jar ##
As per java task, you can specify the jar `RobotAnt` task should use to replace default ones.
Use classpath/pathelement
```
<robotant data_sources="Test.html">
  <arg line="--help"/>
  <classpath>
   <pathelement location="path/myrobotjar.jar"/>
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
  data_sources="TestSelenium.html"
  loglevel="INFO"
  debugfile="jybot.log"
  outputdir="results"
  maxmemory="512m">
  <classpath>
    <pathelement location="libext/robotframework-2.5.5.jar"/>
    <pathelement location="lib/Lib"/>
  </classpath>
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