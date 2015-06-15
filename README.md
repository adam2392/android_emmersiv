# android_emmersiv
Emmersive android application.
Written by Adam Li

Purpose:
This application serves as a template for a home launch screen for an android device. It will display the existing applications installed on the device in a grid view. It will allow the user to select via seekbar, how long an application should run for. Then they can open up an app by selecting it, and then it will display toast messages and close within the selected amount of time. 

All apps are removed from recent history stack and are closed via the shell launch screen.

1. Create a list of applications installed on the Android OS.
2. Open another App.
3. Start a timer and then close that app (includes closing from recent history; done by setting the manifest.xml file)

Main Functions:
1) ListApps: Is just the main java file for handling the launch screen.
2) ApplicationAdapter: is the custom adapter to handle custom grid viewing
3) activity_listapps.xml: the layout for the main launch screen
4) snippet_row.xml: just the filler for each different gridview app and the layout of the icon w/ the description

