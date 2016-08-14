package cn.gov.hrss.ln.stuenroll.rocketmq;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;

public class Producer implements I_Producer {

	private String namesrvAddr;
	private String groupName;
	private String instanceName;

	private DefaultMQProducer producer;

	@Override
	public void start() throws Exception {
		producer = new DefaultMQProducer();
		producer.setNamesrvAddr(namesrvAddr);
		producer.setProducerGroup(groupName);
		producer.setInstanceName(instanceName);
		producer.start();
	}

	@Override
	public void sendMessage(Message message) throws Exception {
		producer.send(message);

	}

	@Override
	public void stop() {
		producer.shutdown();

	}

	public String getNamesrvAddr() {
		return namesrvAddr;
	}

	public void setNamesrvAddr(String namesrvAddr) {
		this.namesrvAddr = namesrvAddr;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	
	

}
