package main.java.caller;

import java.util.Observable;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.IObserver;
import unibo.basicomm23.mqtt.MqttInteraction;
import unibo.basicomm23.utils.CommUtils;

/**
 * Client di test per interagire con SistemaS tramite MQTT.
 * Invia una richiesta sul topic di ingresso del sistema e 
 * resta in ascolto della risposta su un proprio topic dedicato.
 */
public class CallerMqtt implements IObserver {

    private MqttInteraction mqttConn;
    private final String name = "callermqtt";
    private final String brokerAddr = "tcp://broker.hivemq.com";
    
    // Topic su cui il sistema S ascolta (TopicIn del contesto MQTT)
    private final String topicSIn  = "unibo/sistemaSIn";
    // Topic su cui il sistema S invierà la risposta (TopicOut del contesto MQTT)
    private final String topicSOut = "answ_eval_caller";

    public CallerMqtt() {
        setup();
        doJob();
    }

    protected void setup() {
        try {
            CommUtils.outblue(name + " | Connecting to broker: " + brokerAddr);
            // Crea la connessione MQTT: 
            // Inizializza il ricevitore sul topic dove il sistema S pubblica le risposte
            mqttConn = new MqttInteraction(name, brokerAddr, topicSOut, topicSIn);
            CommUtils.outblue(name + " | Connected and subscribed to " + topicSOut);
        } catch (Exception e) {
            CommUtils.outred(name + " | Setup Error: " + e.getMessage());
        }
    }

    protected void doJob() {
        try {
            // preparo la richiesta (Request) per l'attore 'sistemas'
            String content = "3.5"; // Valore x da calcolare
            IApplMessage req = CommUtils.buildRequest(name, "eval", content, "sistemas");

            CommUtils.outgreen(name + " | Sending request: " + req);
            
            // invio della richiesta tramite MQTT (Forward sul topic di input del sistema)
            mqttConn.forward(req);

            // attendo la risposta asincrona (che arriverà via MqttInteraction)
            // Nel frattempo simuliamo un'attesa per non far chiudere il programma subito
            receiveLoop();

        } catch (Exception e) {
            CommUtils.outred(name + " | Job Error: " + e.getMessage());
        }
    }

    protected void receiveLoop() {
        new Thread(() -> {
            try {
                CommUtils.outblue(name + " | Waiting for replies...");
                while (true) {
                    IApplMessage msg = mqttConn.receive();
                    if (msg != null) {
                        update(msg.toString());
                    }
                }
            } catch (Exception e) {
                CommUtils.outred(name + " | Receive Error: " + e.getMessage());
            }
        }).start();
    }

    // Metodi dell'interfaccia IObserver
    @Override
    public void update(Observable o, Object arg) {
        update(arg.toString());
    }

    @Override
    public void update(String value) {
        CommUtils.outmagenta(name + " | RICEVUTO RISULTATO: " + value);
    }

    public static void main(String[] args) {
        new CallerMqtt();
    }
}