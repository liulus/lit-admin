#基础镜像：仓库是java，标签用jdk8
FROM java:8
#将打包好的spring程序拷贝到容器中的指定位置
ADD target/lit-webstarter.jar lit-webstarter
#容器对外暴露8080端口
EXPOSE 8080
#容器启动后需要执行的命令
CMD java -Djava.security.egd=file:/dev/./urandom -jar lit-webstarter