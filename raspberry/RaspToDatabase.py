import requests

hashmap = {}
hashmap['temp'] = '26C'
hashmap['humidity'] = '57%'
hashmap['day'] = '2022년 11월 12일'
hashmap['time'] = '08:20:34'

r = requests.post('http://casio2978.dothome.co.kr/PetSend.php', hashmap).text
print(r)
