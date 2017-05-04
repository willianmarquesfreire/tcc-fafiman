-- Channel: 1-14
-- Authentication: AUTH_OPEN, AUTH_WPA_PSK, AUTH_WPA2_PSK, AUTH_WPA_WPA2_PSK
-- Hidden Network? True: 1, False: 0
-- Max Connections 1-4
-- WiFi Beacon 100-60000
--- Password: 8-64 chars. Minimum 8 Chars
-- SSID: 1-32 chars

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

local user, pass

if file.exists("config.lc") then
    print("Open configuration...")
    file.open("config.lc")
    corte = split(file.read()," ")
    user = corte[1]:gsub("%s+", "")
    pass = corte[2]:gsub("%s+", "")   
    
    print("STA: "..user..pass)
    file.close()
end



net.dns.setdnsserver("8.8.8.8", 0)
net.dns.setdnsserver("192.168.1.252", 1)
net.createConnection(net.TCP, 0)

wifi.setmode(wifi.STATIONAP)
wifi.setphymode(wifi.PHYMODE_N)

if (user ~= nil and pass ~= nil) then
    wifi.sta.config(user,pass)
    wifi.sta.connect()
end

wifi.ap.config({
    ssid="nodemcu",
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

function main()
    if srv~=nil then
      srv:close()
    end
    srv=net.createServer(net.TCP)
    srv:listen(80,function(conn)
      conn:on("receive",function(conn,payload)

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
                local user,password;
                user = ap[_GET.ssid]
                password = ap[_GET.password]

                file.open("config.lc", "w")
                file.writeline(user.." "..password)                                                
                file.close()     
                print("Createad configuration...")
                node.restart()
                print(node.info())
            end
    
        end

        listaAp = "SSID: <select name='ssid'>"
        
        for i=0, tablelength(ap),1 do
            if ap[i] ~= nil then
                listaAp = listaAp..
                "<option value='"..i.."'>"..ap[i].."</option>"
            end
        end

        listaAp = listaAp.."</select><br/>"
        
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

