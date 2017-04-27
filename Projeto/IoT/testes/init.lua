function get(address, decode, callback)
    print("Send to address "..address)
    http.get(address, "Accept: application/json\r\n", function(code, data)
        if (code < 0) then
          print("HTTP request failed")
        else
            if (decode) then
                local resp = cjson.decode(data)
                callback(resp)
            else
                callback(data)
            end
        end
    end)
end

function main()
    get("http://192.168.25.26:8000/eureka/apps/muniserver",true, function(resp)
        ipAplicacao = resp.application.instance.ipAddr
        portAplicacao = resp.application.instance.port["$"]
        print(ipAplicacao..":"..portAplicacao)
        get(ipAplicacao..":"..portAplicacao, false, function(app)
            print(app)
        end)
    end)
end


wifi.setmode(wifi.STATION)
--wifi.sta.config("WiFi-Repeater", "willianfreire")
wifi.sta.config("GUMGA","gumgaqwe123")
wifi.sta.connect()


timeout = 0
tmr.alarm(1,1000,1, function()
    if (wifi.sta.getip() == nil) then
        timeout = timeout + 1
        print("Trying to connect!")
    end
    if (wifi.sta.getip() ~= nil) then
       tmr.stop(1)
       print("Connected. IP is "..wifi.sta.getip())
       main()
    end  
end)