print("---->SERVER NODEMCU-ESP8266 CREATED ")
print("--------->STARTED")

function registerServer()
    -- A simple http server
    srv = net.createServer(net.TCP)
    srv:listen(
        80,
        function(conn)
            conn:on(
                "receive",
                function(conn, payload)
                    print(payload)
                    conn:send("<h1> Hello, NodeMcu.</h1>")
                end
            )
            conn:on(
                "sent",
                function(conn)
                    conn:close()
                end
            )
        end
    )
end

function registerEureka()
    http.post(
        "http://192.168.1.103:8000/eureka/apps/appID",
        "Content-Type: application/json\r\n",
        [[
      {
      "instance": {
        "hostName": "]] .. wifi.sta.getip() .. [[",
        "app": "NODEMCU-ESP8266",
        "ipAddr": "http://]] .. wifi.sta.getip() .. [[",
        "status": "UP",
        "port": {
          "@enabled": "true",
          "$": "18000"
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
        "homePageUrl": "http://]] .. wifi.sta.getip() .. [[/",
        "statusPageUrl": "http://]] .. wifi.sta.getip() .. [[",
        "healthCheckUrl": "]] .. wifi.sta.getip() .. [[/health",
        "vipAddress": "NODEMCU-ESP8266"
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

registerEureka()
registerServer()
