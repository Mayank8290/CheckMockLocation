# Cordova Plugin For Detect Fake GPS Apps and Mock Location Enable

Simple plugin to check mock location and view secure pdf.

Work with Ionic 2+ 

## Using

**Create a new Cordova Project**

    $ ionic cordova start MockLocationDetection
    
**Install the plugin**

    $ cd MockLocationDetection
    $ ionic cordova plugin add https://github.com/Mayank8290/CheckMockLocation.git
    

**How To Use For Mock Location**
```python
window['hello'].checkStatus(function (error) {
      console.log("error",error);
    }, function (success) {
      console.log("success : " + success);
});

The Sucess Callback has the value true or false.

true = Found Mock Locations enabled or Mock Location Apps
false = Mock Location disabled with no apps.
```


**How to Use For View PDF**
```python

 displaypdf() {

    if(platform == "android")
    {
       window['hello'].openapp("http://www.africau.edu/images/default/sample.pdf",(b) => this.errorCallback(b), (a) => this.successCallback(a));
    }
    else if(platform == "ios")
    {
        const browser = this.iab.create('http://www.africau.edu/images/default/sample.pdf');
    }

    
  }

successCallback(result) {

    console.log("result : ", result); // true - enabled, false - disabled
    this.value = result;
  }

  errorCallback(error) {

    console.log("result error : ", error);
  }

```


**Install iOS or Android platform**

    ionic cordova platform add ios
    ionic cordova platform add android@8.0.0
    
**Run the code**
```python
   ionic cordova run android
   ionic cordova run ios
```

