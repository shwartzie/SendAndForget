from django.shortcuts import render
from django.http import HttpResponse
from .services.prompt_service import Execution
# from . import Execution
# Create your views here.
def login(request):
    print('execution: ', Execution().execute('sms'))
    return HttpResponse('login')

def register(request):
    print('body',request)
    return HttpResponse('register')