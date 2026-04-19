package main.java.actors;

import main.java.protoactor26.AbstractProtoactor26;
import main.java.protoactor26.ProtoActorContextInterface;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.utils.CommUtils;

public class SistemaSAsProtoactor extends AbstractProtoactor26{
	public SistemaSAsProtoactor(String name, ProtoActorContextInterface ctx) {
        super(name, ctx);
    }
	
	/*
	 * ------------------------------------
	 * Gestione dei messaggi
	 * ------------------------------------
	 */

    @Override
    protected IApplMessage elabRequest(IApplMessage req) {
        if( req.msgId().equals("eval")){
            double x      = Double.parseDouble(req.msgContent());
            double result = eval(x);
            IApplMessage replyMsg =
                        CommUtils.buildReply(name,req.msgId(),""+result,req.msgSender());
            return replyMsg;
        }else{
            IApplMessage replyMsg =
            CommUtils.buildReply(name,req.msgId(),
            "requestUnkown",req.msgSender());
            return replyMsg;
        }
    }

    @Override protected void elabDispatch(IApplMessage m) { 
    	 CommUtils.outblue(name + " | elabDispatch:" + m);
    }
    
    @Override protected void elabReply(IApplMessage r) { 
    	CommUtils.outblue(name + " | elabReply:" + r);
    }

    @Override protected void elabEvent(IApplMessage ev) { 
    	CommUtils.outcyan(name + " | elabEvent:" + ev);
    }
    
    /*
     * ------------------------------------
     *  Business code
     * ------------------------------------
     */   

    protected double eval(double x) {
        if (x > 4.0) {
            CommUtils.outmagenta(name + " | Simulo ritardo per x=" + x);
            CommUtils.delay(8000);
        }
        return Math.sin(x) + Math.cos(Math.sqrt(3) * x);
    }
    
    /*
     * ------------------------------------
     * Parte proattiva
     * ------------------------------------
     */
    @Override protected void proactiveJob() { 
    	
    }
}
