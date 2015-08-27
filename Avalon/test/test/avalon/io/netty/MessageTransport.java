package test.avalon.io.netty;

public interface MessageTransport {

	public void handleMessage(Object message);
	
	public void setMessageTransport(MessageTransport messageTransport);
}
