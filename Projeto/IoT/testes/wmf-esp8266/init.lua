-- Channel: 1-14
-- Authentication: AUTH_OPEN, AUTH_WPA_PSK, AUTH_WPA2_PSK, AUTH_WPA_WPA2_PSK
-- Hidden Network? True: 1, False: 0
-- Max Connections 1-4
-- WiFi Beacon 100-60000
--- Password: 8-64 chars. Minimum 8 Chars
-- SSID: 1-32 chars
-- file.remove("init.lua")  file.remove("config.lc")  node.restart()

dofile("config.lua")
dofile("util.lua")
dofile("register.lua")

net.dns.setdnsserver("8.8.8.8", 0)
net.dns.setdnsserver("192.168.1.252", 1)
net.createConnection(net.TCP, 0)

wifi.setmode(wifi.STATIONAP)
wifi.setphymode(wifi.PHYMODE_N)

local user = ""
local password = ""

wifi.ap.config({ssid = "wmfsystem", pwd = "", auth = AUTH_OPEN, channel = 6, hidden = 0, max = 4, beacon = 100})
wifi.ap.setip({ip = "192.168.1.1", netmask = "255.255.255.0", gateway = "192.168.1.1"})
wifi.ap.dhcp.config({start = "192.168.1.2"})

if srv ~= nil then
    srv:close()
end
srv = net.createServer(net.TCP)

if file.exists("config.lc") then
    print("Open configuration...")
    if file.open("config.lc") then
        local corte = split(file.read(), " ")
        user = corte[1]:gsub("%s+", "")
        password = corte[2]:gsub("%s+", "")
        
        print("Connecting in ip with user: " .. u .. " and password: " .. p)
        wifi.sta.config(u, p)
        wifi.sta.connect()
        startEureka()
    end
else
    local ap = {}
    wifi.sta.getap(
        function(t)
            i = 0
            for ssid, v in pairs(t) do
                ap[i] = ssid
                i = i + 1
            end
        end
    )
    local listaAp = "SSID: <select name='ssid'>"
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
