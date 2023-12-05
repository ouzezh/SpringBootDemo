#!/usr/bin/env sh
#export JAVA_HOME=${JAVA_HOME8}
#export PATH=$JAVA_HOME/bin:$PATH

appName="SpringBootDemo"

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

## JVM设置
# Xms/Xmx  堆空间
# MetaspaceSize  元空间
# -XX:+UseG1GC  垃圾回收器
# -XX:+ParallelRefProcEnabled 尽可能启用并行引用处理
# -XX:+PrintGCDetails 打印GC日志详情
# -XX:+PrintGCDateStamps  在垃圾收集处打印日期戳
# -XX:ErrorFile 错误日志
# -Xloggc 垃圾回收日志
# -XX:HeapDumpPath  堆内存Dump目录
# HeapDumpOnOutOfMemoryError  堆内存溢出后Dump
JAVA_OPTS="$JAVA_OPTS -Xms5440m -Xmx5440m \
 -XX:MaxMetaspaceSize=512M -XX:MetaspaceSize=512M \
 -XX:+UseG1GC -XX:+ParallelRefProcEnabled \
 -XX:+PrintGCDetails -XX:+PrintGCDateStamps \
 -XX:ErrorFile=${LOG_DIR}/${APPNAME}/hs_err_${datetime}.log \
 -Xloggc:${LOG_DIR}/${APPNAME}/gc.log \
 -XX:HeapDumpPath=${LOG_DIR}/${APPNAME}.dump -XX:+HeapDumpOnOutOfMemoryError"

export JAVA_OPTS;

# Springboot启动环境设置
SPRING_BOOT_OPTS="--spring.profiles.active=dev"
STDOUT_FILE=${LOG_DIR}/app.log

# 启动程序
cd $DEPLOY_DIR
nohup java $JAVA_OPTS -jar "$DEPLOY_DIR/${appName}.jar" $SPRING_BOOT_OPTS >> $STDOUT_FILE 2>&1 &

# 显示启动结果
echo "OK!"
PID=$(ps -ef | grep java | grep "$DEPLOY_DIR/${appName}.jar" |grep -v grep | awk '{print $2}')
echo "PID: $PID"
echo "STDOUT: $STDOUT_FILE"
