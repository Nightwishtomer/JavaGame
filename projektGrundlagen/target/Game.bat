@echo off
set JAR_NAME=projektGrundlagen-0.0-shaded.jar
set JAVAFX_PATH="C:\javafx-sdk-21\lib"

:: Проверка, установлен ли Java
where java >nul 2>nul
if errorlevel 1 (
    echo Java not found. Install Java and add to PATH.
    pause
    exit /b
)

:: Start JAR
echo Start %JAR_NAME%...
java --module-path %JAVAFX_PATH% --add-modules javafx.controls,javafx.fxml -jar %JAR_NAME%

pause
