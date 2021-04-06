process=$1

if [ "$(pgrep $1 | wc -l)" -eq 0 ] 
then
	echo "no $process processes running"
	exit 0
fi

echo "killing $process"
sudo pkill "$process"
sleep 10
if [ "$(pgrep $process | wc -l)" -eq 0 ] 
then
	exit 0
fi

echo "killing $process forcefully"
sudo pkill --signal 9 "$process"
sleep 1
if [ "$(pgrep $process | wc -l)" -eq 0 ]
then
	exit 0
fi

echo "failed killing $process"
exit 1

