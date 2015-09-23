gradlew -p frodo-api/ assemble install --configure-on-demand
gradlew -p frodo-runtime/ assembleDebug install --configure-on-demand -x lint
gradlew -p  frodo-plugin/ build install --configure-on-demand