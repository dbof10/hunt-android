PACKAGE=com.ctech.eaty ACTIVITY=ui.home.view.HomeActivity

./gradlew installDebug --parallel --offline
echo "Starting $ACTIVITY"
adb shell am start -n ${PACKAGE}/.${ACTIVITY}
say "Done"