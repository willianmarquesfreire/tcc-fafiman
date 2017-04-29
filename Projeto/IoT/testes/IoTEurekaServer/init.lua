print("Starting...")

if pcall(startFn) then
    print("Temp sent OK")
else
    print("Temp err")
end

function startFn()
    print("Connecting to WIFI...")
    --Configuring WiFi as STATION
    wifi.setmode(wifi.STATION)
    wifi.sta.config("TP-LINK_E91ECC", "willianfreire")
    cfg = {ip = "192.168.1.200", netmask = "255.255.255.0", gateway = "192.168.1.1"}
    wifi.sta.setip(cfg)
    wifi.sta.connect()
    timeout = 0
    tmr.alarm(
        1,
        1000,
        1,
        function()
            if wifi.sta.getip() == nil then
                print("IP unavaiable, waiting... " .. timeout)
                timeout = timeout + 1
                if timeout >= 60 then
                    file.remove('config.lc')
                    node.restart()
                end
            else
                tmr.stop(1)
                print("Enter configuration mode")
                dofile("connect-wifi-reg-eureka.lua")
                dofile("config-center.lua")
                print("Connected, IP is " .. wifi.sta.getip())
            end
        end
    )
end
