java -agentpath:../ParameterTrackingAgent/parameter_tracking_agent.so=clazz=Fib:method=fib:param=I:ret:J -cp bin Fib 10
