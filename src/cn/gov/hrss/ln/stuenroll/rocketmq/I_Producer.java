package cn.gov.hrss.ln.stuenroll.rocketmq;

import com.alibaba.rocketmq.common.message.Message;

public interface I_Producer {
	
	public void start() throws Exception;

	public void sendMessage(Message message) throws Exception;

	public void stop();
}
