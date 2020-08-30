#import "HWPHello.h"

@implementation HWPHello

- (void)greet:(CDVInvokedUrlCommand*)command
{
     
     NSString *base64Key = @"NTQ2ODYxNzQ3MzIwNkQ3OTIwNEI3NTZFNjcyMDQ2NzU=";
  
    NSString *encryptedBase64Data = @"";

    NSString *decryptedBase64Data = @"";

    NSString *base64ive = @"QUFBQUFBQUFBQUFBQUFBQQ==";

    
    NSString* name = [[command arguments] objectAtIndex:0];
  //  NSString* msg = [NSString stringWithFormat: @"Hello, %@", name];


    NSData* nsdata = [name dataUsingEncoding:NSUTF8StringEncoding];


    /// encrption start
NSData *key  = [[NSData alloc] initWithBase64EncodedString:base64Key  options:0];

NSData *dive  = [[NSData alloc] initWithBase64EncodedString:base64ive  options:0];

NSData *encryptedData = [self crypt:nsdata
                                 iv:dive
                                key:key
                            context:kCCEncrypt];

 encryptedBase64Data = [encryptedData base64EncodedStringWithOptions:0];


 //encryption end


//decryption start

 NSData *decodedDatae = [[NSData alloc] initWithBase64EncodedString:encryptedBase64Data options:0];


NSData *decrptedData = [self decrpt:decodedDatae
                                 iv:dive
                                key:key
                            context:kCCDecrypt];


decryptedBase64Data = [decrptedData base64EncodedStringWithOptions:0];

//
NSData *decodedData = [[NSData alloc] initWithBase64EncodedString:decryptedBase64Data options:0];
NSString *decodedString = [[NSString alloc] initWithData:decodedData encoding:NSUTF8StringEncoding];

//





    // decrption end
   
    CDVPluginResult* result = [CDVPluginResult
                               resultWithStatus:CDVCommandStatus_OK
                               messageAsString:decodedString];

     
    

    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}


- (void)encryptthedata:(CDVInvokedUrlCommand*)command
{

    NSString *base64Key = @"NTQ2ODYxNzQ3MzIwNkQ3OTIwNEI3NTZFNjcyMDQ2NzU=";   // its the base 64 of key that we are used in java
  
    NSString *encryptedBase64Data = @"";


    NSString *base64ive = @"QUFBQUFBQUFBQUFBQUFBQQ==";   // its the base 64 of IV that we are used in java

    
    NSString* name = [[command arguments] objectAtIndex:0];
  //  NSString* msg = [NSString stringWithFormat: @"Hello, %@", name];


    NSData* nsdata = [name dataUsingEncoding:NSUTF8StringEncoding];

    /// encrption start
NSData *key  = [[NSData alloc] initWithBase64EncodedString:base64Key  options:0];

NSData *dive  = [[NSData alloc] initWithBase64EncodedString:base64ive  options:0];

NSData *encryptedData = [self crypt:nsdata
                                 iv:dive
                                key:key
                            context:kCCEncrypt];

 encryptedBase64Data = [encryptedData base64EncodedStringWithOptions:NSDataBase64EncodingEndLineWithLineFeed];


  NSString* newdatatosend =[encryptedBase64Data stringByReplacingOccurrencesOfString:@"\r" withString:@""];

CDVPluginResult* result = [CDVPluginResult
                               resultWithStatus:CDVCommandStatus_OK
                               messageAsString:newdatatosend];

     
    

    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];


}


//


- (void)decryptthedata:(CDVInvokedUrlCommand*)command
{

    NSString *base64Key = @"NTQ2ODYxNzQ3MzIwNkQ3OTIwNEI3NTZFNjcyMDQ2NzU=";   // its the base 64 of key that we are used in java
  
  
    NSString *decryptedBase64Data = @"";

    NSString *base64ive = @"QUFBQUFBQUFBQUFBQUFBQQ==";   // its the base 64 of IV that we are used in java


  NSString* name = [[command arguments] objectAtIndex:0];

 NSData *key  = [[NSData alloc] initWithBase64EncodedString:base64Key  options:0];

NSData *dive  = [[NSData alloc] initWithBase64EncodedString:base64ive  options:0];

    
   
    

    NSData *decodedDatae = [[NSData alloc] initWithBase64EncodedString:name options:0];


   

NSData *decrptedData = [self decrpt:decodedDatae
                                 iv:dive
                                key:key
                            context:kCCDecrypt];


decryptedBase64Data = [decrptedData base64EncodedStringWithOptions:0];

//
NSData *decodedData = [[NSData alloc] initWithBase64EncodedString:decryptedBase64Data options:0];
NSString *decodedString = [[NSString alloc] initWithData:decodedData encoding:NSUTF8StringEncoding];

//
    // decrption end
   
    CDVPluginResult* result = [CDVPluginResult
                               resultWithStatus:CDVCommandStatus_OK
                               messageAsString:decodedString];

     
    

    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];


}


//





- (NSData *)crypt:(NSData *)dataIn
                  iv:(NSData *)iv
                 key:(NSData *)symmetricKey
             context:(CCOperation)encryptOrDecrypt
{
    CCCryptorStatus ccStatus   = kCCSuccess;
    size_t          cryptBytes = 0;    // Number of bytes moved to buffer.
    NSMutableData  *dataOut    = [NSMutableData dataWithLength:dataIn.length + kCCBlockSizeAES128];

    ccStatus = CCCrypt( encryptOrDecrypt,
                       kCCAlgorithmAES128,
                       kCCOptionPKCS7Padding,
                       symmetricKey.bytes,
                       kCCKeySizeAES256,
                       iv.bytes,
                       dataIn.bytes,
                       dataIn.length,
                       dataOut.mutableBytes,
                       dataOut.length,
                       &cryptBytes);

    if (ccStatus != kCCSuccess) {
        NSLog(@"CCCrypt status: %d", ccStatus);
    }

    dataOut.length = cryptBytes;

    return dataOut;
}



- (NSData *)decrpt:(NSData *)dataIn
                  iv:(NSData *)iv
                 key:(NSData *)symmetricKey
             context:(CCOperation)encryptOrDecrypt
{
    CCCryptorStatus ccStatus   = kCCSuccess;
    size_t          cryptBytes = 0;    // Number of bytes moved to buffer.
    NSMutableData  *dataOut    = [NSMutableData dataWithLength:dataIn.length + kCCBlockSizeAES128];

    ccStatus = CCCrypt( encryptOrDecrypt,
                       kCCAlgorithmAES128,
                       kCCOptionPKCS7Padding,
                       symmetricKey.bytes,
                       kCCKeySizeAES256,
                       iv.bytes,
                       dataIn.bytes,
                       dataIn.length,
                       dataOut.mutableBytes,
                       dataOut.length,
                       &cryptBytes);

    if (ccStatus != kCCSuccess) {
        NSLog(@"CCCrypt status: %d", ccStatus);
    }

    dataOut.length = cryptBytes;

    return dataOut;
}



@end
