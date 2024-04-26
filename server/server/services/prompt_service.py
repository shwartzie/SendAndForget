
from ..models import Prompt
from django.db import connection
import datetime
from .messenger import Messenger
class Execution:

    def __init__(self):
        self.prompt = self.get_next()

    def execute(self, service):
        print('Prompt : %s' % service, self.prompt.phone, self.prompt.prompt)
        
        try:
            messenger = Messenger(service)
            status = messenger.send_message(self.prompt.phone,self.prompt.prompt)
        except Exception as error:
            print('Executing prompt failed: %s', error)

        # print('self.get_prompt() ', self.get_prompt())
        # prompt = self.get_prompt()
        # prompt.completed = True
        # prompt.save()
        # next = self.get_next()
        # if (next):
        #     self.execute(Execution())
        return

    def get_next(self):
        current_date = datetime.date.today()
        current_time = datetime.datetime.now().time()
        query = """
                SELECT * FROM server_prompt WHERE completed = false 
                AND execution_date >= %s AND execution_time >= %s
                LIMIT 1
        """

        with connection.cursor() as cursor:
            cursor.execute(query,[current_date, str(current_time)])
            row = cursor.fetchone()
            if row:
               print('row', row[0])
               return Prompt.objects.get(pk=row[0])
            else:
                return None
            
    def get_prompt(self):
        return Prompt.objects.get(pk=self.prompt.id)

