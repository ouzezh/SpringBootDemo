#!/usr/bin/env sh
#export JAVA_HOME=${JAVA_HOME8}
#export PATH=$JAVA_HOME/bin:$PATH

jarName="SpringBootDemo-0.1.jar"

cd $(dirname $0)
DEPLOY_DIR=$(pwd)
LOG_DIR=$DEPLOY_DIR/logs
if [ ! -d $LOG_DIR ]; then
    mkdir $LOG_DIR
fi

JAVA_OPTS="$JAVA_OPTS -Xms5440m -Xmx5440m"
export JAVA_OPTS;

SPRING_BOOT_OPTS="--spring.profiles.active=prod"
STDOUT_FILE=$LOG_DIR/stdout.log
nohup java $JAVA_OPTS -jar "$DEPLOY_DIR/$jarName" $SPRING_BOOT_OPTS >> $STDOUT_FILE 2>&1 &

echo "OK!"
PIDS=$(ps -ef | grep java | grep "$DEPLOY_DIR" |grep -v grep | awk '{print $2}')
echo "PID: $PIDS"
echo "STDOUT: $STDOUT_FILE"
