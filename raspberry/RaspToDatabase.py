import requests
from datetime import datetime

now = datetime.now()

def sendDB(tmp, hum):
    hashmap = {}
    hashmap['temp'] = str(tmp)
    hashmap['humidity'] = str(hum)
    hashmap['day'] = str(now.date())
    hashmap['time'] = str(now.strftime("%H:%M"))
    r = requests.post('http://casio2978.dothome.co.kr/PetSend.php', hashmap).text
    print(r)

