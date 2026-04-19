package lifegamemock;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.mqtt.MqttSupport;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.utils.CommUtils;

public class LifeGameMock {
	private final String MqttBroker = "tcp://localhost:1883";//"tcp://broker.hivemq.com"; //
	private MqttSupport mqttSupport = new MqttSupport();
 	private String receiverIn = "lifegameIn";
	
	
	
	// STRUTTURA INIZIALE PER VEDERE SE TUTTO FUNZIONA
	
	public void doJob() {
		CommUtils.outblue("LifeGameMock | doJob");
		mqttSupport.connectToBroker("lifegamemock" ,MqttBroker );
		CommUtils.outblue("LifeGameMock | connected to broker");
		mqttSupport.subscribe ( receiverIn, (topic, mqttmsg) -> {
			//Lambda is of type org.eclipse.paho.client.mqttv3.IMqttMessageListener
			String msg            = new String( mqttmsg.getPayload() );
			IApplMessage applMesg = new ApplMessage(msg);
			CommUtils.outmagenta("lifegamemock"  + " | Riceve via listener: " + msg );
			if( applMesg.isRequest() ) {
				CommUtils.outred("lifegamemock"  + " | WARNING: unable to handle requests " + applMesg);
				System.exit(0);
			}
		});
	}
	
	public static void main(String[] args) {
		new LifeGameMock().doJob();
	}
}
