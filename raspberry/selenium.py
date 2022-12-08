import time
from selenium import webdriver

def capture():
    driver = webdriver.Chrome('/usr/lib/chromium-browser/chromedriver')
    driver.get("http://localhost:8080/stream")
    driver.save_screenshot('1.png')

