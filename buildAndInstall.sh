#!/bin/sh

# Build .jar
./gradlew lintrules:assemble

# Install
if [ ! -d "~/.android/lint/" ]; then
  mkdir ~/.android/lint/
fi

cp lintrules/build/libs/lintrules.jar ~/.android/lint/