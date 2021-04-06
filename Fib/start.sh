# stop some processes gracefully
u1sdtool --quit
dropbox stop

# kill processes
bash kill_process.sh firefox
bash kill_process.sh skype
bash kill_process.sh update-notifier
bash kill_process.sh dropbox
bash kill_process.sh u1sdtool
bash kill_process.sh u1syncdaemon
bash kill_process.sh ruby
bash kill_process.sh java
bash kill_process.sh top
bash kill_process.sh ssh

# kill services
bash kill_service.sh lightdm
bash kill_service.sh pulseaudio
bash kill_service.sh cron
bash kill_service.sh samba
bash kill_service.sh cups
bash kill_service.sh cups-browsed
bash kill_service.sh bluetooth
bash kill_service.sh avahi-daemon

# kill any remote connections
bash kill_process.sh sshd
bash kill_service.sh ssh
bash kill_service.sh network-manager
bash kill_service.sh networking

sleep 10
