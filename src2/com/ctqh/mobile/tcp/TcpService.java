package com.ctqh.mobile.tcp;



import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.executor.OrderedThreadPoolExecutor;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ctqh.mobile.common.threads.NamedThreadFactory;

public class TcpService {
	private final static Logger logger = LoggerFactory
			.getLogger(TcpService.class);

	private NetworkConfig networkConfig;
	
	private NioSocketAcceptor acceptor;
	private OrderedThreadPoolExecutor threadpool;
	private boolean listening=false;
	
	
	public void stopNetListen() {
		if(!listening){
			return;
		}
		if (acceptor != null) {
			//不监听
			acceptor.unbind();			
		}
		threadpool.shutdown();
		
	}
	public void destroy(){
		//关闭执行处理器
		if(!listening){
			return;
		}
		
		if (acceptor != null) {			
			//不监听
			acceptor.dispose();
		}
	}

	public void startNetListen() throws IOException {
		listening=true;
		Executor executor=Executors.newCachedThreadPool(new NamedThreadFactory("TCPioProcessor",true));
        ((ThreadPoolExecutor) executor).setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		acceptor = new NioSocketAcceptor(2,executor);			
		acceptor.setBacklog(networkConfig.acceptBacklog); //设置主服务监听端口的监听队列的最大值为5000，如果当前已经有5000个连接，再新的连接来将被服务器拒绝 
		acceptor.setReuseAddress(true);//设置每一个非主监听连接的端口可以重用   
		acceptor.setHandler(networkConfig.handler);
		
		DefaultIoFilterChainBuilder fcb = acceptor.getFilterChain();
		IoFilter protocol = new ProtocolCodecFilter(networkConfig.factory);
		fcb.addLast("codec", protocol);
		for (Iterator<Map.Entry<String, IoFilter>> iterator=networkConfig.filters.entrySet().iterator();iterator.hasNext();) {
			Map.Entry<String, IoFilter> entry=iterator.next();
			fcb.addLast(entry.getKey(), entry.getValue());
		}
		threadpool=new OrderedThreadPoolExecutor(4,16,30,TimeUnit.SECONDS,new NamedThreadFactory("mina orderedThread"));//best 16
		fcb.addLast("threadPool", new ExecutorFilter(threadpool));
		SocketSessionConfig sc = acceptor.getSessionConfig();
		sc.setReuseAddress(true);// 设置每一个非主监听连接的端口可以重用
		sc.setReceiveBufferSize(networkConfig.recieveBuffer);// 设置输入缓冲区的大小
		sc.setSendBufferSize(networkConfig.sendBuffer);// 设置输出缓冲区的大小
		sc.setTcpNoDelay(networkConfig.tcpNoDelay);// flush函数的调用 设置为非延迟发送，为true则不组装成大包发送，收到东西马上发出   
		sc.setSoLinger(0);
		sc.setIdleTime(IdleStatus.READER_IDLE, networkConfig.readIdleTimeout);
		acceptor.bind(networkConfig.address);
		logger.error("game server listen run success {}",networkConfig.address.toString());
	}

	public TcpService(NetworkConfig config) {
		networkConfig = config;
	}
	
}
