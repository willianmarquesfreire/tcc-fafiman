print("Open configuration...")
    file.open("config.lc")
    corte = split(file.read()," ")
    user = corte[1]:gsub("%s+", "")
    pass = corte[2]:gsub("%s+", "")   
    
    print("STA: "..user..pass)
    file.close()