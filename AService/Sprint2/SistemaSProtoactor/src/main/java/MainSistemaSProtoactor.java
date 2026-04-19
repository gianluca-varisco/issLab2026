package main.java;

import main.java.actors.SistemaSAsProtoactor;
import main.java.adapter.SistemaSJavalinBetter;
import main.java.protoactor26.AbstractProtoactor26;
import main.java.protoactor26.ProtoActorContext26;
import main.java.protoactor26.ProtoActorContext26Mqtt;
import unibo.basicomm23.utils.CommUtils;

public class MainSistemaSProtoactor {
	public static void main(String[] args) {
		// creo il contesto
    	ProtoActorContext26 ctx  = new ProtoActorContext26("ctx8081",8081);
    	
    	// creo l'attore (che viene registrato nel contesto)
    	AbstractProtoactor26 sistems = new SistemaSAsProtoactor("sistems",ctx);
    	
    	// attivo il server e faccio partire Javalin
    	SistemaSJavalinBetter server = new SistemaSJavalinBetter(ctx);
        server.configureTheSystem();
        
        // attivo anche su MQTT
        ProtoActorContext26Mqtt mqttCtx = new ProtoActorContext26Mqtt("mqttCtx", "unibo/sistemaSIn", "answ_eval_caller");
        new SistemaSAsProtoactor("sistems", mqttCtx); 
        
        CommUtils.outgreen("SISTEMA AVVIATO: pronto su HTTP, WS e MQTT");
     }
}
