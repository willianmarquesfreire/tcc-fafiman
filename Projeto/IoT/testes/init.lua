function get(address, callback)
    http.get(address, "Accept: application/json\r\n", decode, function(code, data)
        if (code < 0) then
          print("HTTP request failed")
        else
          if (decode == true) then
            local resp = cjson.decode(data)
            callback(resp)
          else
            callback(data)
          end
        end
    end)
end


get("http://192.168.1.103:8000/eureka/apps/muniserver",true, function(resp)
    ipAplicacao = resp.application.instance.ipAddr
    portAplicacao = resp.application.instance.port["$"]
    print(ipAplicacao..":"..portAplicacao)
    get(ipAplicacao..":"..portAplicacao, false, function(app)
        print(app)
    end)
end)
