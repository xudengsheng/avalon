package com.avalon.core.actor;

import java.util.UUID;

import com.avalon.core.ContextResolver;
import com.avalon.core.message.GameEngineMessage.NodeInfo;
import com.avalon.setting.AvalonServerMode;

import akka.actor.UntypedActor;
/**
 * 游戏逻辑的主管理
 * @author zero
 *
 */
public class GameEngineActor extends UntypedActor {

	public static String GEUID = UUID.randomUUID().toString();

	private String selfRemotePath;

	private int selfRemoteUUID;

	@Override
	public void onReceive(Object arg0) throws Exception
	{
		if (arg0 instanceof NodeInfo)
		{
			if (((NodeInfo) arg0).GEUID.equals(GEUID))
			{
				selfRemotePath = ((NodeInfo) arg0).remotePath;
				selfRemoteUUID = ((NodeInfo) arg0).uid;
			}
		}
	}

}
