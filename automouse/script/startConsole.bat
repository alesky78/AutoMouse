

REM This script start the jiraProjectGenerator application
REM created by Alessandro D'Ottavio
REM 20/04/2015


REM Configure the console
@echo off
COLOR 04
MODE 80,20
TITLE automa


REM Start the application
java -classpath "automouse.jar;lib/*;config" it.spaghettisource.automouse.Application console
