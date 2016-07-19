package com.cy.gaea.gatty.netty;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by cy on 2016/7/19.
 */
abstract class Service implements LifeCycle{

	// 是否启动
	protected final AtomicBoolean started = new AtomicBoolean(false);

	// 服务状态
	protected final AtomicReference<ServiceState> serviceState = new AtomicReference<ServiceState>();

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
	protected void doStart() throws Exception {

	}

	/**
	 * 启动后
	 *
	 * @throws Exception
	 */
	protected void afterStart() throws Exception {

	}

	/**
	 * 停止前
	 */
	protected void beforeStop() {

	}

	/**
	 * 停止
	 */
	protected void doStop() {

	}

	/**
	 * 停止后
	 */
	protected void afterStop() {

	}
}
