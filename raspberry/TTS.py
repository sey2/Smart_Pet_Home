import RPi.GPIO as GPIO
import time
import os


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

def playAudio():
