#!/bin/sh
##############################################################################
## Gradle start-up script for UN*X
##############################################################################

# Attempt to find java
if [ -n "$JAVA_HOME" ]; then
    JAVA_EXEC="$JAVA_HOME/bin/java"
else
    JAVA_EXEC="$(which java)"
fi

if [ ! -x "$JAVA_EXEC" ]; then
    echo "ERROR: JAVA not found. Please install JAVA or set JAVA_HOME." >&2
    exit 1
fi

DIR="$(cd "$(dirname "$0")" && pwd)"
exec "$JAVA_EXEC" -classpath "$DIR/gradle/wrapper/gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain "$@"
