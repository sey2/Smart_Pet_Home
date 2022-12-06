import RPi.GPIO as GPIO
import time
import os

def speak1(): 
    os.system("espeak {} '{}'".format('-s 120 -p 95 -a 150 -v ko+f3','배가 고파요'))
    
def speak2():
    os.system("espeak {} '{}'".format('-s 120 -p 95 -a 150 -v ko+f3', '산책가고 싶어요'))
    
def speak3():
    os.system("espeak {} '{}'".format('-s 120 -p 95 -a 150 -v ko+f3' , '놀아 주세요'))

