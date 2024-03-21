from django.contrib.auth import authenticate, login
from django.contrib.auth.models import User
from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
import json
@csrf_exempt 
def register(request):
    if request.method == 'POST':
        body = json.loads(request.body)
        username = body.get('username')
        password = body.get('password')
        phone = body.get('phone')

        # Check if username is already taken
        if User.objects.filter(username=username).exists():
            return JsonResponse({'error': 'Username already taken'}, status=400)

        # Create the user
        user = User.objects.create_user(username=username, password=password, phone=phone)
        user.save()
        return JsonResponse({'success': 'User created successfully'}, status=201)

    return JsonResponse({'error': 'Invalid request'}, status=400)

@csrf_exempt 
def user_login(request):
    print(request)
    if request.method == 'POST':
        body = json.loads(request.body)
        username = body.get('username')
        password = body.get('password')
        print("username",username,"password",password)
        return JsonResponse({'success': 'Login successful'}, status=200)
        # Authenticate user
        user = authenticate(request, username=username, password=password)

        if user is not None:
            login(request, user)
            return JsonResponse({'success': 'Login successful'}, status=200)
        else:
            return JsonResponse({'error': 'Invalid credentials'}, status=400)

    return JsonResponse({'error': 'Invalid request'}, status=400)
