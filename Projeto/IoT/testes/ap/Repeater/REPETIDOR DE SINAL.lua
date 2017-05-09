http://www.areresearch.net/2015/10/using-esp8266-as-wifi-range-extender.html

The modifications are scattered around my blog, sorry about that. The relevant changes to make this work are:
In /app/include/lwipopts.h add a #define IP_FORWARD 1
and
In /app/include/lwip/app/dhcpserver.h add #define USE_DNS somewhere (around line 54)
These two should be sufficient to make it work.

 wifi.setmode(wifi.STATIONAP)  
 wifi.sta.config("WiFi-Repeater","willianfreire")  
 tmr.alarm(0, 500, 1, function()  
     if wifi.sta.getip()==nil then  
      print("Connecting to AP...")  
     else  
      tmr.stop(1)  
      tmr.stop(0)  
      print("Connected as: " .. wifi.sta.getip())  
     end  
   end)  
 cfg={}  
    cfg.ssid="intarwebs"  
    wifi.ap.config(cfg)  
