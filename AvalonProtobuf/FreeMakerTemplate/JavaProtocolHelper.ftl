package ${package};

<#list FieldSeqence as item>
import ${item.java_package}.${item.namePix}.${item.java_class_name};
import ${item.java_package}.${protoName}Pro.${item.proto_class_name};
</#list>
import com.google.protobuf.InvalidProtocolBufferException;

public class ${protoName}CodecHelper {

	<#list FieldSeqence as item>
	public static ${item.java_class_name} decode${item.java_class_name}(byte[] message) throws InvalidProtocolBufferException
	{
		${item.java_class_name} bean = new  ${item.java_class_name}();
		${item.proto_class_name} bytesToProtocol = bean.bytesToProtocol(message);
		bean.protocolToJavaBean(bytesToProtocol);
		return bean;
	}

	public static ${item.proto_class_name} ecode${item.java_class_name}( ${item.java_class_name} bean)
	{
		${item.proto_class_name} bytesToProtocol = bean.javaBeanToProtocol();
		return bytesToProtocol;
	}
	</#list>
}
