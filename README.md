# springboot-docker-demo
编译镜像
docker build -t springboot-docker-demo -f Dockerfile

运行
docker run  -p 8080:8080  springboot-docker-demo