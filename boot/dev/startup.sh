#!/usr/bin/env sh
#export JAVA_HOME=${JAVA_HOME8}
#export PATH=$JAVA_HOME/bin:$PATH

jarName="SpringBootDemo-0.1.jar"

# 进入脚本目录
cd $(dirname $0)
DEPLOY_DIR=$(pwd)
# 创建日志文件夹
LOG_DIR=$DEPLOY_DIR/logs
if [ ! -d $LOG_DIR ]; then
    mkdir $LOG_DIR
fi

# 代理设置
AGENT_OPTS=/xx/xx-agent.jar
if [ -f "${AGENT_OPTS}" ]; then
  JAVA_OPTS="${JAVA_OPTS} -javaagent:${AGENT_OPTS}"
fi

# JVM设置
JAVA_OPTS="$JAVA_OPTS -Xms1024m -Xmx1024m"

export JAVA_OPTS;

# Springboot启动环境设置
SPRING_BOOT_OPTS="--spring.profiles.active=dev"
STDOUT_FILE=/dev/null #$LOG_DIR/stdout.log

# 启动程序
cd $DEPLOY_DIR
nohup java $JAVA_OPTS -jar "$DEPLOY_DIR/$jarName" $SPRING_BOOT_OPTS >> $STDOUT_FILE 2>&1 &

# 显示启动结果
echo "OK!"
PID=$(ps -ef | grep java | grep "$DEPLOY_DIR/$jarName" |grep -v grep | awk '{print $2}')
echo "PID: $PID"
echo "STDOUT: $STDOUT_FILE"
