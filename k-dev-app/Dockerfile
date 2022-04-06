FROM registry.cn-hangzhou.aliyuncs.com/mt23/jre8-alpine-shanghai:1.0.0
MAINTAINER The team of self-killer
VOLUME /tmp
ADD target/*jar app.jar
# 开放端口
EXPOSE 8080
# jvm 配置
ENV JAVA_OPTS=${JAVA_OPTS}
# 指定的springboot的配置文件
ENV ACTIVE=${ACTIVE}
# 服务的端口
ENV SERVER_PORT=${SERVER_PORT}
# k-faas配置
ENV KFAAS_HOST=${KFAAS_HOST}
# 文件存储基础路径
ENV FILE_BASE_PATH=${FILE_BASE_PATH}
# 设置启动
ENTRYPOINT java ${JAVA_OPTS} -jar /app.jar --spring.profiles.active=${ACTIVE} --server.port=${SERVER_PORT} --database.sources.db.server=${KFAAS_HOST} --file.base-path=${FILE_BASE_PATH}
