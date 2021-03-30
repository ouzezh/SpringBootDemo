#!/usr/bin/env sh
#export JAVA_HOME=${JAVA_HOME8}
#export PATH=$JAVA_HOME/bin:$PATH

jarName="SpringBootDemo-0.1.jar"

cd `dirname $0`
DEPLOY_DIR=`pwd`

ps -ef | grep java | grep "$DEPLOY_DIR/${jarName}" | grep -v grep
PIDS=`ps -ef | grep java | grep "$DEPLOY_DIR/${jarName}" | grep -v grep |awk '{print $2}'`
if [ -z "$PIDS" ]; then
    echo "ERROR: The server does not started!"
    exit 1
fi

echo -e "Stopping the server ...\c"
for PID in $PIDS ; do
    kill $PID > /dev/null 2>&1
done

echo "OK!"
echo "PID: $PIDS"
