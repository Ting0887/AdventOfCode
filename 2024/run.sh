#!/bin/bash

DAY="$1"

if [ -z "$DAY" ]; then
    echo "請提供要執行的程式名稱，例如: ./run.sh day1"
    exit 1
fi

FILE="src/main/java/$DAY.java"

if [ ! -f "$FILE" ]; then
    echo "找不到檔案: $FILE"
    exit 1
fi

# 編譯
mvn compile

# 執行
mvn exec:java -Dexec.mainClass="$DAY"