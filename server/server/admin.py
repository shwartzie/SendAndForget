from django.contrib import admin

# Register your models here.

from .models import User,Prompt,UserPrompt

admin.site.register(User)
admin.site.register(Prompt)
admin.site.register(UserPrompt)