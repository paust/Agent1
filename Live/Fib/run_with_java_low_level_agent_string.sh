java -javaagent:../ParameterTrackingAgent/ParameterTrackingAgent.jar=java.lang.String.substring -Dtransformer-type=low -cp bin Fib 10
