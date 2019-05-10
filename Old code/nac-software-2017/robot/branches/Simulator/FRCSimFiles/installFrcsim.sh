mkdir workspace
mv GearsBot_CmdDemo workspace
sudo cp debFiles/* /var/cache/apt/archives/.
sudo dpkg -i /var/cache/apt/archives/*.deb
sudo apt-get install -f
tar -xf eclipse-java-neon-2-linux-gtk-x86_64.tar.gz
sudo mv eclipse /opt
sudo ln -s /opt/eclipse/eclipse /usr/bin/eclipse
mkdir ~/wpilib
mkdir ~/wpilib/simulation2017
unzip simulation-2017.1.1.zip -d ~/wpilib/simulation2017
cp models.zip ~/wpilib/simulation2017/
cd ~/wpilib/simulation2017/gz_msgs
mkdir build
cd build
cmake ..
make install
cd ~/wpilib/simulation2017/
unzip models.zip
mv  frcsim-gazebo-models-4/models ~/wpilib/simulation2017/
mv  frcsim-gazebo-models-4/worlds ~/wpilib/simulation2017/
mkdir ~/wpilib/simulation
cd ~/
unzip simulation-1.0.0.zip -d ~/wpilib/simulation
cp models.zip ~/wpilib/simulation/
cd ~/wpilib/simulation/gz_msgs
mkdir build
cd build
cmake ..
make install
cd ~/wpilib/simulation/
unzip models.zip
mv  frcsim-gazebo-models-4/models ~/wpilib/simulation/
mv  frcsim-gazebo-models-4/worlds ~/wpilib/simulation/
sudo ln -s ~/wpilib/simulation/frcsim /usr/bin/frcsim
sudo ln -s ~/wpilib/simulation/sim_ds /usr/bin/sim_ds
sudo mv ~/wpilib/simulation/frcsim.desktop /usr/share/applications
sudo mv ~/wpilib/simulation/sim_ds.desktop /usr/share/applications
sudo mv ~/wpilib/simulation/eclipse.desktop /usr/share/applications
sudo cp ~/wpilib/simulation/sim_ds_logo.png /usr/share/icons/sim_ds/
wget -O /tmp/gazebo6_install.sh http://osrf-distributions.s3.amazonaws.com/gazebo/gazebo6_install.sh
sudo sh /tmp/gazebo6_install.sh
sudo apt-get install g++-4.9 openjdk-8-jdk
sudo apt-get install cmake libprotobuf-dev libprotoc-dev protobuf-compiler -y
sudo apt-get install libgazebo6-dev

