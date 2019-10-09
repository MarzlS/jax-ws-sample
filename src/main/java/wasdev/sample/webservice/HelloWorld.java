package wasdev.sample.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(name = "HelloWorld", targetNamespace = "http://com/ibm/jaxwssample/helloworld/")
public interface HelloWorld {

	 @WebMethod public String helloWorld(String name);
	 
}
