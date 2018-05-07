from datetime import date
from apscheduler.scheduler import Scheduler

# 启动Scheduler
sched = Scheduler()
sched.start()

def job_function():
   print ("Hello World")

# job_function将会每两小时执行一次
sched.add_interval_job(job_function, hours=2)

# 与上面的任务相同，不过规定在2013-5-17 18:30之后才开始运行
sched.add_interval_job(job_function, hours=2, start_date='2013-5-17 18:30')