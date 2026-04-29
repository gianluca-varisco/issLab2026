%====================================================================================
% firefly_sonar_sync description   
%====================================================================================
dispatch( cellstate, cellstate(X,Y,COLOR) ).
event( startSync, startSync(PERIOD) ).
event( stopSync, stopSync(0) ).
%====================================================================================
context(ctxfirefly, "localhost",  "TCP", "8040").
context(ctxgrid, "127.0.0.1",  "TCP", "8050").
 qactor( griddisplay, ctxgrid, "external").
  qactor( sonar, ctxfirefly, "it.unibo.sonar.Sonar").
 static(sonar).
  qactor( firefly, ctxfirefly, "it.unibo.firefly.Firefly").
dynamic(firefly). %%Oct2023 
  qactor( creator, ctxfirefly, "it.unibo.creator.Creator").
 static(creator).
