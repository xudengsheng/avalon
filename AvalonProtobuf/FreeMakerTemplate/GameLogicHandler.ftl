package ${package}.${handler}.${fileName};


import ${package}.${fileName}${protoName}.${java_class_name};
import ${package}.${helpPix}.${fileName}${helperpfix};
import ${package}.${namePix}.${java_class_name}${beanpfix};

import com.avalon.protobuff.JavaProtocolTransform;
import com.avalon.api.internal.IoMessagePackage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.zero.example.core.AbstractClientRequestHandler;

public class ${java_class_name}Handler extends AbstractClientRequestHandler {

	@Override
	public JavaProtocolTransform decode(Object message) throws InvalidProtocolBufferException {
		${java_class_name}${beanpfix} decodeBean = ${fileName}CodecHelper.decode${java_class_name}${beanpfix}((byte[]) message);
		return decodeBean;
	}

	@Override
	public boolean verifyParams(JavaProtocolTransform javaBean) {
		${java_class_name}${beanpfix} decodeBean = (${java_class_name}${beanpfix}) javaBean;
		//需要校验的数据在这里
		return true;
	}

	@Override
	public IoMessagePackage handleClientRequest(Object listener, JavaProtocolTransform message) {
		${java_class_name}${beanpfix} decodeBean = (${java_class_name}${beanpfix}) message;
		// TODO Auto-generated method stub
		return null;
	}

}
