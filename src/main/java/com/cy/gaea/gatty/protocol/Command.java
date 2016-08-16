package com.cy.gaea.gatty.protocol;

import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by cy on 2016/7/10.
 */
public abstract class Command {
	private  String requestId;
	private ConcurrentHashMap<String,String> header = new ConcurrentHashMap<String, String>();

	public Command() {
		this.requestId = UUID.randomUUID().toString();
	}

	public final String getRequestId() {
		return requestId;
	}

	public final void setHeader(String key,String val){
		this.header.put(key,val);
	}

	abstract ByteBuffer encode();
}
