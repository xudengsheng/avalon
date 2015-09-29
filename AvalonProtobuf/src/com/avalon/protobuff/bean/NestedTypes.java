package com.avalon.protobuff.bean;

public enum NestedTypes
{
	REQUIRED, OPTIONAL, REPEATED;
	
	public static NestedTypes getNestedTypes(String string){
		switch (string)
		{
		case "required":
			return REQUIRED;
		case "optional":
			return OPTIONAL;
		case "repeated":
			return REPEATED;
		default:
			throw new RuntimeException("Unkown type");
		}
	}
}
