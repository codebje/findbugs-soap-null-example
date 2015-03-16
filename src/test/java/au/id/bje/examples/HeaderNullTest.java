package au.id.bje.examples;

import com.example.wsdl.helloservice_wsdl.HelloPortType;
import com.example.wsdl.helloservice_wsdl.HelloService;
import org.junit.Test;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Demonstrate null value for getSOAPHeader()
 */
public class HeaderNullTest {
    @Test
    public void confirmNullHeader() {
        HelloService helloService = new HelloService();
        HelloPortType helloPort = helloService.getHelloPort();

        Binding binding = ((BindingProvider) helloPort).getBinding();
        List<Handler> handlers = binding.getHandlerChain();
        handlers.add(new SOAPHandler() {
                         @Override
                         public Set<QName> getHeaders() {
                             return new HashSet<QName>();
                         }

                         @Override
                         public boolean handleMessage(MessageContext context) {
                             try {
                                 org.junit.Assert.assertNull("getHeader()",
                                         ((SOAPMessageContext) context).getMessage().getSOAPHeader());
                             } catch (SOAPException ex) {
                                 throw new RuntimeException(ex);
                             }
                             return false; // prevent the message actually being sent
                         }

                         @Override
                         public boolean handleFault(MessageContext context) {
                             return true;
                         }

                         @Override
                         public void close(MessageContext context) {

                         }
                     });
        binding.setHandlerChain(handlers);

        helloPort.sayHello("Joe");
    }
}
