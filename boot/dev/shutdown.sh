#!/usr/bin/env sh
#export JAVA_HOME=${JAVA_HOME8}
#export PATH=$JAVA_HOME/bin:$PATH

jarName="SpringBootDemo-0.1.jar"

cd `dirname $0`
DEPLOY_DIR=`pwd`

ps -ef | grep java | grep "$DEPLOY_DIR/${jarName}" | grep -v grep

function getPids() {
  ps -ef | grep java | grep "$DEPLOY_DIR/${jarName}" | grep -v grep | awk '{print $2}'
}

function checkShutdownStatus() {
  for i in {1..60}
  do
    echo "check shutdown status $i"
    PIDS=$(getPids)
    if [ -n "$PIDS" ]; then
      sleep 1s
    else
      break
    fi
  done
}

PIDS=$(getPids)
if [ -z "$PIDS" ]; then
  echo "WARN: The server does not started!"
  exit 0
fi

echo -e "Stopping the server ... \c"
echo $PIDS
for PID in $PIDS ; do
  kill $PID || echo "kill $PID failed!"
done
checkShutdownStatus

PIDS=$(getPids)
if [ -z "$PIDS" ]; then
  echo "server is stopped success!"
else
  echo "server is stopped fail!"
  exit 1
fi
