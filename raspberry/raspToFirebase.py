#-*- coding:utf-8 -*-

import pyrebase
import time
from datetime import datetime
import json
import os
from datetime import datetime

config={
	"apiKey": "AIzaSyCTo_nYSa1qANzhJ4YQHpX4zWlIjQYfCjE", #webkey
	"authDomain": "smartpetsystem-be68f", #프로젝트ID
	"databaseURL": "gs://smartpetsystem-152cc.appspot.com", #database url
	"storageBucket": "smartpetsystem-152cc.appspot.com" #storage
}

firebase = pyrebase.initialize_app(config)


def upload(tmp, hum, time):     
    #업로드할 파일명
    #uploadfile = "/home/pi/20184494.png"
    uploadfile = "/home/pi/1.png"
    tmp = str(tmp).split('.')[0] + 'C'

    #업로드할 파일의 확장자 구하기
    s = os.path.splitext(uploadfile)[1]

    #업로드할 새로운파일이름
    #now = datetime.today().strftime("%Y%m%d")
   # now = '34C902022-12-0321:12'
    t = datetime.now()
    now = tmp + str(hum).split('.')[0] + t.strftime('%Y-%m-%d') + time
    filename = now + s 

    #Upload files to Firebase
    storage = firebase.storage()

    storage.child("Picture/"+filename).put(uploadfile)
    fileUrl = storage.child("Picture/"+filename).get_url(1) #0은 저장소 위치 1은 다운로드 url 경로이다.
    #동영상 파일 경로를 알았으니 어디에서든지 참조해서 사용할 수 있다.
    print (fileUrl)

'''
#업로드한 파일과 다운로드 경로를 database에 저장하자. 그래야 나중에 사용할 수 있다. storage에서 검색은 안된다는 것 같다.
#save files info in database
db = firebase.database()
d = {} 
d[filename] = fileUrl
data = json.dumps(d)
results = db.child("Picture").push(data)
print("OK")
'''
#Retrieve data - 전체 파일목록을 출력해 보자. 안드로이드앱에서 출려하게 하면 된다.
'''db = firebase.database()
files = db.child("files").get().val() #딕셔너리로 반환된다.
print(files)
'''

#결과 알림 - 파일이 업로드 되었습니다. 
#아니면 해당 다운로드주소를 이메일로 보내주자.
