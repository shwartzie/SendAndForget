from django.core.mail import send_mail
import phonenumbers
ISRAEL_SMS_GATEWAY_DOMAINS = {
    'Cellcom': 'cellcom.co.il',
    'Orange': 'orange.net.il',
    'Pelephone': 'pelephone.co.il',
    'Golan Telecom': 'golantelecom.co.il',
    'Hot Mobile': 'hotmobile.co.il',
}


class Messenger(object):

    def __init__(self, service):
        self.service = MessengerFactory.create_messenger(service)
        return

    def send_message(self, reciepentData,user):
        return self.service.send_message(reciepentData,user)

    def get_israeli_sms_gateway(self, phone_number):
        try:
            parsed_number = phonenumbers.parse(phone_number, None)
            carrier = parsed_number.carrier
            carrier_domain = ISRAEL_SMS_GATEWAY_DOMAINS.get(carrier)
            print('parsed_number %s' % parsed_number % 'carrier %s' % (carrier, carrier_domain))
            if carrier_domain:
                return f"{phone_number}@{carrier_domain}"
            else:
                return None  # Carrier not found in the mapping
        except phonenumbers.phonenumberutil.NumberParseException:
            return None  # Invalid phone number format


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
    def send_message(self, reciepentData, user):
        try:
            print(f"Sending WhatsApp message to {reciepentData.phone}: {reciepentData.prompt}")
            return True
        except Exception as e:
            print(f"Error sending WhatsApp message: {e}")
            return False

class SMSMessenger(object):
    def send_message(self, reciepentData, user):
        try:
            print(super(Messenger,self))
            carrier_domain = super(Messenger,self).get_israeli_sms_gateway(user.phone)
            print(f"Sending SMS to {reciepentData.phone}: {reciepentData.prompt} from {user.name} number {user.phone}")
            recipient_email = f"{carrier_domain}"
            send_mail('SMS', reciepentData.prompt, user.email, [recipient_email])
             
            # send_sms(
            #     reciepentData.prompt,
            #     user.phone,
            #     [reciepentData.phone],
            #     fail_silently=False
            # )
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