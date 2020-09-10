from tkinter import *
from tkinter import filedialog as fd
import pyrebase
from datetime import datetime


months_eng = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"]
months_tr = ["Ocak", "Şubat", "Mart", "Nisan", "Mayıs", "Haziran", "Temmuz", "Ağustos", "Eylül", "Ekim", "Kasım", "Aralık"]

def to_ampm(hour, minute):
    h = int(hour)
    m = int(minute)
    if (h is 0):
        return "12:{} AM".format(m)
    elif (h < 12):
        return "{}:{} AM".format(h, m)
    elif (h is 12):
        return "12:{} PM".format(m)
    else:
        return "{}:{} PM".format(h - 12, m)

def getDate(tr):
    if tr:
        now = datetime.now()
    else:
        now = datetime.utcnow()

    year = now.strftime("%Y")
    month = now.strftime("%m")
    day = now.strftime("%d")
    hour = now.strftime("%H")
    minute = now.strftime("%M")

    if tr:
        wmonth = months_tr[int(month) - 1]
        hm = "{}:{}".format(hour, minute)
        date = "{} {} {}, {}".format(day, wmonth, year, hm)
    else:
        wmonth = months_eng[int(month) - 1]
        hm = to_ampm(hour, minute)
        date = "{} on {} {}, {}".format(hm, wmonth, day, year)

    return date

def structNews(date, id, out_link, visible_link, img_name, summary, text, src):
    data = {
        "date" : date,
        "id" : str(id),
        "out_link" : out_link,
        "visible_link" : visible_link,
        "img_name" : img_name,
        "summary" : summary,
        "text" : text,
        "source" : src
    }

    return data




def getLastId(database, tr):
    if tr:
        res = database.child("tr_news_id_last").get()
    else:
        res = database.child("eng_news_id_last").get()

    return res.val()

def getLastBatchId(database, tr):
    if tr:
        res = database.child("tr_news_batch_id_last").get()
    else:
        res = database.child("eng_news_batch_id_last").get()

    return res.val()


def insertNews(fbase, out_link, visible_link, img_path, summary, text, tr, src, dv):
    database = fbase.database();
    storage = fbase.storage();

    news_id_last = getLastId(database, tr)
    batch_id_last = getLastBatchId(database, tr)


    date = ""
    if dv == "":
        date = getDate(tr)
    else:
        date = dv

    id = news_id_last + 1

    print("With id:" + str(id))
    extension = img_path.split(".")[-1]

    if tr:
        img_name = "tr_" + str(id) + "." + extension
    else:
        img_name = "eng_" + str(id) + "." + extension

    storage.child("" + img_name).put(img_path)
    print("Image upload done.")

    newsData = structNews(date, id, out_link, visible_link, img_name, summary, text, src)

    if tr:
        last_batch = database.child("tr_news").child(str(batch_id_last)).get().val()
        if last_batch is None:
            cnt = 0
        else:
            cnt = len(last_batch)

        if cnt > 7:
            # Create new batch and insert news data in it.
            database.child("tr_news").child(str(batch_id_last+1)).child(id).set(newsData)
            database.child("tr_news_id_last").set(id)
            database.child("tr_news_batch_id_last").set(batch_id_last+1)
        else:
            database.child("tr_news").child(str(batch_id_last)).child(id).set(newsData)
            database.child("tr_news_id_last").set(id)
    else:
        last_batch = database.child("eng_news").child(str(batch_id_last)).get().val()
        if last_batch is None:
            cnt = 0
        else:
            cnt = len(last_batch)

        if cnt > 7:
            # Create new batch and insert news data in it.
            database.child("eng_news").child(str(batch_id_last+1)).child(id).set(newsData)
            database.child("eng_news_id_last").set(id)
            database.child("eng_news_batch_id_last").set(batch_id_last+1)
        else:
            database.child("eng_news").child(str(batch_id_last)).child(id).set(newsData)
            database.child("eng_news_id_last").set(id)

    print("Article insertion done.")


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


def intest():
    olink = "subdomain.test.com"
    vlink = "test.com"
    img = "dali.jpg"
    summary = "trenger jeg det? jeg vet ikke."
    text = ""
    tr = True

    insertNews(fb, olink, vlink, img, summary, text, tr)


root=Tk()
root.title("CoronAid Insert Article")
frame = Frame(root, width=1120, height=540)
frame.pack()

olink = StringVar()
vlink = StringVar()
img_path = StringVar()
summary = StringVar()
text = StringVar()
dateV = StringVar()
lang = IntVar()
source = IntVar()

def set_filepath(e):
    file = fd.askopenfilename()
    e.delete(0, END)
    e.insert(0, file)
    return


