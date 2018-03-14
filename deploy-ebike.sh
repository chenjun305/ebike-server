#!/bin/bash

#数据库环境变量配置
source ./env_app

DATE=`date +%Y%m%d_%H%M`
#防止后面启动的java进程被jenkins杀
BUILD_ID=DONTKILLME

if [ $2 ];then
    APP_NAME=${2}
else
    echo "Usage: $0 {deploy|start|stop|restart|status}"
    exit 1   
fi

JAVA_OPTIONS_INITIAL=-Xms128M
JAVA_OPTIONS_MAX=-Xmx512M
_JAR_KEYWORDS=${APP_NAME}.jar

PID=$(ps aux | grep ${_JAR_KEYWORDS} | grep -v grep | awk '{print $2}' )

cd /u01/srv/ebike/
BASE_HOME=`pwd`
DEBUG_ARGS="-XX:+UseG1GC  -XX:+PrintGCDetails  -XX:+PrintGCTimeStamps  -Xloggc:$BASE_HOME/logs/${APP_NAME%-*}/gc.log -XX:HeapDumpPath=$BASE_HOME/logs/${APP_NAME%-*}/dumpfile.hprof -XX:+HeapDumpOnOutOfMemoryError"

function check_if_process_is_running {
 if [ "$PID" = "" ]; then
   return 1
 fi
 ps -p $PID | grep "java"
 return $?
}

case "$1" in
  status)
    if check_if_process_is_running
    then
      echo -e "\033[32m $APP_NAME is running \033[0m"
    else
      echo -e "\033[32m $APP_NAME not running \033[0m"
    fi
    ;;
  stop)
    if ! check_if_process_is_running
    then
      echo  -e "\033[32m $APP_NAME  already stopped \033[0m"
      exit 0
    fi
    kill -9 $PID
    echo -e "\033[32m Waiting for process to stop \033[0m"
    NOT_KILLED=1
    for i in {1..5}; do
      if check_if_process_is_running
      then
        echo -ne "\033[32m . \033[0m"
        sleep 1
      else
        NOT_KILLED=0
      fi
    done
    echo
    if [ $NOT_KILLED = 1 ]
    then
      echo -e "\033[32m Cannot kill process \033[0m"
      exit 1
    fi
    echo  -e "\033[32m $APP_NAME already stopped \033[0m"
    ;;

  start)
    if [ "$PID" != "" ] && check_if_process_is_running
    then
      echo -e "\033[32m $APP_NAME already running \033[0m"
      exit 1
    fi
   nohup java -jar $JAVA_OPTIONS_INITIAL $JAVA_OPTIONS_MAX $DEBUG_ARGS $_JAR_KEYWORDS > $BASE_HOME/logs/app.log 2>&1 & 
   echo -ne "\033[32m Starting \033[0m" 
    for i in {1..5}; do
        echo -ne "\033[32m.\033[0m"
        sleep 1
    done
    if check_if_process_is_running 
     then
       echo  -e "\033[32m $APP_NAME fail \033[0m"
    else
       echo  -e "\033[32m $APP_NAME started \033[0m"
    fi
    ;;

  deploy)
    mkdir /u01/srv/ebike_bak${DATE}
    /bin/mv -f /u01/srv/ebike/${_JAR_KEYWORDS} /u01/srv/ebike_bak${DATE}
    echo " deploying $_JAR_KEYWORDS..."
    cp /u01/deploy/u01/deploy/$_JAR_KEYWORDS /u01/srv/ebike/
    $0 start $APP_NAME
    ;;

  restart)
    $0 stop $APP_NAME
    if [ $? = 1 ]
    then
      exit 1
    fi
    $0 start $APP_NAME
    ;;
  *)
    echo "Usage: $0 {start|stop|restart|status}"
    exit 1
esac

exit 0
