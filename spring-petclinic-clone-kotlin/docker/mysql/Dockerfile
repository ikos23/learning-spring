FROM mysql:8.0.19

ENV MYSQL_ROOT_PASSWORD=root-pwd

# To run SQL when MySQL started :)
COPY config-my-db.sql /docker-entrypoint-initdb.d

