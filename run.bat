@echo off
set current_dir=%cd%\fxlib\javafx-sdk-11.0.2\lib
set current_dir = C:\Program Files\Java\javafx-sdk-11.0.2\lib
echo The JavaFX directory is this: %current_dir%
cd output
cd AsteroidMiningGame
java --module-path %current_dir% --add-modules javafx.controls Main
cd ..