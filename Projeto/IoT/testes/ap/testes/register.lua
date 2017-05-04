print("Starting Register on Eureka...")
-- file.remove("init.lua") node.restart()

function registerEureka(ip)
    print("Registering in Eureka "..ip)
    http.post(
        "http://192.168.25.26:8000/eureka/apps/appID",
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



if(pcall(start)) then
    print("Server started")
else
    print("Server Restarted")
    node.restart()
    node.chipid()
end
