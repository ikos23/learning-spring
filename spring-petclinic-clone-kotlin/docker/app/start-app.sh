#!/bin/sh

set -x

echo "**** Petclinic Clone Kotlin ****"

# Wait until MySQL is ready
while ! mysqladmin ping -h"mysql" -P"3306" --silent; do
    echo "Waiting for MySQL to be up..."
    sleep 1
done

echo "MySQL is up. Start the app..."

java -jar /my-app.jar