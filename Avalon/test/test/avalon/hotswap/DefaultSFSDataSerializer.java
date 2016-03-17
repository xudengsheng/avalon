package test.avalon.hotswap;


public class DefaultSFSDataSerializer implements ISFSDataSerializer{

	private static DefaultSFSDataSerializer instance = new DefaultSFSDataSerializer();
	
	public static DefaultSFSDataSerializer getInstance() {
		return instance;
	}
	
	@Override
	public void sayHello() {
		System.out.println("Hello World");
	}

}
