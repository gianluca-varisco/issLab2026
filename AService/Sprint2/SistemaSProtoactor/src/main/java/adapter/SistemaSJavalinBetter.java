package main.java.adapter; 
import java.util.Map;
import org.json.simple.JSONObject;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.websocket.WsMessageContext;
import main.java.protoactor26.ProtoActorContextInterface;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.utils.CommUtils;
 

public class SistemaSJavalinBetter {

	private Javalin app = null;
	private ProtoActorContextInterface context;
    private final String destActor = "sistems"; // Il nome dato nel Main

    public SistemaSJavalinBetter(ProtoActorContextInterface ctx) {
        this.context = ctx;
    }
    
//	protected void setUpServer( boolean forWS ) {
//		CommUtils.outmagenta("setUpServer forWS=" + forWS );
//		if (forWS ) {
//			if( app == null ) app = Javalin.create(config -> {
//	            config.staticFiles.add("/main/resources"); 
//		}else { //forHTTP			
//			if (app == null) {
//	            app = Javalin.create(config -> {
//	                config.staticFiles.add("/main/resources"); 
//	                config.bundledPlugins.enableCors(cors -> {
//	                    cors.addRule(it -> it.anyHost());
//	                });
//	            }).start(8080);
//			}else {
//				CommUtils.outmagenta("Server già avviato. Configuro per CORS ");
//	    		app.before(ctx -> {
//	    		    ctx.header("Access-Control-Allow-Origin", "*"); // Permette a TUTTI (o metti il tuo dominio)
//	    		    ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//	    		    ctx.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
//	    		    ctx.header("Access-Control-Allow-Credentials", "true");
//	    		});
//	
//	    		// Gestisce le richieste OPTIONS (Preflight)
//	    		app.options("/*", ctx -> {
//	    		    ctx.status(204); // No Content - conferma che il server accetta la chiamata
//	    		});
//			}//app != null
//		}
// }
    
    protected void setUpServer(boolean forWS) {
        // CREAZIONE (Solo se non esiste già)
        if (app == null) {
            CommUtils.outmagenta("Avvio istanza Javalin sulla porta 8080...");
            app = Javalin.create(config -> {
                config.staticFiles.add("/main/resources");
                // Abilitiamo CORS una volta per tutte qui
                config.bundledPlugins.enableCors(cors -> {
                    cors.addRule(it -> it.anyHost());
                });
            }).start(8080);
        }

        //  CONFIGURAZIONE AGGIUNTIVA (Sempre eseguita)
        if (forWS) {
            CommUtils.outmagenta("Configurazione WebSocket aggiunta.");
            // Qui non serve fare nulla di speciale, le rotte WS 
            // verranno definite poi in setWorkWS usando l'oggetto 'app' esistente
        } else {
            CommUtils.outmagenta("Configurazione HTTP aggiunta.");
            // Aggiungiamo gli header per CORS se non bastasse il plugin
            app.before(ctx -> {
                ctx.header("Access-Control-Allow-Origin", "*");
            });
        }
    }
		
 

	
 /* 
  * -------------------------------------------------
  * PARTE HTTP  - Stile funzionale
  * -------------------------------------------------
  */
 
    protected double readInputHTTP(JSONObject b) throws NumberFormatException{
        String xs = ""+b.get("x");
        double x  = Double.parseDouble(xs);
        CommUtils.outblue("x="+x  );
        return x;
    }
    
    protected String handlerHTTP(Context ctx) {
   	 //See https://javalin.io/documentation#context
        try {
        	JSONObject m  = CommUtils.parseForJson(ctx.body());
        	CommUtils.outblue("m="+m  );
        	String xVal = String.valueOf(m.get("x"));
        	
        	//creo la richiesta e attendo la risposta
        	IApplMessage req = CommUtils.buildRequest("webserver", "eval", xVal, destActor);
        	IApplMessage reply = context.elabMsg(req); 
        	
            return "risultato HTTP="+reply.msgContent();     
        } catch (NumberFormatException e) {
           return "Errore HTTP: numero non valido";
        }
    }
    
    protected void setWorkHTTP( ) {
    	setUpServer( false ); //per HTTP
        
        app.get("/eval", ctx -> {
	          String xVal = ctx.queryParam("x");
	          //creo la richiesta e attendo la risposta
	          IApplMessage req = CommUtils.buildRequest("webserver", "eval", xVal, destActor);
	          IApplMessage reply = context.elabMsg(req); 
	          
              ctx.json(Map.of("fullUrl", ctx.fullUrl(), "result", reply.msgContent()));
	          });
              
        app.post("/evaluate", ctx -> {  //Warning: check CORS
    	  		 String result = handlerHTTP( ctx );
       	         //Invia risposta in JSON
                 ctx.json(Map.of("fullUrl", ctx.fullUrl(), "body", ctx.body(), "result", result));
       });
    }
 
/* 
 * -------------------------------------------------
 * PARTE WS  - Stile funzionale
 * -------------------------------------------------
*/


    protected double readInputWS(String message) throws NumberFormatException{
    	System.out.println("Messaggio ricevuto su WS: " + message);
        double x = Double.parseDouble(message);   
    	return x;
    }
    
    protected double readInputApplMessageWS(String msjson) throws NumberFormatException{
    	System.out.println("ApplMessage ricevuto: " + msjson);
    	IApplMessage m = ApplMessage.cvtJson( msjson );
    	double x = Double.parseDouble(m.msgContent());
    	return x;
    }

    
    protected String handlerWS(WsMessageContext ctx) {
        try {
        	String m       = ctx.message();     
        	CommUtils.outmagenta(	"handlerWS m="+m  );
        	double x       = readInputWS(m);
        	String xVal = String.valueOf(x);
        	
        	//creo la richiesta e attendo la risposta
        	IApplMessage req = CommUtils.buildRequest("webserver", "eval", xVal, destActor);
        	IApplMessage reply = context.elabMsg(req);
        	
            return "risultato WS="+reply.msgContent() + " per x="+x;  
        } catch (NumberFormatException e) {
           return "Errore WS: numero non valido";
        }
    }
  
    protected void setWorkWS( ) {
       	/*2*/ setUpServer( true ); //per WS


        /*3*/ app.ws("/eval", 
        /*4*/  ws -> {  //ws di tipo `io.javalin.websocket.WsConfig`
        /*5*/   ws.onConnect( ctx -> {  //ctx di tipo `io.javalin.websocket.WsConnectContext`
        			//Thread.sleep(1000); //SOLO PER TEST!
        			String ss = "{\"msg\" : \"welcome\" }";
        			CommUtils.outgreen("server: send msg proactive .... ");
        			ctx.send( ss  ); //Invia messaggio di benvenuto  
					CommUtils.outgreen("server: connection established");
				});
        /*6*/   ws.onMessage(ctx -> { //ctx di tipo `io.javalin.websocket.WsMessageContext`
	                String message = ctx.message();
 	                String answer  = handlerWS( ctx );
 	                CommUtils.outcyan("server invia risposta: " + answer);
	    /*7*/   ctx.send( answer );
              });
        /*8*/ ws.onClose(ctx -> { //ctx di tipo `io.javalin.websocket.WsCloseContext`
        	   System.out.println("server: connection closed"); 
        	  } );
            });
     }

/* ----------------------------------------------------
* Attivo sia pa rate WS che la parte HTTP
* ----------------------------------------------------
*/    
    public void configureTheSystem() {   
    	setWorkWS( );   
    	setWorkHTTP();
        CommUtils.outblue("server avviato su ws://localhost:8080/eval e su HTTP");
    }
}//SistemaSJavalinBetter


 
