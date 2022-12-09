# ADC를 통해 FSR 센서값 출력하기 
import smbus
import time

bus = smbus.SMBus(1)

    
def setup(Addr):
    global address
    address = Addr


def read(chn): # FSR의 값을 읽어들이는 부분 
    try:
        if chn == 0: # chn == 0은 FSR이 연결된 핀(AIN0)
            bus.write_byte(address, 0x40) # Fsr 값 읽기 
    except Exception as e:
        print("address : %s" % address)

    return bus.read_byte(address)


def write(val):
    try:
        temp=val
        temp=int(temp) # 정수로 변환하여 아날로그값을 디지털로 출력 
        bus.write_byte_data(address, 0x40, temp)

    except Exception as e:
        print("Error")



if __name__=="__main__":
    setup(0x48)
    while True:
        print('AIN0 =', read(0)) # FSR 핀 AIN0의 값을 출력 

        tmp = read(0)
        tmp=tmp*(255-125)/255+125
        write(tmp)
        time.sleep(1.0)
