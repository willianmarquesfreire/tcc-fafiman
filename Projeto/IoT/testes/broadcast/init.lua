 wifi.setmode(wifi.STATION)  
 wifi.sta.config("WiFi-Repeater","willianfreire")  
 wifi.sta.connect()
 tmr.alarm(0, 500, 1, function()  
     if wifi.sta.getip()==nil then  
      print("Connecting to AP...")  
     else
     
        if (pcall(function()
            srv = net.createConnection(net.UDP, 0)
            srv:send(1234, "192.168.1.255", "Request Address Eureka")
            srv:on("receive", function(a,b)
                print(a)
                print(b)
                srv:close()
            end)
        end)) then
            print("ui")
        else
            print("aff")
        end
        tmr.stop(1)  
        tmr.stop(0)  
        print("Connected as: " .. wifi.sta.getip())  
     end  
   end)  