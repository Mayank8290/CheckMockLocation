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
         String appid = data.getString(0);

         Intent openapp = new Intent(Intent.ACTION_VIEW);
         openapp.setComponent(new ComponentName("com.herocorp","com.herocorp.ui.activities.BaseDrawerActivity"));
         openapp.putExtra("app_id", appid); //sending token to get values
         openapp.putExtra("dataextra", "");
         cordova.getContext().startActivity(openapp);
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
