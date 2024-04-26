
class Messenger:

    def __init__(self, service):
        self.service = MessengerFactory.create_messenger(service)
        return

    def send_message(self, recipient, message):
        return self.service.send_message(recipient, message)


class MessengerFactory:
    @staticmethod
    def create_messenger(service):
        if str.lower(service) == 'whatsapp':
            return WhatsAppMessenger()
        elif service == 'sms':
            return SMSMessenger()
        elif service == 'facebook':
            return FacebookMessenger()
        else:
            raise ValueError("Invalid messaging service")


# Implement concrete messenger classes
class WhatsAppMessenger:
    def send_message(self, recipient, message):
        try:
            print(f"Sending WhatsApp message to {recipient}: {message}")
            return True
        except Exception as e:
            print(f"Error sending WhatsApp message: {e}")
            return False

class SMSMessenger:
    def send_message(self, recipient, message):
        try:
            print(f"Sending SMS to {recipient}: {message}")
            return True
        except Exception as e:
            print(f"Error sending SMS: {e}")
            return False

class FacebookMessenger:
    def send_message(self, recipient, message):
        try:
            print(f"Sending Facebook message to {recipient}: {message}")
            return True
        except Exception as e:
            # If an error occurs during sending, return False
            print(f"Error sending Facebook message: {e}")
            return False