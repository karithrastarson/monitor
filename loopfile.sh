#!/usr/bin/env bash
    while true
do
    curl -i -X POST http://localhost:8484/monitor/refresh
    sleep 60
done