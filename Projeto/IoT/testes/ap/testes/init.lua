-- Channel: 1-14
-- Authentication: AUTH_OPEN, AUTH_WPA_PSK, AUTH_WPA2_PSK, AUTH_WPA_WPA2_PSK
-- Hidden Network? True: 1, False: 0
-- Max Connections 1-4
-- WiFi Beacon 100-60000
--- Password: 8-64 chars. Minimum 8 Chars
-- SSID: 1-32 chars

if file.exists("config.lc") then
    print("Open configuration...")
    file.open("config.lc")
    print(file.read())
    file.close()
else
    file.open("config.lc", "w")
    file.writeline("")                                                
    file.close()     
    print("Createad configuration...")
end


net.dns.setdnsserver("8.8.8.8", 0)
net.dns.setdnsserver("192.168.1.252", 1)
net.createConnection(net.TCP, 0)

wifi.setmode(wifi.STATIONAP)
wifi.setphymode(wifi.PHYMODE_N)

wifi.sta.config("GUMGA","gumgaqwe123")
wifi.sta.connect()

wifi.ap.config({
    ssid="wmfsystemf",
    pwd="willianfreire",
    auth=AUTH_OPEN,
    channel=6,
    hidden=0, 
    max=4,
    beacon=100
})
wifi.ap.setip({
    ip= "192.168.10.1",
    netmask= "255.255.255.0",
    gateway= "192.168.10.1"
})
wifi.ap.dhcp.config({start= "192.168.10.2"})
print(wifi.getmode())


function main()
    if srv~=nil then
      srv:close()
    end
    srv=net.createServer(net.TCP)
    srv:listen(80,function(conn)
      conn:on("receive",function(conn,payload)
        print(payload)
        conn:send([[
            <p>SSID: </p><input type="text" name="ssid"/></p><br/>
            <p>Password: </p><input type="password" name="password"/></p><br/>
            <p><input type="submit" value="Conectar"/><br/>
        ]])
      end)
      conn:on("sent",function(conn) conn:close() end)
    end)
    print("connected...")

end

tmr.alarm(1,1000,1,function()
    if wifi.ap.getip() == nil then
        print("Connecting...")
    else
        print("Connected in "..wifi.ap.getip())
        main()
        tmr.stop(1)
    end
end)