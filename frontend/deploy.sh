echo "hello frontend"
cd /home/ubuntu/dist
pwd
docker build -t routingstar/frontend:latest
docker container stop nginx
docker rm nginx
docker run -d --name nginx -p 80:80 routingstar/frontend