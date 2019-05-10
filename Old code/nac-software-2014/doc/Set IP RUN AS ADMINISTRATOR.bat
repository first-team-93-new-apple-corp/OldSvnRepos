@echo off
set wlan_Adap_Name=Wireless Network Connection
set lan_Adap_Name=Local Area Connection
 
echo Choose: 
echo [A] Set Wireless Static IP of adapter: "%wlan_Adap_Name%"
echo [B] Set Wireless DHCP (Automatic IP) of adapter: "%wlan_Adap_Name%"
echo [C] Set LAN Static IP of adapter: "%lan_Adap_Name%"
echo [D] Set LAN DHCP (Automatic IP) of adapter: "%lan_Adap_Name%"
echo [E] Cancel
echo. 
:choice 
SET /P C=[A,B,C,D,E]? 
for %%? in (A) do if /I "%C%"=="%%?" goto A 
for %%? in (B) do if /I "%C%"=="%%?" goto B
for %%? in (C) do if /I "%C%"=="%%?" goto C 
for %%? in (D) do if /I "%C%"=="%%?" goto D
for %%? in (E) do if /I "%C%"=="%%?" goto end
for %%? in ("") do if /I "%C%"=="" goto end
goto choice 
:A 
@echo off
echo "Static IP Address:" 
echo 10.0.93.6
echo.
echo "Subnet Mask:" 
echo 255.255.255.0
echo.
echo "Default Gateway:"
echo 10.0.93.4
pause
 
netsh interface ip set address "%wlan_Adap_Name%" static 10.0.93.6 255.255.255.0 10.0.93.4 1
pause
goto end

:B
@echo off
netsh interface ip set address "%wlan_Adap_Name%" dhcp
pause
goto end

:C
@echo off
echo "Static IP Address:" 
echo 10.0.93.5
echo.
echo "Subnet Mask:" 
echo 255.255.255.0
echo.
echo "Default Gateway:"
echo 10.0.93.4
pause

netsh interface ip set address "%lan_Adap_Name%" static 10.0.93.5 255.255.255.0 10.0.93.4 1
pause
goto end

:D
@echo off
netsh interface ip set address "%lan_Adap_Name%" dhcp
pause
goto end

end