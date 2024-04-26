from django.db import models
from phonenumber_field.modelfields import PhoneNumberField
# Create your models here.
from django.utils import timezone

class User(models.Model):
    name = models.CharField(max_length=200)
    phone = PhoneNumberField(null=True, blank=False, unique=True)
    email = models.EmailField(null=True, max_length=254, unique=True)
    updated = models.DateTimeField(auto_now=True)
    created_at = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return self.name


class Prompt(models.Model):
    prompt = models.TextField(max_length=400, blank=True, null=True)
    phone = PhoneNumberField(null=True, blank=False, unique=True)
    execution_date = models.DateField(null=False, default=timezone.now)
    execution_time = models.TimeField(null=False, default=timezone.now)
    updated = models.DateTimeField(auto_now=True)
    created_at = models.DateTimeField(auto_now_add=True)
    completed = models.BooleanField(default=False)
    
    def __str__(self):
        return self.prompt


class UserPrompt(models.Model):
    user_id = models.OneToOneField(
        User, on_delete=models.CASCADE, null=True, unique=True)
    prompt_id = models.OneToOneField(
        Prompt, on_delete=models.CASCADE, null=True, unique=True)

    def save(self, *args, **kwargs):
        if self.user_id_id is None or self.prompt_id_id is None:
            raise ValueError(
                "Both user_id and prompt_id must be set before saving.")
        return super().save(*args, **kwargs)

    def __str__(self):
        return f"User: {self.user}, Prompt: {self.prompt}"
