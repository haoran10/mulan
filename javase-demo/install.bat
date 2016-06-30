@echo off

call mvn clean install -Dmaven.test.skip
@pause