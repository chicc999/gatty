package com.cy.gaea.gatty.netty;

import com.cy.gaea.gatty.protocol.Command;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by cy on 2016/7/10.
 */
public class ResponseFuture {
	protected static Logger logger = LoggerFactory.getLogger(ResponseFuture.class);
	// 开始事件
	private final long beginTime = System.currentTimeMillis();
	// 请求
	private Command request;
	// 应答
	private Command response;
	// 异常
	private Throwable cause;
	// 超时
	private long timeout;
	// 请求ID
	private String requestId;
	// 通道
	private Channel channel;
	// 回调
	private CommandCallback callback;
	// 是否成功
	private boolean success;
	// 回调一次
	private AtomicBoolean onceCallback = new AtomicBoolean(false);
	// 是否释放
	private AtomicBoolean released = new AtomicBoolean(false);
	// 门闩
	private CountDownLatch latch;
	// 信号量
	private Semaphore semaphore;

	/**
	 * 异步调用构造函数
	 *
	 * @param channel   通道
	 * @param request   请求
	 * @param timeout   超时
	 * @param callback  异步调用回调
	 * @param semaphore 信号量
	 * @param latch     门闩
	 */
	public ResponseFuture(Channel channel, Command request, long timeout, CommandCallback callback, Semaphore semaphore,
						  CountDownLatch latch) {
		if (request == null) {
			throw new IllegalArgumentException("request can not be null");
		}
		this.channel = channel;
		this.request = request;
		this.requestId = request.getRequestId();
		this.timeout = timeout;
		this.callback = callback;
		this.semaphore = semaphore;
		this.latch = latch;
	}

	public Command getRequest() {
		return request;
	}

	public Command getResponse() {
		return this.response;
	}

	public void setResponse(Command response) {
		this.response = response;
	}

	public Throwable getCause() {
		return this.cause;
	}

	public void setCause(Throwable cause) {
		this.cause = cause;
	}

	public long getTimeout() {
		return timeout;
	}

	public String getRequestId() {
		return requestId;
	}

	public CommandCallback getCallback() {
		return this.callback;
	}

	public long getBeginTime() {
		return beginTime;
	}

	public boolean isSuccess() {
		return this.success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Channel getChannel() {
		return channel;
	}

	/**
	 * 是否超时
	 *
	 * @return
	 */
	public boolean isTimeout() {
		return System.currentTimeMillis() > beginTime + timeout;
	}

	/**
	 * 阻塞并等待返回
	 *
	 * @return 应答命令
	 * @throws InterruptedException
	 */
	public Command await() throws InterruptedException {
		if (latch != null) {
			latch.await(timeout, TimeUnit.MILLISECONDS);
		}
		return response;
	}

	/**
	 * 等待返回
	 *
	 * @param timeout 超时时间
	 * @return 应答命令
	 * @throws InterruptedException
	 */
	public Command await(long timeout) throws InterruptedException {
		if (latch != null) {
			latch.await(timeout, TimeUnit.MILLISECONDS);
		}
		return response;
	}

	/**
	 * 回调
	 */
	public void callback() {
		if (callback == null) {
			return;
		}
		if (onceCallback.compareAndSet(false, true)) {
			try {
				if (isSuccess()) {
					callback.onSuccess(request, response);
				} else if (cause != null) {
					callback.onException(request, cause);
				} else {
					logger.error("bigbug: success and exception confused! {}", request);
				}
			} catch (Throwable ignored) {
				logger.error("callback error",ignored);
			}
		}
	}

	/**
	 * 确定成功
	 */
	public void onSuccess() {
		setSuccess(true);
		callback();
	}

	/**
	 * 确定失败
	 */
	public void onFailed(Throwable cause) {
		setSuccess(false);
		setCause(cause);
		callback();
	}

	/**
	 * 释放资源，不回调
	 *
	 * @return 成功标示
	 */
	public boolean release() {
		return release(null, false);
	}

	/**
	 * 释放资源，并回调
	 *
	 * @param e        异常
	 * @param callback 是否回调
	 * @return 成功标示
	 */
	public boolean release(final Throwable e, final boolean callback) {
		// 确保释放一次
		if (released.compareAndSet(false, true)) {
			// 设置了异常，则不成功
			if (e != null) {
				success = false;
				cause = e;
			}
			// 释放请求资源
			if (request != null) {
				//request.release();
			}
			// 释放信号量
			if (semaphore != null) {
				semaphore.release();
			}
			// 唤醒同步等待线程
			if (latch != null) {
				latch.countDown();
			}
			// 回调
			if (callback) {
				this.callback();
			}
			// 清空资源引用
			request = null;
			return true;
		}
		return false;
	}
}
