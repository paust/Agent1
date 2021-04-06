tmp_file=.tmp_result

result_file_prefix=./overheads/overhead_
result_file_postfix=.csv

bash start.sh

for n in $(seq 10 50)
do
	echo "Running overhead measurements with n = $n"
	result_file="$result_file_prefix$n$result_file_postfix"
	echo "No Agent;Native Agent;Java Low-level Agent;Java High-level Agent;" > $result_file
	for iteration in $(seq 1 100)
	do
		echo "Starting iteration $iteration" &&
		java -cp bin Suite $n $tmp_file > /dev/null 2> /dev/null &&
		cat $tmp_file >> $result_file && echo -n ";" >> $result_file && rm $tmp_file &&
		java -agentpath:../ParameterTrackingAgent/parameter_tracking_agent.so=clazz=Fib:method=fib:param=I:ret:J -cp bin Suite $n $tmp_file > /dev/null 2> /dev/null &&
		cat $tmp_file >> $result_file && echo -n ";" >> $result_file && rm $tmp_file &&
		java -javaagent:../ParameterTrackingAgent/ParameterTrackingAgent.jar=Fib.fib -Dtransformer-type=low -cp bin Suite $n $tmp_file > /dev/null 2> /dev/null &&
		cat $tmp_file >> $result_file && echo -n ";" >> $result_file && rm $tmp_file &&
		java -javaagent:../ParameterTrackingAgent/ParameterTrackingAgent.jar=Fib.fib -Dtransformer-type=high -cp bin Suite $n $tmp_file > /dev/null 2> /dev/null &&
		cat $tmp_file >> $result_file && echo -n ";" >> $result_file && rm $tmp_file &&
		echo "" >> $result_file
	done
	bash end.sh
	dropbox start
	sleep $((60 * 5))	
	bash start.sh
done

bash end.sh

