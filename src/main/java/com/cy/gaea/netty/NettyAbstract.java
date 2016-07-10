package com.cy.gaea.netty;

import com.cy.gaea.exception.RemotingIOException;
import com.cy.gaea.protocol.Command;
import io.netty.channel.Channel;

/**
 * Created by cy on 2016/7/10.
 */
public abstract class NettyAbstract implements Transport{
	@Override
	public Command sync(Channel channel, Command command) throws RemotingIOException {
		return null;
	}

	@Override
	public Command sync(Channel channel, Command command, int timeout) throws RemotingIOException {
		return null;
	}

	@Override
	public ResponseFurture async(Channel channel, Command command, CommandCallback callback) throws RemotingIOException {
		return null;
	}
}
