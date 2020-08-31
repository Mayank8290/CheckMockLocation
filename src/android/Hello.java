package com.example.plugin;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import android.content.ComponentName;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

//Cordova imports
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;

//Android imports
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.util.Log;
//import android.provider.Settings;
import android.util.Base64;

//Package Cryptografy
//import sun.misc.*;
import javax.crypto.*;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import javax.crypto.spec.SecretKeySpec;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


//JSON Imports
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;







public class Hello extends CordovaPlugin {

  String key = "5468617473206D79204B756E67204675";
  String cipherTeks = " ";
  public static String IV = "AAAAAAAAAAAAAAAA";
  String original = "";

  @Override
  public boolean execute(String action, JSONArray data, CallbackContext callbackContext) {


    try {
      if (action.equals("greet")) {

        String email = data.getString(0);


        String securedData = null;

        /*
         * Encrypt the data before sharing using AES encryption.
         * Please use this Helper EncryptionUtility.class for encryption in your main application.
         * Encryption is key is mentioned in the method below.
         */
        try {
          securedData = encryptforhealthsdk("A1HS8CUR1TY@9812", email, "A1HS8CUR1TY@9812");
        } catch (Exception e) {
          e.printStackTrace();
        }


        String INTENT_ACTION = "com.vivant.heromotocorp.HMCL_LAUNCHER";
        Intent intent = new Intent();
        intent.setAction(INTENT_ACTION);
        intent.putExtra("E-MAIL", securedData);

        try {
          cordova.getContext().startActivity(intent);
        } catch (ActivityNotFoundException e) {
          e.printStackTrace();
        }
        return true;

      } else if (action.equals("encryptthedata")) {


        return true;
      } else if (action.equals("decryptthedata")) {

        return true;
      }  else if (action.equals("mymethod"))
      {
        String name = data.getString(0);

        callbackContext.success(name);
        return true;
      }else if (action.equals("checkStatus")) // letsService
      {
        if(isMockSettingsON(cordova.getContext()) || areThereMockPermissionApps(cordova.getContext()))
        {
          callbackContext.success("true");
        }
        else
        {
          callbackContext.success("false");
        }
        return true;
      }else if (action.equals("bookService")) //letsService
      {

        String email = data.getString(0);


        String securedData = null;

        /*
         * Encrypt the data before sharing using AES encryption.
         * Please use this Helper EncryptionUtility.class for encryption in your main application.
         * Encryption is key is mentioned in the method below.
         */
        try {
          securedData = encryptforhealthsdk("A1HS8CUR1TY@9812", email, "A1HS8CUR1TY@9812");
        } catch (Exception e) {
          e.printStackTrace();
        }


        String INTENT_ACTION = "com.vivant.heromotocorp.HMCL_LAUNCHER";
        Intent intent = new Intent();
        intent.setAction(INTENT_ACTION);
        intent.putExtra("E-MAIL", securedData);

        try {
          cordova.getContext().startActivity(intent);
        } catch (ActivityNotFoundException e) {
          e.printStackTrace();
        }
        return true;
      }
      else if(action.equals("openapp"))
      {
         String link = data.getString(0);
         Intent intent = new Intent(cordova.getContext(), com.example.plugin.ShowPdf.class);
         intent.putExtra("pdflink", link);
         cordova.getContext().startActivity(intent);
         return true;
      }


      else {
        callbackContext.error("Action is not greek");
        return false;

      }

    }catch (Exception e)
    {
      //If we get here, then something horrible has happened
      System.err.println("Exception: " + e.getMessage());
      callbackContext.error(e.getMessage());
      return false;
    }
  }


  // check for mock location



  public static boolean isMockSettingsON(Context context) {
    // returns true if mock location enabled, false if not enabled.
    if (Settings.Secure.getString(context.getContentResolver(),
            Settings.Secure.ALLOW_MOCK_LOCATION).equals("0"))
      return false;
    else
      return true;
  }


  public static boolean areThereMockPermissionApps(Context context) {
    int count = 0;

    PackageManager pm = context.getPackageManager();
    List<ApplicationInfo> packages =
            pm.getInstalledApplications(PackageManager.GET_META_DATA);

    for (ApplicationInfo applicationInfo : packages) {
      try {
        PackageInfo packageInfo = pm.getPackageInfo(applicationInfo.packageName,
                PackageManager.GET_PERMISSIONS);

        // Get Permissions
        String[] requestedPermissions = packageInfo.requestedPermissions;

        if (requestedPermissions != null) {
          for (int i = 0; i < requestedPermissions.length; i++) {
            if (requestedPermissions[i]
                    .equals("android.permission.ACCESS_MOCK_LOCATION")
                    && !applicationInfo.packageName.equals(context.getPackageName())
                    && !applicationInfo.packageName.equals("com.android.calendar")
              ) {
              count++;
            }
          }
        }
      } catch (PackageManager.NameNotFoundException e) {
        Log.e("Got exception " , e.getMessage());
      }
    }

    if (count > 0)
      return true;
    return false;
  }






  //








  public static String encryptforhealthsdk(final String key, String message, String IV)
    throws GeneralSecurityException {
    try {

      byte[] ivBytes = IV.getBytes();
      byte[] keyBytes = key.getBytes("UTF-8");

      final SecretKeySpec secreteKey = new SecretKeySpec(keyBytes, "AES");
      byte[] cipherText = encrypt(secreteKey, ivBytes, message.getBytes("UTF-8"));
      String encoded = Base64.encodeToString(cipherText, Base64.NO_WRAP);

      return encoded;
    } catch (UnsupportedEncodingException e) {

      throw new GeneralSecurityException(e);
    }
  }

  public static byte[] encrypt(final SecretKeySpec key, final byte[] iv, final byte[] message)
    throws GeneralSecurityException {
    byte[] cipherText = new byte[0];
    try {
      final Cipher cipher = Cipher.getInstance("AES/CBC/ISO10126Padding");
      IvParameterSpec ivSpec = new IvParameterSpec(iv);
      cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
      cipherText = cipher.doFinal(message);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return cipherText;
  }

}
