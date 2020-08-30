# Cordova Plugin For Detect Fake GPS Apps and Mock Location Enable

Simple plugin that returns true if it found mock location enabled with mock lockation apps.


## Using

**Create a new Cordova Project**

    $ ionic cordova start MockLocationDetection
    
**Install the plugin**

    $ cd MockLocationDetection
    $ ionic cordova plugin add https://github.com/Mayank8290/CheckMockLocation.git
    

**How To Use**
```python
window['hello'].checkStatus(function (error) {
      console.log("error",error);
    }, function (success) {
      console.log("success : " + success);
});
```

The Sucess Callback has the value true or false.

true = Found Mock Locations enabled or Mock Location Apps
false = Mock Location disabled with no apps.

**Install iOS or Android platform**

    ionic cordova platform add ios
    ionic cordova platform add android@8.0.0
    
**Run the code**

   ionic cordova run android
   ionic cordova run ios


