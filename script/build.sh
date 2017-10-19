PACKAGE=com.ctech.eaty ACTIVITY=ui.home.view.HomeActivity

cd app/src/main/js
react-native bundle --platform android --dev false --entry-file live.android.js --bundle-output ../assets/react/live/index.android.bundle
react-native bundle --platform android --dev false --entry-file jobs.android.js --bundle-output ../assets/react/job/index.android.bundle
cd ../../../../
./gradlew installDebug --parallel --offline
echo "Starting $ACTIVITY"
adb shell am start -n ${PACKAGE}/.${ACTIVITY}
