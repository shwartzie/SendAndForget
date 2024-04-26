from django.core.mail import send_mail
import phonenumbers
<<<<<<< HEAD
=======
from twilio.rest import Client
from django.conf import settings
import requests
from django.http import JsonResponse
import httpx
twilio_sid = settings.TWILIO_ACCOUNT_SID
twilio_auth_token = settings.TWILIO_AUTH_TOKEN
twilio_sandbox_phone_number = settings.TWILIO_SANDBOX_NUMBER
client = Client(twilio_sid, twilio_auth_token)

>>>>>>> a6b0615 (continued working on sending sms)
ISRAEL_SMS_GATEWAY_DOMAINS = {
    'Cellcom': 'cellcom.co.il',
    'Orange': 'orange.net.il',
    'Pelephone': 'pelephone.co.il',
    'Golan Telecom': 'golantelecom.co.il',
<<<<<<< HEAD
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
=======
    'HOT Telecom': 'hotmobile.co.il',
}


class Messenger():

    def __init__(self, service):
        print('Messenger', service)
        self.service = MessengerFactory.create_messenger(service)
        return

    def send_message(self, reciepentData, user):
        return self.service.send_message(reciepentData, user)

    def get_israeli_sms_gateway(self, user_phone_number, reciepent_phone_number):
        try:
            print('phone_number', user_phone_number)
            user_carrier = client.lookups \
                .v2 \
                .phone_numbers(str(user_phone_number)) \
                .fetch(fields='line_type_intelligence')

            user_carrier_name = user_carrier.line_type_intelligence['carrier_name']
            user_carrier_domain = ISRAEL_SMS_GATEWAY_DOMAINS.get(
                user_carrier_name)

            reciepent_carrier = client.lookups \
                .v2 \
                .phone_numbers(str(reciepent_phone_number)) \
                .fetch(fields='line_type_intelligence')

            reciepent_carrier_name = reciepent_carrier.line_type_intelligence['carrier_name']
            reciepent_carrier_domain = ISRAEL_SMS_GATEWAY_DOMAINS.get(
                reciepent_carrier_name)

            print('carrier_domain', user_carrier_domain)
            return {
                'user': f"{user_phone_number}@{user_carrier_domain}",
                'reciepent': f"{reciepent_phone_number}@{reciepent_carrier_domain}"
            }

        except Exception as e:
            print('Failed to get gateway: %s' % e)
            raise e
>>>>>>> a6b0615 (continued working on sending sms)


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
<<<<<<< HEAD
class WhatsAppMessenger:
    def send_message(self, reciepentData, user):
        try:
            print(f"Sending WhatsApp message to {reciepentData.phone}: {reciepentData.prompt}")
=======


class WhatsAppMessenger(Messenger):
    def __init__(self):
        return

    def send_message(self, recipient, user):
        try:
            api_url = 'https://graph.facebook.com/v18.0/315951958261167/messages'
            headers =  {'Authorization': f'Authorization: Bearer {access_token}', 'Content-Type': 'application/json'}
            payload = {
                'to': str(user.phone),
                'message': recipient.prompt,
                'messaging_product': "whatsapp",
                "type": "template",
                "template": {"name": "hello_world", "language": {"code": "en_US"}}
            }

            try:
                print('sending request')
                response = requests.post(api_url, json=payload,headers=headers)
                response.raise_for_status() 
                print('response', response)

            except Exception as e:
                raise e
                # return JsonResponse({'success': False, 'error': str(e)})
            # message = client.messages.create(
            #     body=recipient.prompt,
            #     from_=f'whatsapp:{twilio_sandbox_phone_number}',
            #     to=f'whatsapp:{user.phone}'
            # )
            print(f"Sending WhatsApp message to {user.phone}: {recipient.prompt}")
>>>>>>> a6b0615 (continued working on sending sms)
            return True
        except Exception as e:
            print(f"Error sending WhatsApp message: {e}")
            return False

<<<<<<< HEAD
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
=======

class SMSMessenger(Messenger):
    def __init__(self):
        return

    def send_message(self, reciepent, user):
        try:

            carrier_domain = self.get_israeli_sms_gateway(
                user.phone, reciepent.phone)
            print('send_message carrier_domain', carrier_domain)

            send_mail('SMS', reciepent.prompt,
                      carrier_domain['user'], [carrier_domain['reciepent']])

>>>>>>> a6b0615 (continued working on sending sms)
            return True
        except Exception as e:
            print(f"Error sending SMS: {e}")
            return False

<<<<<<< HEAD
class FacebookMessenger:
=======

class FacebookMessenger(Messenger):
    def __init__(self):
        return

>>>>>>> a6b0615 (continued working on sending sms)
    def send_message(self, recipient, message):
        try:
            print(f"Sending Facebook message to {recipient}: {message}")
            return True
        except Exception as e:
            # If an error occurs during sending, return False
            print(f"Error sending Facebook message: {e}")
<<<<<<< HEAD
            return False
=======
            return False
>>>>>>> a6b0615 (continued working on sending sms)
