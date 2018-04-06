# Java Sample Code for the Authorize.Net SDK
[![Travis CI Status](https://travis-ci.org/AuthorizeNet/sample-code-java.svg?branch=master)](https://travis-ci.org/AuthorizeNet/sample-code-java)

This repository contains working code samples which demonstrate Java integration with the [Authorize.Net Java SDK](https://www.github.com/AuthorizeNet/sdk-java).

The samples are organized into categories and common usage examples, just like our [API Reference Guide](http://developer.authorize.net/api/reference). Our API Reference Guide is an interactive reference for the Authorize.Net API. It explains the request and response parameters for each API method and has embedded code windows to allow you to send actual requests right within the API Reference Guide.


## Using the Sample Code

The samples are all completely independent and self-contained. You can analyze them to get an understanding of how a particular method works, or you can use the snippets as a starting point for your own project.

You can also run each sample directly from the command line.

## Running the Samples From the Command Line
* Clone this repository:
```
    $ git clone https://github.com/AuthorizeNet/sample-code-java.git
```
* Run "mvn package" in the root directory to create the SampleCode console app.
* Run the individual samples by name. For example:
```
     > java -jar target/SampleCode.jar [CodeSampleName]
```
e.g.
```
     > java -jar target/SampleCode.jar ChargeCreditCard
```
Running SampleCode without a parameter will give you the list of sample names.

**NOTE : You can update to your Sandbox credentials in SampleCode.java**

**For using behind proxy**

Please set the JAVA environment proxy using a similar code :
```
    System.setProperty("https.proxyUse", "true");
    System.setProperty("https.proxyHost", "127.0.0.1");
    System.setProperty("https.proxyPort", "3128");
```
**For using proxy authentication**

Please set the JAVA environment proxy credentials using a similar code :
```
    System.setProperty("https.proxyUserName", "exampleUsername");
    System.setProperty("https.proxyPassword", "examplePassword");
```
