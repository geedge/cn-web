ARG JDK_IMAGE
#设置依赖于jdk:1.8.0_73(自定义jdk),需要在docker中安装该版本的jdk,如不想使用该版本,可自行指定jdk版本
FROM ${JDK_IMAGE}
MAINTAINER shizhendong shizhendong@zdjizhi.com

#设置时区,编码(防止中文乱码)等环境变量
ENV TimeZone=Asia/Shanghai LANG=en_US.UTF-8 LANGUAGE=en_US:en LC_ALL=en_US.UTF-8 PATH=$PATH:/opt/cn/cn-web/go/bin

#设置时区
RUN  echo $TimeZone > /etc/timezone

WORKDIR /opt/cn/cn-web

#将当前目录下的config目录复制到docker的WORKDIR中,项目启动的时候直接读取里面的配置文件
COPY config config
#复制jar到docker的WORKDIR目录
ARG JAR_FILE
COPY  ${JAR_FILE} cn-admin.xjar
COPY  xjar.go xjar.go
ADD  go.tar.gz ./

#默认启动jar
#ENTRYPOINT ["java","-jar","syncconfigtoredis.jar"]
RUN yum -y install bind-utils pango.x86_64 libXcomposite.x86_64 libXcursor.x86_64 libXdamage.x86_64 libXext.x86_64 libXi.x86_64 libXtst.x86_64 cups-libs.x86_64 libXScrnSaver.x86_64 libXrandr.x86_64 GConf2.x86_64 alsa-lib.x86_64 atk.x86_64 gtk3.x86_64 && chmod -R 755 /opt/cn/cn-web/go/ && go build xjar.go && chmod +x /opt/cn/cn-web/xjar
#默认启动jar
ENTRYPOINT ["./xjar","java","--add-opens java.base/jdk.internal.loader=ALL-UNNAMED","--add-opens  jdk.zipfs/jdk.nio.zipfs=ALL-UNNAMED","-jar","cn-admin.xjar"]

#装到docker容器中名称是syncconfigtoredis,tag是1.0
#docker build -t syncconfigtoredis:1.0 .
#启动docker容器,--restart=always设置程序挂了之后自动重启,-d后台运行,-v指定共享目录 -e JAVA_OPTS设置jvm参数 -p指定宿主机端口与docker主机端口的映射,docker端口取决于/home/ceiec/bifang/api/config/application.properties
#docker run --restart=always -d -v /opt/cn/cn-web/config:/opt/cn/cn-web/config -v /opt/cn/cn-web/logs/:/opt/cn/cn-web/logs/ -p 9998:9998  -e JAVA_OPTS='-Xms512m -Xmx40960m' syncconfigtoredis:1.0
