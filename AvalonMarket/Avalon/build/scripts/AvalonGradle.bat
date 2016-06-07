@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  AvalonGradle startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

@rem Add default JVM options here. You can also use JAVA_OPTS and AVALON_GRADLE_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windowz variants

if not "%OS%" == "Windows_NT" goto win9xME_args
if "%@eval[2+2]" == "4" goto 4NT_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*
goto execute

:4NT_args
@rem Get arguments from the 4NT Shell from JP Software
set CMD_LINE_ARGS=%$

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\avalon-1.0.0-Extension.jar;%APP_HOME%\lib\tools.jar;%APP_HOME%\lib\jodd-core-3.7.jar;%APP_HOME%\lib\jodd-props-3.7.jar;%APP_HOME%\lib\protobuf-java-2.4.1.jar;%APP_HOME%\lib\guava-19.0.jar;%APP_HOME%\lib\javassist-3.20.0-GA.jar;%APP_HOME%\lib\netty-all-5.0.0.Alpha2.jar;%APP_HOME%\lib\logback-classic-1.1.7.jar;%APP_HOME%\lib\logback-core-1.1.7.jar;%APP_HOME%\lib\logback-access-1.1.7.jar;%APP_HOME%\lib\akka-actor_2.11-2.4.4.jar;%APP_HOME%\lib\akka-agent_2.11-2.4.4.jar;%APP_HOME%\lib\akka-camel_2.11-2.4.4.jar;%APP_HOME%\lib\akka-cluster_2.11-2.4.4.jar;%APP_HOME%\lib\akka-cluster-metrics_2.11-2.4.4.jar;%APP_HOME%\lib\akka-cluster-sharding_2.11-2.4.4.jar;%APP_HOME%\lib\akka-cluster-tools_2.11-2.4.4.jar;%APP_HOME%\lib\akka-contrib_2.11-2.4.4.jar;%APP_HOME%\lib\akka-http-core_2.11-2.4.4.jar;%APP_HOME%\lib\akka-http-testkit_2.11-2.4.4.jar;%APP_HOME%\lib\akka-multi-node-testkit_2.11-2.4.4.jar;%APP_HOME%\lib\akka-osgi_2.11-2.4.4.jar;%APP_HOME%\lib\akka-persistence_2.11-2.4.4.jar;%APP_HOME%\lib\akka-persistence-tck_2.11-2.4.4.jar;%APP_HOME%\lib\akka-remote_2.11-2.4.4.jar;%APP_HOME%\lib\akka-slf4j_2.11-2.4.4.jar;%APP_HOME%\lib\akka-stream_2.11-2.4.4.jar;%APP_HOME%\lib\akka-stream-testkit_2.11-2.4.4.jar;%APP_HOME%\lib\akka-testkit_2.11-2.4.4.jar;%APP_HOME%\lib\akka-distributed-data-experimental_2.11-2.4.4.jar;%APP_HOME%\lib\akka-typed-experimental_2.11-2.4.4.jar;%APP_HOME%\lib\akka-http-experimental_2.11-2.4.4.jar;%APP_HOME%\lib\akka-http-jackson-experimental_2.11-2.4.4.jar;%APP_HOME%\lib\akka-http-spray-json-experimental_2.11-2.4.4.jar;%APP_HOME%\lib\akka-http-xml-experimental_2.11-2.4.4.jar;%APP_HOME%\lib\akka-persistence-query-experimental_2.11-2.4.4.jar;%APP_HOME%\lib\config-1.3.0.jar;%APP_HOME%\lib\scala-java8-compat_2.11-0.7.0.jar;%APP_HOME%\lib\scala-stm_2.11-0.7.jar;%APP_HOME%\lib\camel-core-2.13.4.jar;%APP_HOME%\lib\akka-parsing_2.11-2.4.4.jar;%APP_HOME%\lib\org.osgi.core-4.3.1.jar;%APP_HOME%\lib\org.osgi.compendium-4.3.1.jar;%APP_HOME%\lib\akka-protobuf_2.11-2.4.4.jar;%APP_HOME%\lib\scalatest_2.11-2.2.4.jar;%APP_HOME%\lib\junit-4.12.jar;%APP_HOME%\lib\netty-3.10.3.Final.jar;%APP_HOME%\lib\uncommons-maths-1.2.2a.jar;%APP_HOME%\lib\ssl-config-akka_2.11-0.2.1.jar;%APP_HOME%\lib\reactive-streams-1.0.0.jar;%APP_HOME%\lib\jackson-databind-2.7.2.jar;%APP_HOME%\lib\spray-json_2.11-1.3.2.jar;%APP_HOME%\lib\scala-xml_2.11-1.0.5.jar;%APP_HOME%\lib\jaxb-impl-2.2.6.jar;%APP_HOME%\lib\scala-reflect-2.11.2.jar;%APP_HOME%\lib\hamcrest-core-1.3.jar;%APP_HOME%\lib\ssl-config-core_2.11-0.2.1.jar;%APP_HOME%\lib\jackson-annotations-2.7.0.jar;%APP_HOME%\lib\jackson-core-2.7.2.jar;%APP_HOME%\lib\scala-parser-combinators_2.11-1.0.4.jar;%APP_HOME%\lib\slf4j-api-1.7.20.jar;%APP_HOME%\lib\scala-library-2.11.8.jar

@rem Execute AvalonGradle
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %AVALON_GRADLE_OPTS%  -classpath "%CLASSPATH%" com.avalon.core.AvalonEngine %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable AVALON_GRADLE_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%AVALON_GRADLE_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
