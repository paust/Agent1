javac -cp javassist.jar -d bin -sourcepath src src/at/jku/ssw/mevss/jpmb/parametertracking/*.java
jar cfm ParameterTrackingAgent.jar manifest -C bin at
