# Generated by Django 5.0.4 on 2024-04-26 10:53

import datetime
import django.utils.timezone
from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('server', '0006_prompt_execution_date_alter_prompt_execution_time'),
    ]

    operations = [
        migrations.AlterField(
            model_name='prompt',
            name='execution_date',
            field=models.DateField(default=django.utils.timezone.now),
        ),
        migrations.AlterField(
            model_name='prompt',
            name='execution_time',
            field=models.TimeField(default=datetime.time(10, 53, 9, 126446)),
        ),
    ]
