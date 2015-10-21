package com.zero.example.map;

import akka.actor.UntypedActor;

public class MapBlock extends UntypedActor {

	private final int x;

	private final int y;

	public MapBlock(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	public void onReceive(Object arg0) throws Exception {
		// TODO Auto-generated method stub

	}

}
