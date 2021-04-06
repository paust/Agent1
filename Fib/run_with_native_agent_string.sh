java -agentpath:../ParameterTrackingAgent/parameter_tracking_agent.so=clazz="java/lang/String":method="substring":param="II":ret:"Ljava/lang/String;" -cp bin Fib 10
