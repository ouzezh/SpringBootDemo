#!/usr/bin/env sh
#export JAVA_HOME=${JAVA_HOME8}
#export PATH=$JAVA_HOME/bin:$PATH

jarName="SpringBootDemo-0.1.jar"

# 进入脚本目录
cd `dirname $0`
DEPLOY_DIR=`pwd`

# 显示进程信息
ps -ef | grep java | grep "$DEPLOY_DIR/${jarName}" | grep -v grep

function getPid() {
  ps -ef | grep java | grep "$DEPLOY_DIR/${jarName}" | grep -v grep | awk '{print $2}'
}

function checkShutdownStatus() {
  for i in {1..60}
  do
    echo "check shutdown status $i"
    PID=$(getPid)
    if [ -n "$PID" ]; then
      sleep 1s
    else
      break
    fi
  done
}

# 获取进程PID
PID=$(getPid)
if [ -z "$PID" ]; then
  echo "WARN: The server does not started!"
  exit 0
fi

# 停止进程
echo -e "Stopping the server ... \c"
echo $PID
for PID in $PID ; do
  kill $PID || echo "kill $PID failed!"
done
# 检查停止状态
checkShutdownStatus

# 显示停止结果
PID=$(getPid)
if [ -z "$PID" ]; then
  echo "server is stopped success!"
else
  echo "server is stopped fail!"
  exit 1
fi
