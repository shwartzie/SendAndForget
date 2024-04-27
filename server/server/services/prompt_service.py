
from ..models import Prompt, UserPrompt, User
from django.db import connection
import datetime
from .messenger import Messenger
from .user_service import UserService


class Execution:

    def __init__(self):
        self.reciepentData = self.get_next()
        self.user_service = UserService()
        self.user = self.user_service.get_user_by_prompt_id(self.reciepentData.id)

    def execute(self, service):
        print('Prompt : %s' % service, self.reciepentData.phone,
              self.reciepentData.prompt)
        print('User: %s' % self.user)
        try:
            messenger = Messenger(service)
            isOk = messenger.send_message(self.reciepentData, self.user)
            if not isOk:
                raise Exception('Send Message failed')

        except Exception as error:
            print('Executing prompt failed: %s', error)

        # print('self.get_prompt() ', self.get_prompt())
        # prompt = self.get_prompt()
        # prompt.completed = True
        # prompt.save()
        # next = self.get_next()
        # if (next):
        #     self.execute(Execution())
        return isOk

    def get_next(self):
        current_date = datetime.date.today()
        current_time = datetime.datetime.now().time()
        query = """
                SELECT * FROM server_prompt WHERE completed = false 
                AND execution_date >= %s AND execution_time >= %s
                LIMIT 1
        """

        with connection.cursor() as cursor:
            cursor.execute(query, [current_date, str(current_time)])
            row = cursor.fetchone()
            if row:
                print('row', row[0])
                return Prompt.objects.get(pk=row[0])
            else:
                return None

    def get_prompt(self):
        return Prompt.objects.get(pk=self.reciepentData.id)
