import RPi.GPIO as GPIO
import time
import os
button_pin1 = 17
button_pin2 = 27
button_pin3 = 22
servo_pin = 18 # 서보모터 핀
SERVO_MAX_DUTY    = 12   # 서보의 최대(180도) 위치의 주기
SERVO_MIN_DUTY    = 3    # 서보의 최소(0도) 위치의 주기
GPIO.setmode(GPIO.BCM)
PIR_PIN = 7 # 인체감지센서 핀
GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)
GPIO.setup(button_pin1, GPIO.IN , pull_up_down=GPIO.PUD_DOWN) # hungry_버튼 1
GPIO.setup(button_pin2, GPIO.IN , pull_up_down=GPIO.PUD_DOWN) # walk_버튼 2
GPIO.setup(button_pin3, GPIO.IN , pull_up_down=GPIO.PUD_DOWN) # play_버튼 3
GPIO.setup(servo_pin, GPIO.OUT)     # 서보모터 GPIO.setup
servo = GPIO.PWM(servo_pin, 50) # PWM 인스턴스 서보 생성, 주파수 50으로 사용 
servo.start(0.0) # PWM 시작 duty = 0. duty가 0이면 서보는 동작하지 않음
chk = 0 # 동작 횟수를 저장하는 변수  
try:
        while True:
                if(GPIO.input(PIR_PIN) == 1): # 인체감지센서 작동시 
                        print('motion')
                        chk += 1
                        if chk >= 3: # 동작 3번 감지되면 카메라 촬영 
                                execute_camera()
                                chk = 0
                else:
                        print('nothing')
                time.sleep(3)
except KeyboardInterrupt:
        print('Exit')
        exit()
option_hungry = '-s 120 -p 95 -a 150 -v ko+f3' # 옵션1, Hungry
msg1 = '배가 고파요'
option_walk = '-s 120 -p 95 -a 150 -v ko+f3' # 옵션2, Walk
msg2 = '산책가고 싶어요' 
option_play = '-s 120 -p 95 -a 150 -v ko+f3'  #옵션3, Play
msg3 = '놀아 주세요'
def speak1(option_hungry, msg1) : 
    os.system("espeak {} '{}'".format(option_hungry,msg1))
def speak2(option_walk, msg2) :
    os.system("espeak {} '{}'".format(option_walk,msg2))
def speak3(option_option_play, msg3) :
    os.system("espeak {} '{}'".format(option_play,msg3))
while True:
    if GPIO.input(button_pin1) == 1: # 버튼 1번 누르면 
        print("Button pushed!") 
        print('espeak', option_hungry, msg1) # msg1에 대한 텍스트 출력 
        speak1(option_hungry,msg1) # msg1에 대한 음성 출력 
        time.sleep(10)
    elif GPIO.input(button_pin2) == 1: # 버튼 2번 누르면
        print("Button pushed!") 
        print('espeak', option_walk, msg2) # msg2에 대한 텍스트 출력
        speak2(option_walk,msg2)  # msg2에 대한 음성 출력
        time.sleep(10)
    elif GPIO.input(button_pin3) == 1: # 버튼 3번 누르면
        print("Button pushed!")
        print('espeak', option_play, msg3) # msg3에 대한 텍스트 출력
        speak3(option_play,msg3)  # msg2에 대한 음성 출력
        time.sleep(10)
        
    else :
        print("Button Not pushed!")
    time.sleep(1)
    
def setServo(degree): # 서보 위치를 설정하는 함수 
    if degree > 180: # 각도는 최대 180도 
        degree = 180
        # 각도를 duty로 변경해주는 것 
        duty = SERVO_MIN_DUTY +(degree*(SERVO_MAX_DUTY-SERVO_MIN_DUTY)/180.0)
        print("Degree: {} to {} Duty".format(degree, duty))
        servo.ChangeDutyCycle(duty) # 변경된 duty값을 서보 PWM에 적용 
if __name__ == "__main__":
    setServo(0) # 서보 0도 
    time.sleep(0)
    setServo(90)
    time.sleep(0)
    servo.stop()
GPIO.cleanup()
    
