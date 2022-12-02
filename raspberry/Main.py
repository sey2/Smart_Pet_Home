import Servo as sv
import TTS
import socket

button_pin1 = 17
button_pin2 = 27
button_pin3 = 22

GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)

GPIO.setup(button_pin1, GPIO.IN , pull_up_down=GPIO.PUD_DOWN) # hungry_버튼 1
GPIO.setup(button_pin2, GPIO.IN , pull_up_down=GPIO.PUD_DOWN) # walk_버튼 2
GPIO.setup(button_pin3, GPIO.IN , pull_up_down=GPIO.PUD_DOWN) # play_버튼 3

HOST='172.20.10.12'
PORT =5003

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print('Connecting to ' + HOST)

s.connect((HOST,PORT))
try:
    message = b'Hello Wrold'
    print('Sending ' + format(message))
    s.sendall(message)

    while True:
        data = s.recv(128)
        
        if data and data.format == b'Hello Wrold':
            sv.startServo()
            # DHT11 코드
            # 데이터 베이스 보내는 코드
            # 클라우드 사진 보내는 코드

        if GPIO.input(button_pin1) == 1: # 버튼 1번 누르면
            print('espeak', option_hungry, msg1) # msg1에 대한 텍스트 출력
            TTS.speak1(option_hungry,msg1) # msg1에 대한 음성 출력
            time.sleep(1)
        
        elif GPIO.input(button_pin2) == 1: # 버튼 2번 누르면
            print('espeak', option_walk, msg2) # msg2에 대한 텍스트 출력
            speak2(option_hungry,msg1) # msg2에 대한 음성 출력
            time.sleep(1)
            
        elif GPIO.input(button_pin3) == 1: # 버튼 1번 누르면
            print('espeak', option_play, msg3) # msg1에 대한 텍스트 출력
            speak3(option_hungry,msg3) # msg1에 대한 음성 출력
            time.sleep(1)        
finally:
    s.close()
