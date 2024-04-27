
from ..models import User, UserPrompt


class UserService(object):

    def __init__(self):
        self = self
        return

    def get_by_id(self, user_id):
        return User.objects.get(pk=user_id)

    def get_prompt_by_id(self, prompt_id):
        return UserPrompt.objects.get(prompt_id=prompt_id)

    def get_user_by_prompt_id(self, prompt_id):
        return self.get_by_id(self.get_prompt_by_id(prompt_id).user_id)
