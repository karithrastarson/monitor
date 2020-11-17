#!/usr/bin/env bash
    while true
do
    curl -i -X POST http://localhost:8080/news/refresh
    sleep 60
done