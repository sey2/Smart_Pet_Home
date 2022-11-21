
import socket
from threading import Thread
import RPi.GPIO as GPIO
import time

# raspberry ip
HOST = '172.20.10.2'
PORT = 7555

def receive(s):
    while True:
        try:
            data = s.recv(128)
            if not data:
                break
            decode = data.decode()
            print(decode)
        except:
            pass

def Chat():
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind((HOST, PORT))
    s.listen(1)
    connection, addr = s.accept()
    print('Connected with ' + addr[0])
    th = Thread(target=receive, args=(connection,))
    th.daemon = True
    th.start()

    try:
        while True:
            message = input()
            connection.sendall(message.encode())
    finally:
                s.close()
Chat()
