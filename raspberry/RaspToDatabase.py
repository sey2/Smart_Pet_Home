import requests
from datetime import datetime

now = datetime.now()

def sendDB(tmp, hum, time):
    hashmap = {}
    hashmap['temp'] = str(tmp).split('.')[0] + 'C'
    hashmap['humidity'] = str(hum).split('.')[0] + '%'
    hashmap['day'] = str(now.date())
    hashmap['time'] = time
    r = requests.post('http://casio2978.dothome.co.kr/PetSend.php', hashmap).text
    print(r)

