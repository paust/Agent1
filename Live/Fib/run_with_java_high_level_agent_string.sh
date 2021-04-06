java -javaagent:../ParameterTrackingAgent/ParameterTrackingAgent.jar=java.lang.String.substring -Dtransformer-type=high -cp bin Fib 10
