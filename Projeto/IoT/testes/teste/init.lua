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