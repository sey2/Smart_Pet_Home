import RPi.GPIO as GPIO
import Servo as sv
import TTS
import socket
import time
import sys
import DHT
import RaspToDatabase as DB
import raspToFirebase as FB
import camera
from datetime import datetime


GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)

button_pin1 = 17
button_pin2 = 27
button_pin3 = 22

GPIO.setup(button_pin1, GPIO.IN , pull_up_down=GPIO.PUD_DOWN) # hungry_버튼 1
GPIO.setup(button_pin2, GPIO.IN , pull_up_down=GPIO.PUD_DOWN) # walk_버튼 2
GPIO.setup(button_pin3, GPIO.IN , pull_up_down=GPIO.PUD_DOWN) # play_버튼 3

HOST='172.20.10.6'
PORT = 5070

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print('Connecting to ' + HOST)
s.connect((HOST,PORT))

def btn_event1(a):
    TTS.speak1() 
    print("btn1")

def btn_event2(a):
    TTS.speak2()
    print("btn2")
    
def btn_event3(a):
    TTS.speak3()
    print("btn3")


GPIO.add_event_detect(button_pin1, GPIO.RISING, callback=btn_event1, bouncetime=250)
GPIO.add_event_detect(button_pin2, GPIO.RISING, callback=btn_event2, bouncetime=250)
GPIO.add_event_detect(button_pin3, GPIO.RISING, callback=btn_event3, bouncetime=250)

try:
    message = b'Hello Wrold'
    print('Sending ' + format(message))
    s.sendall(message)

    while True:
        data = s.recv(128)
        if data:
            print("recive data")
            #sv.startServo()
            
            flush = s.recv(128)

            now = datetime.now()
            tme = str(now.strftime("%H:%M"))
            
            # DHT11 코드
            hum, tmp = DHT.readDHT()
            print('Temp ={0:0.1f}*C Humidity={0:0.1f}%'.format(tmp,hum))
            
            # 데이터 베이스 보내는 코드
            DB.sendDB(tmp,hum, tme)
            
            # 클라우드 사진 보내는 코드
            camera.capture()

            time.sleep(3)
            FB.upload(tmp, hum, tme)
              
finally:
    s.close()
    GPIO.cleanup()


