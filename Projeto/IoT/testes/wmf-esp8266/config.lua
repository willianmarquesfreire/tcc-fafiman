function configureWifi()
    srv:listen(
        80,
        function(conn)
            conn:on(
                "receive",
                function(conn, payload)
                    for i = 0, tablelength(ap), 1 do
                        if ap[i] ~= nil then
                            listaAp = listaAp .. "<option value='" .. ap[i] .. "'>" .. ap[i] .. "</option>"
                        end
                    end
                    
                    listaAp = listaAp .. "</select><br/>"
                    
                    local _GET = getParamsUrl(payload)
                    
                    if (_GET ~= nil) then
                        if (_GET.ssid ~= nil and _GET.password ~= nil) then
                            print("Connecting to wifi and creating configuration...")
                            print("SSID: " .. _GET.ssid)
                            print("Password: " .. _GET.password)
                            
                            for i = 0, tablelength(ap), 1 do
                                if ap[i] ~= nil then
                                    if (string.find(ap[i], _GET.ssid)) then
                                        user = ap[i]
                                    end
                                end
                            end
                            
                            password = _GET.password
                            
                            wifi.sta.config(user, password)
                            wifi.sta.connect()
                            
                            if file.open("config.lc", "w") then
                                file.writeline(user .. " " .. password)
                                file.close()
                            end
                            
                            node.restart()
                            node.chipid()
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
                        ]] .. listaAp .. [[
                        <p>Password: </p><input type="password" name="password"/></p><br/>
                        <p><input type="submit" value="Conectar"/><br/>
                        </form>
                        </body>
                        </html>
                    ]])
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