def upload_news(fb, ol, vl, ip, smm, tb, ln, sr, dv):
    ol_ = ol.get()
    vl_ = vl.get()
    ip_ = ip.get()
    smm_ = smm.get()
    tb_ = tb.get("1.0","end-1c")
    ln_ = ln.get()
    sr_ = sr.get()
    dv_ = dv.get()

    print("\nOut link: " + ol_)
    print("Visible link: " + vl_)
    print("Image path: " + ip_)
    print("Summary: " + smm_)
    print("Content: " + tb_)
    print("Language(tr=1, eng=0): " + str(ln_))
    print("Source(our=0, ext=1): " + str(sr_))


    if ln_ is 0:
        tr_ = False
    else:
        tr_ = True


    if dv_ == "":
        print(getDate(tr_))
    else:
        print(dv_)

    print("")

    if sr_ is 0:
        src_ = "our"
    else:
        src_ = "ext"

    insertNews(fb, ol_, vl_, ip_, smm_, tb_, tr_, src_, dv_)

olink_label = Label(frame, text="Out link:")
olink_label.config(anchor=E)
olink_label.place(x=10, y=10, height=30, width=80)

olink_entry = Entry(frame, textvariable=olink)
olink_entry.place(x=90, y=10, height=28, width=900)

vlink_label = Label(frame, text="Visible link:")
vlink_label.config(anchor=E)
vlink_label.place(x=10, y=40, height=30, width=80)

vlink_entry = Entry(frame, textvariable=vlink)
vlink_entry.place(x=90, y=40, height=28, width=900)

img_path_label = Label(frame, text="Image path:")
img_path_label.config(anchor=E)
img_path_label.place(x=10, y=70, height=30, width=80)

img_path_entry = Entry(frame, textvariable=img_path)
img_path_entry.place(x=90, y=70, height=28, width=900)

img_path_button = Button(frame, text="Browse", command=lambda:set_filepath(img_path_entry))
img_path_button.place(x=1000, y=70, height=28, width=100)

summary_label = Label(frame, text="Summary:")
summary_label.config(anchor=E)
summary_label.place(x=10, y=100, height=30, width=80)

summary_entry = Entry(frame, textvariable=summary)
summary_entry.place(x=90, y=100, height=28, width=900)

text_label = Label(frame, text="Content:")
text_label.config(anchor=E)
text_label.place(x=10, y=130, height=30, width=80)

text_box = Text(frame)
text_box.place(x=90, y=130, height=300, width=900)


lang_label = Label(frame, text="Language:")
lang_label.config(anchor=E)
lang_label.place(x=10, y=430, height=30, width=80)


date_label = Label(frame, text="Date:")
date_label.config(anchor=E)
date_label.place(x=330, y=430, height=30, width=80)

date_entry = Entry(frame, textvariable=dateV)
date_entry.place(x=410, y=430, height=28, width=200)


r2 = Radiobutton(frame, text="English", value=0, var=lang)
r1 = Radiobutton(frame, text="Turkish", value=1, var=lang)

r2.config(anchor=W)
r1.config(anchor=W)

r2.pack()
r2.place(x=170, y=430, height=30, width=150)
r1.place(x=90, y=430, height=30, width=80)


source_label = Label(frame, text="Source:")
source_label.config(anchor=E)
source_label.place(x=10, y=460, height=30, width=80)


def our_visible(oe, ve, tb):
    oe.delete(0, 'end')
    oe = Entry(frame, textvariable=olink, state='disabled')
    oe.place(x=90, y=10, height=28, width=900)

    ve.delete(0, 'end')
    ve = Entry(frame, textvariable=vlink, state='disabled')
    ve.place(x=90, y=40, height=28, width=900)

    tb.config(state=NORMAL)

def ext_visible(oe, ve, tb):
    oe = Entry(frame, textvariable=olink)
    oe.place(x=90, y=10, height=28, width=900)

    ve = Entry(frame, textvariable=vlink)
    ve.place(x=90, y=40, height=28, width=900)

    tb.delete(1.0, 'end')
    tb.config(state=DISABLED)


trex_label = Label(frame, text="Tr: 29 Mart 2020, 16:34")
trex_label.config(anchor=W)
trex_label.place(x=410, y=460, height=30, width=170)

enex_label = Label(frame, text="En: 4:34 PM on March 29, 2020")
enex_label.config(anchor=W)
enex_label.place(x=550, y=460, height=30, width=170)

r3 = Radiobutton(frame, text="External", value=1, var=source, command=lambda:ext_visible(olink_entry, vlink_entry, text_box))
r4 = Radiobutton(frame, text="Ours", value=0, var=source, command=lambda:our_visible(olink_entry, vlink_entry, text_box))

r3.config(anchor=W)
r4.config(anchor=W)

r3.pack()
r3.place(x=170, y=460, height=30, width=150)
r4.place(x=90, y=460, height=30, width=80)

upload_button = Button(frame, text="Upload", command=lambda:upload_news(fb, olink, vlink, img_path, summary, text_box, lang, source, dateV))
upload_button.place(x=90, y=490, height=30, width=80)

our_visible(olink_entry, vlink_entry, text_box)

mainloop()


print("exiting.")
