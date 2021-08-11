echo "hello"
cd /home/ubuntu/app/backend
pwd
docker build -t routingstar/backend:latest .
docker container stop routingstar
docker rm routingstar
docker run -d --name routingstar -p 8081:8081 routingstar/backend