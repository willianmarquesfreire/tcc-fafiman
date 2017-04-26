--Pedro Minatel - 2015-12-13
print("Starting...")

if pcall(function ()
    print("Open config")
    dofile("config.lc")
end) then
   print("Connecting to WIFI...")
   --Configuring WiFi as STATION
   wifi.setmode(wifi.STATION)
   wifi.sta.config("WiFi-Repeater","willianfreire")
   wifi.sta.connect()
   timeout = 0;
   tmr.alarm(1, 1000, 1, function()
      if wifi.sta.getip() == nil then
         print("IP unavaiable, waiting... " .. timeout)
      timeout = timeout + 1
      if timeout >= 60 then
       file.remove('config.lc')
       node.restart()
      end
      else
         tmr.stop(1)
         print("Connected, IP is "..wifi.sta.getip())
        --do your code here
      end
   end)
else
   print("Enter configuration mode")
   dofile("wifi_setup.lua")
end--Pedro Minatel - 2015-12-13
print("Starting...")

if pcall(function ()
    print("Open config")
    dofile("config.lc")
end) then
   print("Connecting to WIFI...")
   --Configuring WiFi as STATION
   wifi.setmode(wifi.STATION)
   wifi.sta.config("WiFi-Repeater","willianfreire")
   wifi.sta.connect()
   timeout = 0;
   tmr.alarm(1, 1000, 1, function()
      if wifi.sta.getip() == nil then
         print("IP unavaiable, waiting... " .. timeout)
      timeout = timeout + 1
      if timeout >= 60 then
       file.remove('config.lc')
       node.restart()
      end
      else
         tmr.stop(1)
         print("Connected, IP is "..wifi.sta.getip())
        --do your code here
      end
   end)
else
   print("Enter configuration mode")
   dofile("wifi_setup.lua")
end