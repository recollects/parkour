import itchat
import threading

itchat.auto_login()

    #users = itchat.search_friends(name=u'儿时的回忆')
    #userName = users[0]['UserName']
#itchat.send('日常一催,代码提交一波【来自机器自动发送】', toUserName='ye349383012')




def fun_timer():
    #print('Hello Timer!')
    user = itchat.search_friends(name=u'儿时的回忆')[0]
    user.send(u'日常一催,代码提交一波【自动发送】')
    global timer
    timer = threading.Timer(5.5, fun_timer)
    timer.start()

timer = threading.Timer(1, fun_timer)
timer.start()