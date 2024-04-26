from django.shortcuts import render
from django.http import HttpResponse
from .services.prompt_service import Execution
# from . import Execution
# Create your views here.
def login(request):
<<<<<<< HEAD
    print('execution: ', Execution().execute('sms'))
=======
    print('execution: ', Execution().execute('whatsapp'))
>>>>>>> a6b0615 (continued working on sending sms)
    return HttpResponse('login')

def register(request):
    print('body',request)
    return HttpResponse('register')