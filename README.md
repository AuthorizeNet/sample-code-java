# Sample Java Code for Authorize.Net
[![Build Status](https://travis-ci.org/AuthorizeNet/sample-code-java.png?branch=master)](https://travis-ci.org/AuthorizeNet/sample-code-java)

This repository contains working code samples which demonstrate Java integration with the Authorize.Net Java SDK
The samples are organized just like our API, which you can also try out directly here: http://developer.authorize.net/api/reference


## Using the Sample Code

The samples are all completely independent and self-contained so you can look at them to get a gist of how the method works, you can use the snippets to try in your own sample project, or you can run each sample from the command line.

## Running the Samples
 1.  Clone this repository.  
 2.  Run "mvn package" in the root directory to create the SampleCode console app.  
 3.  Then run a sample directly by name:    
```
     > java -jar target/SampleCode.jar [CodeSampleName]
```
e.g.
```
     > java -jar target/SampleCode.jar ChargeCreditCard
```
Running SampleCode without a parameter will give you the list of sample names.  Handy or what!

**NOTE : You can update to your Sandbox credentials in SampleCode.java**

**For using behind proxy**

Please set the JAVA environment proxy using a similar code :
```
    System.setProperty("https.proxyUse", "true");
    System.setProperty("https.proxyHost", "127.0.0.1");
    System.setProperty("https.proxyPort", "3128");
```

*PLEASE NOTE THIS PROJECT IS CURRENTLY UNDER DEVELOPMENT*
