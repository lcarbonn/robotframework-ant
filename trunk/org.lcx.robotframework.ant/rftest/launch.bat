@echo off
:: start selenium
call startselenium.bat

:: launch the test
set JAVA_OPTS=-Xmx512m
set ROBOTFRAMEWORK=.\lib\robotframework-2.5.6.jar
set RF_OPTS=--debugfile jybot.log --loglevel TRACE --outputdir results
java %JAVA_OPTS%  -jar %ROBOTFRAMEWORK% %RF_OPTS% %*
