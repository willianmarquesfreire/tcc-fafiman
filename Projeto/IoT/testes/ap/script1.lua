
  -- Configurar o modo operação (STATION, SOFTAP ou STATIONAP)
wifi.setmode(wifi.SOFTAP)
  -- Configurar os dados de SSID e senha
wifi.ap.config({ssid="wmfsystemteste",pwd="willianfreire"})
  -- Resetar o módulo
print(wifi.getmode())