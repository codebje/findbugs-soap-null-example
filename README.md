The SOAPMessage.getSOAPHeader() method can return null if the underlying SOAP message has had its header deleted.
With Sun JDK 1.7, a literal null is returned, while 1.8 will return a proxy wrapping the null.
