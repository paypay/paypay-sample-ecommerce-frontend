from .base import Resource
from ..constants.url import URL
import datetime


class Payment(Resource):
    def __init__(self, client=None):
        super(Payment, self).__init__(client)
        self.base_url = URL.PAYMENT

    def create(self, data={}, **kwargs):
        url = "{}/{}".format(self.base_url, 'payments')
        return self.post_url(url, data, **kwargs)

    def get_payment_details(self, id, **kwargs):
        url = "{}/{}/{}".format(self.base_url, 'payments', id)
        return self.fetch(None, url, **kwargs)

    def cancel_payment(self, id, **kwargs):
        url = "{}/{}".format('payments', id)
        return self.delete(None, url, **kwargs)

    def refund_payment(self, data={}, **kwargs):
        url = "/{}".format('refunds')
        data['requestedAt'] = datetime.datetime.now().second
        return self.post_url(url, data, **kwargs)

    def refund_details(self, data={}, **kwargs):
        url = "/{}".format('refunds')
        return self.fetch(url, data, **kwargs)
