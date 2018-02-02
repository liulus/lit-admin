#基础镜像：仓库是java，tag是8
FROM java:8
#将打包好的spring程序拷贝到容器中的指定位置
ADD target/lit-webstarter.jar lit-webstarter.jar
#容器对外暴露8080端口
EXPOSE 8080
ENV JAVA_OPTS="-Dspring.profiles.active=docker"
#容器启动后需要执行的命令
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar lit-webstarter.jar"]