# Cordova Plugin For Detect Fake GPS Apps and View Secure PDF

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

The Sucess Callback has the value true , false and true with package name.

true = Found Mock Locations enabled
true~~Package_Name_Of_App_That_Has_A_Mock_Location_Permission = Found Mock Location Permission in an app , it will return true concat with package name seprated by ~~
false = Mock Location disabled with no apps.
```


**How to Use For View PDF**
```python

 displaypdf() {

    if(platform == "android")
    {
       // please pass pdf link and pdf name by concat them using ~~ below is the example//
       window['hello'].openapp("http://www.africau.edu/images/default/sample.pdf~~pdfname",(b) => this.successCallback(b), (a) => this.errorCallback(a));
    }
    else if(platform == "ios")
    {
        const browser = this.iab.create('http://www.africau.edu/images/default/sample.pdf');
    }

    
  }

successCallback(result) {
    // you will get true in result
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

