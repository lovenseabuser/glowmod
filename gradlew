#!/bin/sh
APP_NAME="Gradle"
APP_BASE_NAME=`basename "$0"`
DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'
CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar

set -e
DIRNAME=`dirname "$0"`
APP_HOME=`cd "$DIRNAME" || exit; pwd -P`

exec gradle "$@"
