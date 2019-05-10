#1/bin/sh
#launcher.sh
#navigate to home directory, then to this directory, then execute python script, then back home

#cd /
#cd home/pi/Desktop
xhost +localhost &
sudo /usr/bin/python /home/pi/Desktop/Green_Detection.py >/home/pi/Desktop/annoying.out 2>/home/pi/Desktop/err.out &

