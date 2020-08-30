/*global cordova, module*/

module.exports = {
    greet: function (name, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "Hello", "greet", [name]);
    },

    encryptthedata: function (name, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "Hello", "encryptthedata", [name]);
    },

    decryptthedata: function (name, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "Hello", "decryptthedata", [name]);
    },
	
	checkStatus: function (name, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "Hello", "checkStatus", [name]);
    },
	bookService: function (vinNumber, appointmentDate, appointmentSlot, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "Hello", "bookService", [vinNumber, appointmentDate, appointmentSlot]);
    },
    openapp: function (appid, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "Hello", "openapp", [appid]);
    }

};
