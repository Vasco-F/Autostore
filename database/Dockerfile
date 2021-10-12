FROM mysql:5.7.35
# ENV MYSQL_ROOT_PASSWORD admin
# ENV MYSQL_PASSWORD admin
ADD create-table.sql /docker-entrypoint-initdb.d/
EXPOSE 3306