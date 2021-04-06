killed_services_file_name=".killed_services"

while read -r service_name; do
	echo "starting $service_name service"
	sudo service "$service_name" start
done <<< "$(sudo tac $killed_services_file_name)"

sudo rm "$killed_services_file_name"

sleep 30

