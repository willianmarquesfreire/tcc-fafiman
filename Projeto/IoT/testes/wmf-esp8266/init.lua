-- Channel: 1-14
-- Authentication: AUTH_OPEN, AUTH_WPA_PSK, AUTH_WPA2_PSK, AUTH_WPA_WPA2_PSK
-- Hidden Network? True: 1, False: 0
-- Max Connections 1-4
-- WiFi Beacon 100-60000
--- Password: 8-64 chars. Minimum 8 Chars
-- SSID: 1-32 chars
-- file.remove("init.lua") file.remove("config.lc") file.remove("config.lua") file.remove("register.lua") file.remove("util.lua") node.restart()
gpio.mode(4, gpio.OUTPUT)
gpio.write(4, gpio.HIGH)

gpio.mode(3, gpio.INPUT)

gpio.trig(4, "up", function()
    print("Deleting configuration...")
    file.remove("config.lc")
    gpio.write(4, gpio.HIGH)
    node.restart()
end)

if module_config ~= "ok" then
    dofile("config.lua")
end

if module_util ~= "ok" then
    dofile("util.lua")
end
if module_register ~= "ok" then
    dofile("register.lua")
end

wifi.setmode(wifi.STATIONAP)

user = ""
password = ""

srv = net.createServer(net.TCP)

if file.exists("config.lc") == true then
    print("Open configuration...")
    if file.open("config.lc") then
        local corte = split(file.read(), " ")
        user = corte[1]:gsub("%s+", "")
        password = corte[2]:gsub("%s+", "")
        
        print("Connecting in ip with user: " .. user .. " and password: " .. password)
        wifi.sta.config(user, password)
        wifi.sta.connect()
        startEureka()
    end
else
    wifi.ap.config({ssid = "NodeMcuEsp8266"..node.chipid(), pwd = nil, auth = AUTH_OPEN, channel = 6, hidden = 0, max = 4, beacon = 100})
    wifi.ap.setip({ip = "192.168.10.1", netmask = "255.255.255.0", gateway = "192.168.10.1"})
    wifi.ap.dhcp.config({start = "192.168.10.2"})

    tmr.alarm(
        1,
        1000,
        1,
        function()
            if wifi.ap.getip() == nil then
                print("Connecting...")
            else
                print("Connected in " .. wifi.ap.getip())
                configureWifi()
            end
            tmr.stop(1)
        end
    )
end
