#coding=utf-8
import itchat
from itchat.content import *

@itchat.msg_register([PICTURE,TEXT])
def simple_reply(msg):
    if msg['Type'] == TEXT:
        ReplyContent = 'I received message: '+msg['Content']
    if msg['Type'] == PICTURE:
        ReplyContent = 'I received picture: '+msg['FileName']
    itchat.send_msg(ReplyContent,msg['FromUserName'])

    users = itchat.search_friends(name=u'儿时的回忆')
    userName = users[0]['UserName']
    itchat.send('hello',toUserName = userName)

itchat.auto_login()
itchat.run()