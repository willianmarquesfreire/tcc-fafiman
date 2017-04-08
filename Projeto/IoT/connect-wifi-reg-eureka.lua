wifi.setmode(wifi.STATION)
wifi.sta.config("TP-LINK_E91ECC","willianfreire")
wifi.sta.connect()


net.dns.setdnsserver("8.8.8.8", 0)
http.get('https://172.16.0.156:8001/eureka/apps/appID',
[[
  Content-Type: application/json\r\n
  ]],
  [[
  {
    "instance": {
        "hostName": "WKS-SOF-L011",
        "app": "com.wmfsystem.iotbonito"
    },
        "vipAddress": "com.wmfsystem.iotbonito",
        "secureVipAddress": "com.wmfsystem.iotbonito",
        "ipAddr": "10.0.0.10",
        "status": "STARTING",
        "port": {"$": "8080", "@enabled": "true"},
        "securePort": {"$": "8443", "@enabled": "true"},
        "healthCheckUrl": "http://WKS-SOF-L011:8080/healthcheck",
        "statusPageUrl": "http://WKS-SOF-L011:8080/status",
        "homePageUrl": "http://WKS-SOF-L011:8080",
        "dataCenterInfo": {
            "@class": "com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo",
            "name": "MyOwn"
        }
  }
  ]],
  function(code, data)
    if (code < 0) then
      print("HTTP request failed")
    else
      print(code, data)
    end
  end)
 
