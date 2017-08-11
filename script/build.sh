PACKAGE=com.ctech.eaty ACTIVITY=ui.home.view.HomeActivity

cd app/src/main/js
react-native bundle --platform android --dev false --entry-file live/index.android.js --bundle-output ../assets/react/live/index.android.bundle
cd ../../../../
./gradlew installDebug --parallel --offline
echo "Starting $ACTIVITY"
adb shell am start -n ${PACKAGE}/.${ACTIVITY}
