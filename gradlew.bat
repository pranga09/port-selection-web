@echo off
set DIR=%~dp0
set JAVA_EXEC=%JAVA_HOME%\bin\java.exe
if not exist "%JAVA_EXEC%" (
    echo JAVA not found. Please install JAVA or set JAVA_HOME.
    exit /b 1
)
"%JAVA_EXEC%" -classpath "%DIR%gradle\wrapper\gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain %*
