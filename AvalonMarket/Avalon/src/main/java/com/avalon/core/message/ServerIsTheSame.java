package com.avalon.core.message;

import akka.cluster.Member;

public class ServerIsTheSame extends AvaloneMessage
{
	public final String UUID;

	public final int type;

	public final Member member;

	public ServerIsTheSame(String uUID, int type, Member member)
	{
		super(MessageType.ServerIsTheSame);
		UUID = uUID;
		this.type = type;
		this.member = member;
	}

}
