package com.cy.gaea.gatty.protocol;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Created by cy on 2016/7/10.
 */
public abstract class Command {
	private  String requestId;

	public Command() {
		this.requestId = UUID.randomUUID().toString();
	}

	public final String getRequestId() {
		return requestId;
	}

	abstract ByteBuffer encode();
}
