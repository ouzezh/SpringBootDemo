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
    echo "ERROR: The server does not started!"
    exit 1
else
  echo -e "Stopping the server ... \c"
  echo $PIDS
  curl -X POST "http://localhost:8080/payroll/shutdown" -H 'token: x' || echo "shutdown by curl failed!"
  echo
  checkShutdownStatus

  PIDS=$(getPids)
  if [ -n "$PIDS" ]; then
    for PID in $PIDS ; do
      kill $PID || echo "kill failed!"
    done
    checkShutdownStatus
  fi

fi

PIDS=$(getPids)
if [ -z "$PIDS" ]; then
  echo "server is stopped success!"
else
  echo "server is stopped fail!"
  exit 1
fi
