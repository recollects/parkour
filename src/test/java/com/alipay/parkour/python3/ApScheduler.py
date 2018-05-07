import datetime  
from apscheduler.schedulers.blocking import BlockingScheduler  

import itchat
import threading


scheduler = BlockingScheduler()

itchat.auto_login()

def test():  
    #print ("now is '%s' " % datetime.datetime.now()  )
    user = itchat.search_friends(name=u'苏明瑾')[0]
    user.send(u'起床写代码啦!当前系统时间:"%s" \n【自动发送】'% datetime.datetime.now())
  
scheduler.add_job(test, "cron", second="*/3")  
  
try:  
    scheduler.start()  
except (KeyboardInterrupt, SystemExit):  
    scheduler.shutdown() 