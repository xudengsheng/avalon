package ${package};

public interface MessageKey
{
	<#list FieldSequence as item>
	public static final int ${item.pname} = ${item.opcode};
	</#list>
}
