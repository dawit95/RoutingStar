echo "hello"
cd /home/ubuntu/app/backend
pwd
docker build -t routingstar/backend:latest .
docker run -d -p 8081:8081 routingstar/backend --name routingstar/backend