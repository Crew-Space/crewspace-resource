echo "> 현재 실행 중인 Docker 컨테이너 pid 확인" >> /home/ubuntu/resource/deploy.log
CURRENT_PID=$(sudo docker container ls -q)

if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 Docker 컨테이너가 없으므로 종료하지 않습니다." >> /home/ubuntu/resource/deploy.log
else
  echo "> sudo docker stop $CURRENT_PID"
  sudo docker stop $CURRENT_PID
  sudo docker rm $CURRENT_PID
  sleep 5
fi

cd /home/ubuntu/resource

sudo docker rmi $(sudo docker images | grep 'resource') # 기존에 있던 이미지 삭제
sudo docker build -t resource ./ # 이미지 생성
sudo docker run -d -p 8080:8080 resource # 컨테이너 실행
