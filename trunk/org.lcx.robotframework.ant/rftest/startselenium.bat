@echo off
:: start selenium
set selenium-server=.\lib\Lib\SeleniumLibrary\lib\selenium-server.jar
set sel_opts=-trustAllSSLCertificates
start java -jar %selenium-server% %sel_opts% %* 

