# Generated by Django 5.0.4 on 2024-04-26 10:51

import datetime
from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('server', '0005_remove_userprompt_prompt_remove_userprompt_user_and_more'),
    ]

    operations = [
        migrations.AddField(
            model_name='prompt',
            name='execution_date',
            field=models.DateField(default=datetime.date(2024, 4, 26)),
        ),
        migrations.AlterField(
            model_name='prompt',
            name='execution_time',
            field=models.TimeField(default=datetime.time(10, 51, 36, 457072)),
        ),
    ]