#!/bin/bash
./gradlew -p frodo-api/ clean assemble install --configure-on-demand
./gradlew -p frodo-runtime/ clean assembleDebug install --configure-on-demand -x lint
./gradlew -p  frodo-plugin/ clean build install --configure-on-demand