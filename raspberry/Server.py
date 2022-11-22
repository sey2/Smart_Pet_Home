import socket
from threading import Thread
import RPi.GPIO as GPIO
import time

# raspberry ip
HOST = '172.20.10.2'
PORT = 2036

def receive(s):
    while True:
        try:
            data = s.recv(1024)
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

    if connection:
        print('Connected with ' + addr[0])
        in_data = connection.recv(4)
        length = int.from_bytes(in_data,"little");
        in_data = connection.recv(length)
        msg = in_data.decode()
     
        print(msg)
   
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

