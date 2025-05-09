Run this in terminal after downloading the jar to open program: 
java --module-path "C:\Program Files\Java\javafx-sdk-24\lib" --add-modules javafx.controls,javafx.fxml -jar OmniVent.jar

OR

TYPE THIS INTO NOTEPAD AND INSERT CHANGE TO LOCATION FOR YOUR COMPUTER, ONCE DONE SAVE IT AS A '.bat' FILE AND OPEN THAT FILE TO RUN THIS:
@echo off
java --module-path "C:\Program Files\Java\javafx-sdk-24\lib" --add-modules javafx.controls,javafx.fxml -jar [LOCATION OF THE .jar FILE]
pause
