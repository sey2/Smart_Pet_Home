#!/usr/bin/env python
import Adafruit_DHT
import sys

sensor = Adafruit_DHT.DHT11
pin = 21

def readDHT():
    while True:
        hum, tmp = Adafruit_DHT.read_retry(sensor, pin)
        
        if hum is not None and tmp is not None:
            return hum, tmp
        else:
            print('Failed')


