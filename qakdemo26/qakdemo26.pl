%====================================================================================
% qakdemo26 description   
%====================================================================================
dispatch( msg1, msg1(ARG) ). %da sender a receiver
%====================================================================================
context(ctxqakdemo26, "localhost",  "TCP", "8010").
 qactor( receiver, ctxqakdemo26, "it.unibo.receiver.Receiver").
 static(receiver).
  qactor( sender, ctxqakdemo26, "it.unibo.sender.Sender").
 static(sender).
