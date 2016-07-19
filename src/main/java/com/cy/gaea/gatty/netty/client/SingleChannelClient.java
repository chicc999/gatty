package com.cy.gaea.gatty.netty.client;

import com.cy.gaea.gatty.exception.ConnectTimeoutException;
import com.cy.gaea.gatty.netty.NettyAbstract;
import com.cy.gaea.gatty.netty.config.NettyClientConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;

/**
 * Created by cy on 2016/7/12.
 *
 * 单连接客户端
 */
public class SingleChannelClient extends NettyAbstract {

	Logger logger = LoggerFactory.getLogger(SingleChannelClient.class);

	// Netty客户端启动器
	protected Bootstrap bootstrap;

	public SingleChannelClient(NettyClientConfig config) {
		super(config);
	}

	public SingleChannelClient(NettyClientConfig config, ExecutorService serviceExecutor,
							   EventLoopGroup ioLoopGroup) {
		super(config,serviceExecutor,ioLoopGroup);
	}


	/**
	 * 创建连接，阻塞直到成功或失败
	 *
	 * @param address 地址
	 * @throws IOException
	 */
	public Channel createChannel(String address) throws IOException {
		return createChannel(address, 0);
	}

	/**
	 * 创建连接，阻塞直到成功或失败
	 *
	 * @param address           地址
	 * @param connectionTimeout 连接超时
	 * @throws IOException
	 */
	public Channel createChannel(String address, long connectionTimeout) throws IOException {
		if (address == null || address.isEmpty()) {
			throw new IllegalArgumentException("address must not be empty!");
		}
		String[] parts = address.split("[._:]");
		if (parts.length < 5) {
			throw new ConnectTimeoutException(address);
		}
		int port;
		try {
			port = Integer.parseInt(parts[parts.length - 1]);
		} catch (NumberFormatException e) {
			throw new ConnectTimeoutException(address);
		}
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < parts.length - 1; i++) {
			if (i > 0) {
				builder.append('.');
			}
			builder.append(parts[i]);
		}
		String ip = builder.toString();
		try {
			return createChannel(new InetSocketAddress(InetAddress.getByName(ip), port), connectionTimeout);
		} catch (ConnectTimeoutException e) {
			throw e;
		} catch (UnknownHostException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			throw new SocketException("Connect "+address+" causes unKnow exception");
		}
	}

	/**
	 * 创建连接，阻塞直到成功或失败
	 *
	 * @param address 地址
	 * @throws IOException
	 */
	public Channel createChannel(SocketAddress address) throws IOException {
		return createChannel(address, 0);
	}

	/**
	 * 创建连接
	 *
	 * @param address           地址
	 * @param connectionTimeout 连接超时
	 * @throws ConnectTimeoutException
	 */
	public Channel createChannel(SocketAddress address, long connectionTimeout) throws IOException {
		if (address == null) {
			throw new IllegalArgumentException("address must not be null!");
		}
		InetSocketAddress isa = (InetSocketAddress) address;
		String ip = isa.getAddress().getHostAddress() + ":" + isa.getPort();
		ChannelFuture channelFuture;
		Channel channel = null;
		try {
			if (connectionTimeout <= 0) {
				connectionTimeout = ((NettyClientConfig) config).getConnectionTimeout();
			}

			channelFuture = this.bootstrap.connect(address);
			if (!channelFuture.await(connectionTimeout)) {
				throw new ConnectTimeoutException( ip);
			}
			channel = channelFuture.channel();
			if (channel == null || !channel.isActive()) {
				throw new SocketException("Connect "+address+" causes unKnow exception");
			}
		} catch (InterruptedException ignored) {
		}
		return channel;
	}


	/**
	 * 启动前
	 *
	 * @throws Exception
	 */
	protected void beforeStart() throws Exception {

	}

	/**
	 * 启动
	 *
	 * @throws Exception
	 */
	protected void doStart() throws Exception  {

	}

	/**
	 * 启动后
	 *
	 * @throws Exception
	 */
	protected void afterStart() throws Exception  {

	}

	/**
	 * 停止前
	 */
	protected void beforeStop()  {

	}

	/**
	 * 停止
	 */
	protected void doStop()  {

	}

	/**
	 * 停止后
	 */
	@Override
	protected void afterStop()  {

	}
}
