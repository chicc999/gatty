package com.cy.gaea.gatty.netty;

import com.cy.gaea.gatty.netty.config.NettyClientConfig;

/**
 * Created by cy on 2016/7/10.
 */
public class ChannelPoolClient extends NettyAbstract {

	public ChannelPoolClient(NettyClientConfig config) {
		super(config);
	}

	@Override
	public void start() throws Exception {

	}

	@Override
	public void stop() {

	}

	@Override
	public boolean isStarted() {
		return false;
	}
}
