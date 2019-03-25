FROM mysql:8.0.15
MAINTAINER kyree

RUN apt-get update \
  && apt-get install -y vim psmisc

COPY db.sql /docker-entrypoint-initdb.d/db.sql

ENV MYSQL_ROOT_PASSWORD password

RUN /entrypoint.sh mysqld & sleep 30 && killall mysqld
#RUN rm /docker-entrypoint-initdb.d/db.sql

#HEALTHCHECK --interval=5m --timeout=3s CMD curl -f http://localhost/ || exit 1
#HEALTHCHECK --interval=3s --timeout=30s CMD ./healthcheck.sh
  
#RUN mysql -u root --password=password -e "CREATE DATABASE sample"