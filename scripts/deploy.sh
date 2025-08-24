#!/bin/bash

PROJECT_NAME=ssgb
REPOSITORY=/home/ssgb_server/
PACKAGE=$REPOSITORY
JAR_NAME=$(ls -tr $PACKAGE | grep 'SNAPSHOT.jar' | tail -n 1)
JAR_PATH=$PACKAGE$JAR_NAME

echo $JAR_NAME >> /home/ssgb_server/deploy.log
echo $JAR_PATH >> /home/ssgb_server/deploy.log

cd $REPOSITORY

CURRENT_PID=$(pgrep -f $PROJECT_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 종료할 애플리케이션이 없습니다" >> /home/ssgb_server/deploy.log
else
  echo "> 실행 중인 애플리케이션 종료 $CURRENT_PID" >> /home/ssgb_server/deploy.log
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> 배포 - $JAR_PATH" >> /home/ssgb_server/deploy.log
chmod +x $JAR_PATH

java -jar $JAR_PATH >> /home/ssgb_server/deploy.log 2>/home/ssgb_server/deploy_err.log &