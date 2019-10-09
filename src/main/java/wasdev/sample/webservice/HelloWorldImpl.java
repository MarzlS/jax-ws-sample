package wasdev.sample.webservice;

import javax.jws.WebService;

@WebService(serviceName = "HelloWorld", endpointInterface="wasdev.sample.webservice.HelloWorld",
targetNamespace="http://com/ibm/jaxwssample/helloworld/", portName="HelloWorld")
public class HelloWorldImpl implements HelloWorld {

	 public String helloWorld(String name) {
		  return "Hello world from " + name;
	 }

}
