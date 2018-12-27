# JXLazyController

**J**    -> Java
**X**    -> Xinput
**LazyController** -> I made this so I could be more lazy in those winter nights and control my PC connected to a large display / projector without having to come out from my blanket.

Control Mouse and Keyboard using XInput Controllers (Xbox 360, Xbox One, Logitech F710, etc)

## Build
Maven is used as the build system. 

    mvn clean build

To build the jar file. You can keep this file in your startup folder.

## Key Mappings
| Controller |  Function|
|--|--|
| Left Axes |  Mouse movement
Right Axes | Scroll
A | Left mouse click
X | Right mouse click
Y | Show / Hide On-screen Keyboard
B | Enter
D_Pad | Arrow Keys
Back | Esc
Start | Windows Key
Left Shoulder / Left Bumper | Ctrl 
Right Shoulder / Right Bumper | Alt 
Left Thumbstick Button | Middle Mouse Button 
Right Thumbstick Button | Windows Search (with Cortana)
Left Trigger | Spacebar 
Right Trigger | Backspace |



## Special Functions
| Combination | Function  |
|--|--|
| Back + Start | Start / Stop Listening from Controller  
 Left Bumper + Right Bumper | Cycle through various speed levels 
 Left Trigger + D_UP | Increase Volume 
 Left Trigger + D_DOWN | Decrease Volume 
 Left Trigger + D_RIGHT | Next Media Key
 Left Trigger + D_LEFT | Previous Media Key
 Left Trigger + B | Play / Pause 
 Right Trigger + D_UP | Browser zoom-in 
 Right Trigger + D_DOWN | Browser zoom-out 
 Right Trigger + D_LEFT | Browser previous tab 
 Right Trigger + D_RIGHT | Browser next tab 
 Right Trigger + B | Browser Refresh (F5) |



## To Do

1. Add horizontal scroll
2. Add continuous arrow key function on continuous press
3. Add notifications for important events 
4. Add features for presentation controls like pointers, etc


