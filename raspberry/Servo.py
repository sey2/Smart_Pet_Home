import RPi.GPIO as GPIO
import time
import os

SERVO_MAX_DUTY    = 12   # 서보의 최대(180도) 위치의 주기
SERVO_MIN_DUTY    = 3    # 서보의 최소(0도) 위치의 주기
servo_pin = 18

GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)
GPIO.setup(servo_pin, GPIO.OUT)     # 서보모터 GPIO.setup

servo = GPIO.PWM(servo_pin, 50) # PWM 인스턴스 서보 생성, 주파수 50으로 사용 
servo.start(0.0) # PWM 시작 duty = 0. duty가 0이면 서보는 동작하지 않음

def setServo(degree): # 서보 위치를 설정하는 함수 
    if degree > 180: # 각도는 최대 180도 
        degree = 180
        # 각도를 duty로 변경해주는 것 
    duty = SERVO_MIN_DUTY +(degree*(SERVO_MAX_DUTY-SERVO_MIN_DUTY)/180.0)
    print("Degree: {} to {} Duty".format(degree, duty))
    servo.ChangeDutyCycle(duty) # 변경된 duty값을 서보 PWM에 적용 

def startServo():
    setServo(0)
    time.sleep(3)
    setServo(90)
    time.sleep(3)
    servo.start(0.0)

print("end")
