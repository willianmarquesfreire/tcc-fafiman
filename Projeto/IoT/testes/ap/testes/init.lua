-- Channel: 1-14
-- Authentication: AUTH_OPEN, AUTH_WPA_PSK, AUTH_WPA2_PSK, AUTH_WPA_WPA2_PSK
-- Hidden Network? True: 1, False: 0
-- Max Connections 1-4
-- WiFi Beacon 100-60000
--- Password: 8-64 chars. Minimum 8 Chars
-- SSID: 1-32 chars
-- file.remove("init.lua")  file.remove("config.lc")  node.restart()

function registerEureka(ip)
    print("Registering in Eureka "..ip)
    http.post(
        "http://10.10.10.12:8000/eureka/apps/appID",
        "Content-Type: application/json\r\n",
        [[
      {
          "instance": {
            "hostName": "]]..ip..[[",
            "app": "nodemcu",
            "ipAddr": "http://]]..ip..[[",
            "status": "UP",
            "port": {
              "@enabled": "true",
              "$": "8080"
            },
            "securePort": {
              "@enabled": "false",
              "$": "443"
            },
            "dataCenterInfo": {
              "@class": "com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo",
              "name": "MyOwn"
            },
            "leaseInfo": {
              "renewalIntervalInSecs": 30,
              "durationInSecs": 90,
              "registrationTimestamp": 1492644843509,
              "lastRenewalTimestamp": 1492649644434,
              "evictionTimestamp": 0,
              "serviceUpTimestamp": 1492644813469
            },
            "homePageUrl": "http://]]..ip..[[:8080/",
            "statusPageUrl": "http://]]..ip..[[:8080/info",
            "healthCheckUrl": "http://]]..ip..[[:8080/health",
            "vipAddress": "nodemcu"
          }
        }
  ]],
        function(code, data)
            if (code < 0) then
                print("HTTP request failed")
            else
                print(code, data)
            end
        end
    )
end

function registerServer()
    -- A simple http server
    if srv~=nil then
      srv:close()
    end
    srv = net.createServer(net.TCP)
    srv:listen(
        8080,
        function(conn)

            conn:on("receive", function(client,request)
                local buf = "";
                local _, _, method, path, vars = string.find(request, "([A-Z]+) (.+)?(.+) HTTP");
                if(method == nil)then
                    _, _, method, path = string.find(request, "([A-Z]+) (.+) HTTP");
                end
                local _GET = {}
                if (vars ~= nil)then
                    for k, v in string.gmatch(vars, "(%w+)=(%w+)&*") do
                        _GET[k] = v
                    end
                end
                buf = buf.."<h1> ESP8266 Web Server</h1>";
                for i=1,8,1
                do
                    gpio.mode(i, gpio.OUTPUT)
                    buf = buf.."<p>DIGITAL "..i.." <a href=\"?pin="..i.."&stat=on\"><button>ON</button></a>&nbsp;<a href=\"?pin="..i.."&stat=off\"><button>OFF</button></a></p>"
                end

                if (_GET.stat ~= nil and _GET.stat ~= nil) then
                    if (_GET.stat == "on") then
                        gpio.write(_GET.pin, gpio.HIGH);
                    else
                        gpio.write(_GET.pin, gpio.LOW);
                    end
                end

                print(buf)
                
                client:send(buf);
            end)
            
            conn:on(
                "sent",
                function(conn)
                    conn:close();
                    collectgarbage();
                end
            )
        end
    )
end


function start()
    timeout = 0
    tmr.alarm(1, 1000, 1,
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
                registerEureka(wifi.sta.getip())
                registerServer()
                print("Connected, IP is " .. wifi.sta.getip())
            end
        end
    )
end

function split(str, pat)
   local t = {}  -- NOTE: use {n = 0} in Lua-5.0
   local fpat = "(.-)" .. pat
   local last_end = 1
   local s, e, cap = str:find(fpat, 1)
   while s do
      if s ~= 1 or cap ~= "" then
         table.insert(t,cap)
      end
      last_end = e+1
      s, e, cap = str:find(fpat, last_end)
   end
   if last_end <= #str then
      cap = str:sub(last_end)
      table.insert(t, cap)
   end
   return t
end

local unescape = function(s)
    s = string.gsub(s, "+", " ")
    s = string.gsub(
        s,
        "%%(%x%x)",
        function(h)
            return string.char(tonumber(h, 16))
        end
    )
    return s
end

function tablelength(T)
  local count = 0
  for _ in pairs(T) do count = count + 1 end
  return count
end


net.dns.setdnsserver("8.8.8.8", 0)
net.dns.setdnsserver("192.168.1.252", 1)
net.createConnection(net.TCP, 0)

wifi.setmode(wifi.STATIONAP)
wifi.setphymode(wifi.PHYMODE_N)

wifi.ap.config({
    ssid="wmfsystem",
    pwd="willianfreire",
    auth=AUTH_OPEN,
    channel=6,
    hidden=0, 
    max=4,
    beacon=100
})
wifi.ap.setip({
    ip= "192.168.1.1",
    netmask= "255.255.255.0",
    gateway= "192.168.1.1"
})
wifi.ap.dhcp.config({start= "192.168.1.2"})
print(wifi.getmode())

local ap = {}

-- Print AP list that is easier to read
function listap(t) -- (SSID : Authmode, RSSI, BSSID, Channel)
    i = 0;
    for ssid,v in pairs(t) do
        ap[i] = ssid
        i = i + 1;
    end
end

wifi.sta.getap(listap)

listaAp = "SSID: <select name='ssid'>"
        

function main()
    if srv~=nil then
      srv:close()
    end
    srv=net.createServer(net.TCP)
    srv:listen(80,function(conn)
      conn:on("receive",function(conn,payload)

        for i=0, tablelength(ap),1 do
            if ap[i] ~= nil then
                listaAp = listaAp..
                "<option value='"..ap[i].."'>"..ap[i].."</option>"
            end
        end
        
        listaAp = listaAp.."</select><br/>"

        local buf = "";
        local _, _, method, path, vars = string.find(payload, "([A-Z]+) (.+)?(.+) HTTP");
        if(method == nil)then
            _, _, method, path = string.find(payload, "([A-Z]+) (.+) HTTP");
        end
        local _GET = {}
        if (vars ~= nil)then
            for k, v in string.gmatch(vars, "(%w+)=(%w+)&*") do
                _GET[k] = unescape(v)
            end
        end

        if (_GET ~= nil) then
            if (_GET.ssid ~= nil and _GET.password ~= nil) then
                print("SSID: ".._GET.ssid)
                print("Password: ".._GET.password)
                local user = "";
                local password = ""

                for i=0, tablelength(ap),1 do
                    if ap[i] ~= nil then
                        if (string.find(ap[i],_GET.ssid)) then
                            user = ap[i]
                        end
                    end
                end
                
                password = _GET.password

                wifi.sta.config(user,password)
                wifi.sta.connect()
    
                print("Createad configuration...")
                if(pcall(start)) then
                    print("Server started")
                else
                    print("Server Restarted")
                    node.restart()
                    node.chipid()
                end

            end
    
        end
        
        conn:send([[
            <!DOCTYPE html>
            <html lang="pt-br">
            <head>
            <meta charset="utf8"/>
            <title>NodeMcu Configuration</title>
            </head>
            <body>
            <form action="#">
            ]]..
            listaAp
            ..[[
            <p>Password: </p><input type="password" name="password"/></p><br/>
            <p><input type="submit" value="Conectar"/><br/>
            </form>
            </body>
            </html>
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

