module_util="ok"
function split(str, pat)
    local t = {} -- NOTE: use {n = 0} in Lua-5.0
    local fpat = "(.-)" .. pat
    local last_end = 1
    local s, e, cap = str:find(fpat, 1)
    while s do
        if s ~= 1 or cap ~= "" then
            table.insert(t, cap)
        end
        last_end = e + 1
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
    for _ in pairs(T) do
        count = count + 1
    end
    return count
end

function getParamsUrl(request)
    local buf = ""
    local _, _, method, path, vars = string.find(request, "([A-Z]+) (.+)?(.+) HTTP")
    if (method == nil) then
        _, _, method, path = string.find(request, "([A-Z]+) (.+) HTTP")
    end
    local _GET = {}
    if (vars ~= nil) then
        for k, v in string.gmatch(vars, "(%w+)=(%w+)&*") do
            _GET[k] = unescape(v)
        end
    end
    return _GET
end
