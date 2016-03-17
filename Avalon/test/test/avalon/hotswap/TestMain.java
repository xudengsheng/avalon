package test.avalon.hotswap;

import java.io.IOException;

import com.avalon.util.HotSwapClassUtil;
import com.avalon.util.PropertiesWrapper;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;

import jodd.props.Props;

public class TestMain {
	public static void main(String[] args) throws IOException, IllegalConnectorArgumentsException {
		Thread thread=new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					try {
						DefaultSFSDataSerializer.getInstance().sayHello();
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();
		
		HotSwapClassUtil hotSwapClassUtil=new HotSwapClassUtil(new PropertiesWrapper(new Props()));
		hotSwapClassUtil.reload("DefaultSFSDataSerializer.class", "test.avalon.hotswap.DefaultSFSDataSerializer");
	}
}
