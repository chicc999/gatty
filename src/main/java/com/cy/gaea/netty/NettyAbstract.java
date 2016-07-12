package com.cy.gaea.netty;

import com.cy.gaea.exception.RemotingIOException;
import com.cy.gaea.netty.config.NettyConfig;
import com.cy.gaea.protocol.Command;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;

import java.util.concurrent.ExecutorService;

/**
 * Created by cy on 2016/7/10.
 */
public abstract class NettyAbstract implements Transport {

    // 异步回调 & 业务处理执行器
    protected ExecutorService serviceExecutor;
    // 传输配置
    protected NettyConfig config;

    // IO处理线程池
    protected EventLoopGroup ioLoopGroup;



    public NettyAbstract(NettyConfig config) {
		this(config,null,null);
	}

    public NettyAbstract(NettyConfig config, ExecutorService serviceExecutor,
            EventLoopGroup ioLoopGroup) {
        this.serviceExecutor = serviceExecutor;
        this.config = config;
        this.ioLoopGroup = ioLoopGroup;
    }

    @Override
    public Command sync(Channel channel, Command command) throws RemotingIOException {
        return null;
    }

    @Override
    public Command sync(Channel channel, Command command, int timeout) throws RemotingIOException {
        return null;
    }

    @Override
    public ResponseFurture async(Channel channel, Command command, CommandCallback callback)
            throws RemotingIOException {
        return null;
    }
}
