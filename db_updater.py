import urllib.request as urllib
from html.parser import HTMLParser
import pyrebase

class HTMLTableParser(HTMLParser):
    """ This class serves as a html table parser. It is able to parse multiple
    tables which you feed in. You can access the result per .tables field.
    """
    def __init__(
        self,
        decode_html_entities=False,
        data_separator=' ',
    ):

        HTMLParser.__init__(self)

        self._parse_html_entities = decode_html_entities
        self._data_separator = data_separator

        self._in_td = False
        self._in_th = False
        self._current_table = []
        self._current_row = []
        self._current_cell = []
        self.tables = []

    def handle_starttag(self, tag, attrs):
        """ We need to remember the opening point for the content of interest.
        The other tags (<table>, <tr>) are only handled at the closing point.
        """
        if tag == 'td':
            self._in_td = True
        if tag == 'th':
            self._in_th = True

    def handle_data(self, data):
        """ This is where we save content to a cell """
        if self._in_td or self._in_th:
            self._current_cell.append(data.strip())

    def handle_charref(self, name):
        """ Handle HTML encoded characters """

        if self._parse_html_entities:
            self.handle_data(self.unescape('&#{};'.format(name)))

    def handle_endtag(self, tag):
        """ Here we exit the tags. If the closing tag is </tr>, we know that we
        can save our currently parsed cells to the current table as a row and
        prepare for a new row. If the closing tag is </table>, we save the
        current table and prepare for a new one.
        """
        if tag == 'td':
            self._in_td = False
        elif tag == 'th':
            self._in_th = False

        if tag in ['td', 'th']:
            final_cell = self._data_separator.join(self._current_cell).strip()
            self._current_row.append(final_cell)
            self._current_cell = []
        elif tag == 'tr':
            self._current_table.append(self._current_row)
            self._current_row = []
        elif tag == 'table':
            self.tables.append(self._current_table)
            self._current_table = []


def structCD(name, tc, nc, td, nd, tr, ac, sc, ratio):
    # name: country name
    # tc: total case
    # nc: new case
    # td: total death
    # nd: new death
    # tr: total recover
    # ac: active case
    # sc: serious critical
    # ratio: case in each 1 mil
    data = {
        "name" : name,
        "tc" : tc,
        "nc" : nc,
        "td" : td,
        "nd" : nd,
        "tr" : tr,
        "ac" : ac,
        "sc" : sc,
        "ratio" : ratio
    }

    return data


def urlCompatKey(cname):
    res = ""

    for c in cname:
        ascii_val = ord(c)
        if ((ascii_val >= 65 and ascii_val<=90) or (ascii_val>=97 and ascii_val<=122)):
            res += c

    return res

def updateCD(database):
    cdURL = "https://www.worldometers.info/coronavirus/"

    headers = {}
    headers['User-Agent'] = "Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.27 Safari/537.17"

    req = urllib.Request(cdURL, headers = headers)
    resp = urllib.urlopen(req)
    xhtml = resp.read().decode('utf-8')

    hparser = HTMLTableParser()
    hparser.feed(xhtml)

    titles = hparser.tables[0][0]
    clist = hparser.tables[0][1:]

    for country in clist:
        name = country[0]
        tc = country[1]
        nc = country[2]
        td = country[3]
        nd = country[4]
        tr = country[5]
        ac = country[6]
        sc = country[7]
        ratio = country[8]

        currentCData = structCD(name, tc, nc, td, nd, tr, ac, sc, ratio)
        key = urlCompatKey(name)
        database.child("countryData").child(key).set(currentCData)
        print("DONE: {}".format(name))


api_key = "AIzaSyCRzwAs_Gc11stMZJl74T3tR38HP3mKGdw"
auth_domain = "coronaid-fabfc.firebaseapp.com"
dbURL = "https://coronaid-fabfc.firebaseio.com"
storage = "coronaid-fabfc.appspot.com"
serviceAccount = "coronaid-fabfc-firebase-adminsdk-omnic-488ee8322f.json"

config = {
    "apiKey" : api_key,
    "authDomain" : auth_domain,
    "databaseURL" : dbURL,
    "storageBucket" : storage,
    "serviceAccount" : serviceAccount
}

fb = pyrebase.initialize_app(config)
db = fb.database()
updateCD(db)
