service_name=$1

killed_services_file_name=".killed_services"

if [ "$(sudo service $service_name status | grep running | wc -l)" -eq 0 ]
then
	echo "no $service_name service running"
	exit 0
fi

sudo echo "$service_name" >> "$killed_services_file_name"

echo "stopping $service_name"
sudo service "$service_name" stop
if [ $? -eq 0 ]
then
	exit 0
fi

echo "stopping $service_name (as upstart job)"
sudo stop "$service_name"
if [ $? -eq 0 ]
then
	exit 0
fi

echo "stopping $service_name (as process)"
sudo bash kill_process.sh "$service_name"
if [ $? -eq 0 ]
then
	exit 0
fi

echo "stopping $service_name (legacy)"
sudo "/etc/init.d/$service_name" stop
if [ $? -eq 0 ]
then
	exit 0
fi

echo "failed to stop $service_name"
exit 1

