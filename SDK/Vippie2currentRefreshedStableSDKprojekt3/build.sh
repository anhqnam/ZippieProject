#!/bin/sh

# Check for the file.
if [ -f lock ]; then
        echo "BUILD IS LOCKED BY OTHER THREADS!!!"
else
        touch lock;
        mkdir -p logs;
        LOG_PATH=logs/build.$(date +"%F-%H-%M").log;
        ant clean;
        ant zip-release > ${LOG_PATH} 2>&1;
        cat ${LOG_PATH};
        rm -f lock;
fi