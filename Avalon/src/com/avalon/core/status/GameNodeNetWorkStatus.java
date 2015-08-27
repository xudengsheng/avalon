package com.avalon.core.status;
/**
 *  网络状态
 * @author ZERO
 *
 */
public enum GameNodeNetWorkStatus {
	//联通，阻塞
	UNICOM(1), BLOCK(0);

	private GameNodeNetWorkStatus(int state) {
		State = state;
	}

	public final int State;
}
