@echo off
rd /s /q %CATALINA_HOME%\webapps\ROOT\
copy .\target\springproject.war %CATALINA_HOME%\webapps\ROOT.war /a
%CATALINA_HOME%/bin/catalina.bat run
