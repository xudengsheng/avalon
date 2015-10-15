package ${java_package}.${namePix};

import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.avalon.protobuff.JavaProtocolTransform;
import ${java_package}.${java_outer_classname}.*;
import ${java_package}.${java_outer_classname}.${protoName};
import ${java_package}.${java_outer_classname}.${protoName}.Builder;
<#if needcollection>
import java.util.List;
import java.util.ArrayList;
</#if>
<#list importFiel as file>
import ${file};
</#list>


public class ${java_class_name} implements JavaProtocolTransform {

	<#list FieldSeqence as item>
	<#if item.collection>
	<#if item.primitive>
	private List<${item.javaFielType}> ${item.lowerName};
	<#else>
	private List<${item.javaBeanName}> ${item.lowerName};
	</#if>
	<#else>
	<#if item.primitive>
	private ${item.javaFielType} ${item.lowerName};
	<#else>
	private ${item.javaBeanName} ${item.lowerName};
	</#if>
	</#if>
	</#list>
	
	<#list FieldSeqence as item>
	<#if item.collection>
		<#if item.primitive>
	public List<${item.javaFielType}> get${item.upperName}()
	{
		return 	${item.lowerName};
	}

	public void set${item.upperName}(List<${item.javaFielType}> ${item.lowerName}) {
		this.${item.lowerName} = ${item.lowerName};
	}	
		<#else>
	public List<${item.javaBeanName}> get${item.upperName}()
	{
		return 	${item.lowerName};
	}

	public void set${item.upperName}(List<${item.javaBeanName}> ${item.lowerName}) {
		this.${item.lowerName} = ${item.lowerName};
	}	
		</#if>
	<#else>
	<#if item.primitive>
	public ${item.javaFielType} get${item.upperName}()
	{
		return 	${item.lowerName};
	}

	public void set${item.upperName}(${item.javaFielType} ${item.lowerName}) {
		this.${item.lowerName} = ${item.lowerName};
	}	
	<#else>
	
	public ${item.javaBeanName} get${item.upperName}()
	{
		return 	${item.lowerName};
	}

	public void set${item.upperName}(${item.javaBeanName} ${item.lowerName}) {
		this.${item.lowerName} = ${item.lowerName};
	}	
	
	</#if>
	</#if>
	</#list>
	
	@Override
	public void protocolToJavaBean(Message message) {
		${protoName} protocal = (${protoName}) message;
		<#list FieldSeqence as item>
		<#if item.collection>
		{	
			<#if item.primitive>
		List<${item.javaFielType}> list = new ArrayList<${item.javaFielType}>();
		for (${item.javaFielType} JavaBean : protocal.get${item.upperName}List()) {
			list.add(JavaBean);
		}
		this.set${item.upperName}(list);
			<#else>
		List<${item.javaBeanName}> list = new ArrayList<${item.javaBeanName}>();
		for (${item.javaFielType} JavaBean : protocal.get${item.upperName}List()) {
			${item.javaBeanName} protocol = new ${item.javaBeanName}();
			protocol.protocolToJavaBean(JavaBean);
			list.add(protocol);
		}
		this.set${item.upperName}(list);
			</#if>
		}
		<#else>
		<#if item.primitive>
		this.set${item.upperName}(protocal.get${item.upperName}());
		<#else>
		{
			${item.javaBeanName} protocol = new ${item.javaBeanName}();
			protocol.protocolToJavaBean(protocal.get${item.upperName}());
			this.set${item.upperName}(protocol);
		}
		</#if>
		</#if>
		</#list>
	}

	@Override
	public ${protoName} javaBeanToProtocol() {
		Builder newBuilder = ${protoName}.newBuilder();
		<#list FieldSeqence as item>
		<#if item.collection>
		{
			<#if item.primitive>
			List<${item.javaFielType}> list = new ArrayList<${item.javaFielType}>();
			for (${item.javaFielType} JavaBean : this.get${item.upperName}()) {
				list.add(JavaBean);
			}
			newBuilder.addAll${item.upperName}(list);
			<#else>
			List<${item.javaFielType}> list = new ArrayList<${item.javaFielType}>();
			for (${item.javaBeanName} JavaBean : this.get${item.upperName}()) {
			list.add((${item.javaFielType}) JavaBean.javaBeanToProtocol());
			}
			newBuilder.addAll${item.upperName}(list);
			</#if>
		}
		<#else>
		<#if item.primitive>
		newBuilder.set${item.upperName}(this.get${item.upperName}());
		<#else>
		{
			${item.javaFielType} protocol = this.get${item.upperName}().javaBeanToProtocol();
			newBuilder.set${item.upperName}(protocol);
		}
		</#if>
		</#if>
		</#list>
		return newBuilder.build();
	}

	@Override
	public byte[] getByteArray() {
		return javaBeanToProtocol().toByteArray();
	}

	@Override
	public ${protoName} bytesToProtocol(byte[] bytes) throws InvalidProtocolBufferException {
		return ${protoName}.parseFrom(bytes);
	}
}