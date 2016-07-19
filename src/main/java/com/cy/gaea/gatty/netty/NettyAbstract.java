package com.cy.gaea.gatty.netty;

import com.cy.gaea.gatty.exception.RemotingIOException;
import com.cy.gaea.gatty.protocol.Command;
import com.cy.gaea.gatty.netty.config.NettyConfig;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;

import java.util.concurrent.ExecutorService;

/**
 * Created by cy on 2016/7/10.
 */
public abstract class NettyAbstract extends Service implements Transport  {

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

    /**
     * 启动前
     *
     * @throws Exception
     */
    @Override
    protected void beforeStart() throws Exception {

    }

    /**
     * 启动
     *
     * @throws Exception
     */
    @Override
    protected void doStart() throws Exception  {

    }

    /**
     * 启动后
     *
     * @throws Exception
     */
    @Override
    protected void afterStart() throws Exception  {

    }

    /**
     * 停止前
     */
    @Override
    protected void beforeStop()  {

    }

    /**
     * 停止
     */
    @Override
    protected void doStop()  {

    }

    /**
     * 停止后
     */
    @Override
    protected void afterStop()  {

    }
}
