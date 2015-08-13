package com.app.empire.protocol.data.player;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class PlayerButtonInfo extends AbstractData {
	private int[] buttonId;
	private byte[] buttonType;
	private int[] buttonSort;
	private boolean[] isHighlight;

	public PlayerButtonInfo(int sessionId, int serial) {
		super(Protocol.MAIN_PLAYER, Protocol.PLAYER_PlayerButtonInfo, sessionId, serial);
	}

	public PlayerButtonInfo() {
		super(Protocol.MAIN_PLAYER, Protocol.PLAYER_PlayerButtonInfo);
	}

	public int[] getButtonId() {
		return buttonId;
	}

	public void setButtonId(int[] buttonId) {
		this.buttonId = buttonId;
	}

	public byte[] getButtonType() {
		return buttonType;
	}

	public void setButtonType(byte[] buttonType) {
		this.buttonType = buttonType;
	}

	public int[] getButtonSort() {
		return buttonSort;
	}

	public void setButtonSort(int[] buttonSort) {
		this.buttonSort = buttonSort;
	}

	public boolean[] getIsHighlight() {
		return isHighlight;
	}

	public void setIsHighlight(boolean[] isHighlight) {
		this.isHighlight = isHighlight;
	}
}
