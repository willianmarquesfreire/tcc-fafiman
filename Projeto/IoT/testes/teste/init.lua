request = [[
GET /?ip=172.17.0.1 HTTP/1.1
Accept: application/json, application/*+json
User-Agent: Java/1.8.0_121
Host: 192.168.1.104:8000
Connection: keep-alive
]]

local index = string.find(request, "?ip=")
local novo = string.sub(request, index)
local vars = string.match(novo, "%d+%.%d+%.%d+%.%d+%:*%d*")


print(vars)

