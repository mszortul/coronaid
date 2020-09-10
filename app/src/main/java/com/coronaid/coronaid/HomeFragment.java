package com.coronaid.coronaid;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class HomeFragment extends Fragment {

    public HomeFragment() {}


    public final HashMap<String, Integer> nameToFlag = new HashMap<String, Integer>() {{
        put("Afghanistan", R.drawable.af);
        put("Albania", R.drawable.al);
        put("Algeria", R.drawable.dz);
        //put("AmericanSamoa", R.drawable.as);
        put("Andorra", R.drawable.ad);
        put("Angola", R.drawable.ao);
        //put("Anguilla", R.drawable.ai);
        //put("Antarctica", R.drawable.aq);
        put("AntiguaandBarbuda", R.drawable.ag);
        put("Argentina", R.drawable.ar);
        put("Armenia", R.drawable.am);
        put("Aruba", R.drawable.aruba);
        put("Australia", R.drawable.au);
        put("Austria", R.drawable.at);
        put("Azerbaijan", R.drawable.az);
        put("Bahamas", R.drawable.bs);
        put("Bahrain", R.drawable.bh);
        put("Bangladesh", R.drawable.bd);
        put("Barbados", R.drawable.bb);
        put("Belarus", R.drawable.by);
        put("Belgium", R.drawable.be);
        put("Belize", R.drawable.bz);
        put("Benin", R.drawable.bj);
        //put("Bermuda", R.drawable.bm);
        put("Bhutan", R.drawable.bt);
        put("Bolivia", R.drawable.bo);
        put("BosniaandHerzegovina", R.drawable.ba);
        put("Botswana", R.drawable.bw);
        //put("Bouvet Island", R.drawable.bv);
        put("Brazil", R.drawable.br);
        //put("British Indian Ocean Territory", R.drawable.io);
        put("Brunei", R.drawable.bn);
        put("Bulgaria", R.drawable.bg);
        put("BurkinaFaso", R.drawable.bf);
        put("Burundi", R.drawable.bi);
        put("Cambodia", R.drawable.kh);
        put("Cameroon", R.drawable.cm);
        put("Canada", R.drawable.ca);
        put("CapeVerde", R.drawable.cv);
        put("CaymanIslands", R.drawable.cayman);
        put("CAR", R.drawable.cf);
        put("Curaao", R.drawable.cura);
        put("Chad", R.drawable.td);
        //put("ChannelIslands", R.drawable.top_app_logo);
        put("Chile", R.drawable.cl);
        put("China", R.drawable.cn);
        //put("Christmas Island", R.drawable.cx);
        //put("Cocos (Keeling) Islands", R.drawable.cc);
        put("Colombia", R.drawable.co);
        put("Comoros", R.drawable.km);
        put("Congo", R.drawable.cg);
        put("CookIslands", R.drawable.ck);
        put("CostaRica", R.drawable.cr);
        put("Croatia", R.drawable.hr);
        put("Cuba", R.drawable.cu);
        put("Cyprus", R.drawable.cy);
        put("Czechia", R.drawable.cz);
        put("Denmark", R.drawable.dk);
        //put("DiamondPrincess", R.drawable.top_app_logo);
        put("Djibouti", R.drawable.dj);
        put("Dominica", R.drawable.dm);
        put("DominicanRepublic", R.drawable.do_);
        put("EastTimor", R.drawable.tl);
        put("Ecuador", R.drawable.ec);
        put("Egypt", R.drawable.eg);
        put("ElSalvador", R.drawable.sv);
        put("EquatorialGuinea", R.drawable.gq);
        put("Eritrea", R.drawable.er);
        put("Estonia", R.drawable.ee);
        put("Eswatini", R.drawable.eswatini);
        put("Ethiopia", R.drawable.et);
        //put("Falkland Islands", R.drawable.fk);
        put("FijiIslands", R.drawable.fj);
        put("FaeroeIslands", R.drawable.faeroe);
        put("Finland", R.drawable.fi);
        put("France", R.drawable.fr);
        put("FrenchGuiana", R.drawable.fgui);
        put("FrenchPolynesia", R.drawable.fpoly);
        //put("French Southern territories", R.drawable.tf);
        put("Gabon", R.drawable.ga);
        put("Gambia", R.drawable.gm);
        put("Georgia", R.drawable.ge);
        put("Germany", R.drawable.de);
        put("Ghana", R.drawable.gh);
        put("Gibraltar", R.drawable.gibraltar);
        put("Greece", R.drawable.gr);
        put("Greenland", R.drawable.greenland);
        put("Grenada", R.drawable.gd);
        //put("Guadeloupe", R.drawable.top_app_logo);
        put("Guam", R.drawable.guam);
        put("Guatemala", R.drawable.gt);
        put("Guinea", R.drawable.gn);
        put("GuineaBissau", R.drawable.gw);
        put("Guyana", R.drawable.gy);
        put("Haiti", R.drawable.ht);
        //put("Heard Island and McDonald Islands", R.drawable.hm);
        put("VaticanCity", R.drawable.va);
        put("Honduras", R.drawable.hn);
        put("HongKong", R.drawable.hk);
        put("Hungary", R.drawable.hu);
        put("Iceland", R.drawable.is);
        put("India", R.drawable.in);
        put("Indonesia", R.drawable.id);
        put("Iran", R.drawable.ir);
        put("Iraq", R.drawable.iq);
        put("Ireland", R.drawable.ie);
        put("Israel", R.drawable.il);
        put("Italy", R.drawable.it);
        put("IvoryCoast", R.drawable.ci);
        put("Jamaica", R.drawable.jm);
        put("Japan", R.drawable.jp);
        put("Jordan", R.drawable.jo);
        put("Kazakhstan", R.drawable.kz);
        put("Kenya", R.drawable.ke);
        put("Kiribati", R.drawable.ki);
        put("Kuwait", R.drawable.kw);
        put("Kyrgyzstan", R.drawable.kg);
        put("Laos", R.drawable.la);
        put("Latvia", R.drawable.lv);
        put("Lebanon", R.drawable.lb);
        put("Lesotho", R.drawable.ls);
        put("Liberia", R.drawable.lr);
        put("LibyanArabJamahiriya", R.drawable.ly);
        put("Liechtenstein", R.drawable.li);
        put("Lithuania", R.drawable.lt);
        put("Luxembourg", R.drawable.lu);
        put("Macao", R.drawable.macao);
        put("NorthMacedonia", R.drawable.mk);
        put("Madagascar", R.drawable.mg);
        put("Malawi", R.drawable.mw);
        put("Malaysia", R.drawable.my);
        put("Maldives", R.drawable.mv);
        put("Mali", R.drawable.ml);
        put("Malta", R.drawable.mt);
        put("MarshallIslands", R.drawable.mh);
        put("Martinique", R.drawable.martinique);
        put("Mauritania", R.drawable.mr);
        put("Mauritius", R.drawable.mu);
        put("Mayotte", R.drawable.mayotte);
        put("Mexico", R.drawable.mx);
        put("Moldova", R.drawable.md);
        put("Monaco", R.drawable.mc);
        put("Mongolia", R.drawable.mn);
        put("Montserrat", R.drawable.montserrat);
        put("Morocco", R.drawable.ma);
        put("Mozambique", R.drawable.mz);
        put("Myanmar", R.drawable.mm);
        put("Namibia", R.drawable.na);
        put("Nauru", R.drawable.nr);
        put("Nepal", R.drawable.np);
        put("Netherlands", R.drawable.nl);
        //put("Netherlands Antilles", R.drawable.an);
        put("NewCaledonia", R.drawable.newcal);
        put("NewZealand", R.drawable.nz);
        put("Nicaragua", R.drawable.ni);
        put("Niger", R.drawable.ne);
        put("Nigeria", R.drawable.ng);
        put("Niue", R.drawable.nu);
        //put("Norfolk Island", R.drawable.nf);
        put("NKorea", R.drawable.kp);
        //put("Northern Mariana Islands", R.drawable.mp);
        put("Norway", R.drawable.no);
        put("Oman", R.drawable.om);
        put("Pakistan", R.drawable.pk);
        put("Palau", R.drawable.pw);
        put("Palestine", R.drawable.ps);
        put("Panama", R.drawable.pa);
        put("PapuaNewGuinea", R.drawable.pg);
        put("Paraguay", R.drawable.py);
        put("Peru", R.drawable.pe);
        put("Philippines", R.drawable.ph);
        //put("Pitcairn", R.drawable.pn);
        put("Poland", R.drawable.pl);
        put("Portugal", R.drawable.pt);
        put("PuertoRico", R.drawable.prico);
        put("Qatar", R.drawable.qa);
        put("Runion", R.drawable.reunion);
        put("Romania", R.drawable.ro);
        put("Russia", R.drawable.ru);
        put("Rwanda", R.drawable.rw);
        //put("Saint Helena", R.drawable.sh);
        put("SaintKittsandNevis", R.drawable.kn);
        put("SaintMartin", R.drawable.smartin);
        put("SaintLucia", R.drawable.lc);
        //put("Saint Pierre and Miquelon", R.drawable.pm);
        put("StVincentGrenadines", R.drawable.vc);
        put("Samoa", R.drawable.ws);
        put("SanMarino", R.drawable.sm);
        put("SaoTomeandPrincipe", R.drawable.st);
        put("SaudiArabia", R.drawable.sa);
        put("Senegal", R.drawable.sn);
        put("Serbia", R.drawable.serbia);
        put("Seychelles", R.drawable.sc);
        put("SierraLeone", R.drawable.sl);
        put("Singapore", R.drawable.sg);
        put("Slovakia", R.drawable.sk);
        put("Slovenia", R.drawable.si);
        put("SolomonIslands", R.drawable.sb);
        put("Somalia", R.drawable.so);
        put("SouthAfrica", R.drawable.za);
        //put("South Georgia and the South Sandwich Islands", R.drawable.gs);
        put("SKorea", R.drawable.kr);
        put("SSudan", R.drawable.ss);
        put("Spain", R.drawable.es);
        put("SriLanka", R.drawable.lk);
        put("StBarth", R.drawable.stbarth);
        put("Sudan", R.drawable.sd);
        put("Suriname", R.drawable.sr);
        //put("Svalbard and Jan Mayen", R.drawable.sj);
        put("Swaziland", R.drawable.sz);
        put("Sweden", R.drawable.se);
        put("Switzerland", R.drawable.ch);
        put("Syria", R.drawable.sy);
        put("Tajikistan", R.drawable.tj);
        put("Tanzania", R.drawable.tz);
        put("Thailand", R.drawable.th);
        put("DRC", R.drawable.cd);
        put("Togo", R.drawable.tg);
        //put("Tokelau", R.drawable.tk);
        put("Tonga", R.drawable.to);
        put("Taiwan", R.drawable.taiwan);
        put("TrinidadandTobago", R.drawable.tt);
        put("Tunisia", R.drawable.tn);
        put("Turkey", R.drawable.tr);
        put("Turkmenistan", R.drawable.tm);
        //put("Turks and Caicos Islands", R.drawable.tc);
        put("Tuvalu", R.drawable.tv);
        put("Uganda", R.drawable.ug);
        put("Ukraine", R.drawable.ua);
        put("UAE", R.drawable.ae);
        put("UK", R.drawable.gb);
        put("USA", R.drawable.us);
        put("Uruguay", R.drawable.uy);
        put("Uzbekistan", R.drawable.uz);
        put("Vanuatu", R.drawable.vu);
        put("Venezuela", R.drawable.ve);
        put("Vietnam", R.drawable.vn);
        //put("Virgin Islands, British", R.drawable.vg);
        put("USVirginIslands", R.drawable.usvirgin);
        //put("Wallis and Futuna", R.drawable.wf);
        put("Western Sahara", R.drawable.eh);
        put("Yemen", R.drawable.ye);
        put("Zambia", R.drawable.zm);
        put("Zimbabwe", R.drawable.zw);
    }};

    public final HashMap<String, String> trNames = new HashMap<String, String>() {{
        put("Afghanistan", "Afganistan");
        put("Albania", "Arnavutluk");
        put("Algeria", "Cezayir");
        //put("AmericanSamoa", "Amerikan Samoası");
        put("Andorra", "Andorra");
        put("Angola", "Angola");
        //put("Anguilla", "Anguilla");
        //put("Antarctica", "Antarktika");
        put("AntiguaandBarbuda", "Antigua ve Barbuda");
        put("Argentina", "Arjantin");
        put("Armenia", "Ermenistan");
        put("Aruba", "Aruba");
        put("Australia", "Avustralya");
        put("Austria", "Avusturya");
        put("Azerbaijan", "Azerbaycan");
        put("Bahamas", "Bahamalar");
        put("Bahrain", "Bahreyn");
        put("Bangladesh", "Bangladeş");
        put("Barbados", "Barbados");
        put("Belarus", "Belarus");
        put("Belgium", "Belçika");
        put("Belize", "Belize");
        put("Benin", "Benin");
        //put("Bermuda", "Bermuda");
        put("Bhutan", "Butan");
        put("Bolivia", "Bolivya");
        put("BosniaandHerzegovina", "Bosna Hersek");
        put("Botswana", "Botsvana");
        //put("Bouvet Island", "Bouvet Adası");
        put("Brazil", "Brezilya");
        //put("British Indian Ocean Territory", R.drawable.io);
        put("Brunei", "Brunei");
        put("Bulgaria", "Bulgaristan");
        put("BurkinaFaso", "BurkinaFaso");
        put("Burundi", "Burundi");
        put("Cambodia", "Kamboçya");
        put("Cameroon", "Kamerun");
        put("Canada", "Kanada");
        put("CapeVerde", "Cape Verde");
        put("CaymanIslands", "Cayman Adaları");
        put("CAR", "Orta Afrika Cumhuriyeti");
        put("Curaao", "Curaçao");
        put("Chad", "Çad");
        put("ChannelIslands", "Manş Adaları");
        put("Chile", "Şili");
        put("China", "Çin");
        //put("Christmas Island", R.drawable.cx);
        //put("Cocos (Keeling) Islands", R.drawable.cc);
        put("Colombia", "Kolombiya");
        put("Comoros", "Komorlar");
        put("Congo", "Kongo");
        put("CookIslands", "Cook Adaları");
        put("CostaRica", "Kosta Rika");
        put("Croatia", "Hırvatistan");
        put("Cuba", "Küba");
        put("Cyprus", "Kıbrıs");
        put("Czechia", "Çekya");
        put("Denmark", "Danimarka");
        put("DiamondPrincess", "Diamond Princess");
        put("Djibouti", "Cibuti");
        put("Dominica", "Dominik");
        put("DominicanRepublic", "Dominik Cumhuriyeti");
        put("EastTimor", "Doğu Timor");
        put("Ecuador", "Ekvador");
        put("Egypt", "Mısır");
        put("ElSalvador", "El Salvador");
        put("EquatorialGuinea", "Ekvator Ginesi");
        put("Eritrea", "Eritre");
        put("Estonia", "Estonya");
        put("Eswatini", "Esvati̇ni̇");
        put("Ethiopia", "Etiyopya");
        //put("Falkland Islands", "Falkland Adaları");
        put("FijiIslands", "Fiji Adaları");
        put("FaeroeIslands", "Faroe Adaları");
        put("Finland", "Finlandiya");
        put("France", "Fransa");
        put("FrenchGuiana", "Fransız Guyanası");
        put("FrenchPolynesia", "Fransız Polinezyası");
        //put("French Southern territories", R.drawable.tf);
        put("Gabon", "Gabon");
        put("Gambia", "Gambiya");
        put("Georgia", "Gürcistan");
        put("Germany", "Almanya");
        put("Ghana", "Gana");
        put("Gibraltar", "Cebelitarık");
        put("Greece", "Yunanistan");
        put("Greenland", "Grönland");
        put("Grenada", "Grenada");
        put("Guadeloupe", "Guadeloupe");
        put("Guam", "Guam");
        put("Guatemala", "Guatemala");
        put("Guinea", "Gine");
        put("GuineaBissau", "Gine Bissau");
        put("Guyana", "Guyana");
        put("Haiti", "Haiti");
//put("Heard Island and McDonald Islands", R.drawable.hm);
        put("VaticanCity", "Vatikan");
        put("Honduras", "Honduras");
        put("HongKong", "Hong Kong");
        put("Hungary", "Macaristan");
        put("Iceland", "İzlanda");
        put("India", "Hindistan");
        put("Indonesia", "Endonezya");
        put("Iran", "İran");
        put("Iraq", "Irak");
        put("Ireland", "İrlanda");
        put("Israel", "İsrail");
        put("Italy", "İtalya");
        put("IvoryCoast", "Fildişi Sahilleri");
        put("Jamaica", "Jamaika");
        put("Japan", "Japonya");
        put("Jordan", "Ürdün");
        put("Kazakhstan", "Kazakistan");
        put("Kenya", "Kenya");
        put("Kiribati", "Kiribati");
        put("Kuwait", "Kuveyt");
        put("Kyrgyzstan", "Kırgızistan");
        put("Laos", "Laos");
        put("Latvia", "Letonya");
        put("Lebanon", "Lübnan");
        put("Lesotho", "Lesotho");
        put("Liberia", "Liberya");
        put("LibyanArabJamahiriya", "Libya");
        put("Liechtenstein", "Lihtenştayn");
        put("Lithuania", "Litvanya");
        put("Luxembourg", "Lüksemburg");
        put("Macao", "Makao");
        put("NorthMacedonia", "Kuzey Makedonya");
        put("Madagascar", "Madagaskar");
        put("Malawi", "Malavi");
        put("Malaysia", "Malezya");
        put("Maldives", "Maldivler");
        put("Mali", "Mali");
        put("Malta", "Malta");
        put("MarshallIslands", "Marşal Adaları");
        put("Martinique", "Martinik");
        put("Mauritania", "Moritanya");
        put("Mauritius", "Morityus");
        put("Mayotte", "Mayotte");
        put("Mexico", "Meksika");
        put("Moldova", "Moldova");
        put("Monaco", "Monako");
        put("Mongolia", "Moğolistan");
        put("Montserrat", "Montserrat");
        put("Morocco", "Fas");
        put("Mozambique", "Mozambik");
        put("Myanmar", "Myanmar");
        put("Namibia", "Namibya");
        put("Nauru", "Nauru");
        put("Nepal", "Nepal");
        put("Netherlands", "Hollanda");
//put("Netherlands Antilles", R.drawable.an);
        put("NewCaledonia", "Yeni Kaledonya");
        put("NewZealand", "Yeni Zelanda");
        put("Nicaragua", "Nikaragua");
        put("Niger", "Nijer");
        put("Nigeria", "Nijerya");
        put("Niue", "Niue");
//put("Norfolk Island", R.drawable.nf);
        put("NKorea", "Kuzey Kore");
//put("Northern Mariana Islands", R.drawable.mp);
        put("Norway", "Norveç");
        put("Oman", "Umman");
        put("Pakistan", "Pakistan");
        put("Palau", "Palau");
        put("Palestine", "Filistin");
        put("Panama", "Panama");
        put("PapuaNewGuinea", "Papua Yeni Gine");
        put("Paraguay", "Paraguay");
        put("Peru", "Peru");
        put("Philippines", "Filipinler");
//put("Pitcairn", R.drawable.pn);
        put("Poland", "Polonya");
        put("Portugal", "Portekiz");
        put("PuertoRico", "Porto Riko");
        put("Qatar", "Katar");
        put("Reunion", "Reunion");
        put("Romania", "Romanya");
        put("Russia", "Rusya");
        put("Rwanda", "Ruanda");
//put("Saint Helena", R.drawable.sh);
        put("SaintKittsandNevis", "SaintKittsveNevis");
        put("SaintMartin", "Saint Martin");
        put("SaintLucia", "Saint Lucia");
//put("Saint Pierre and Miquelon", R.drawable.pm);
        put("StVincentGrenadines", "Saint Vincent ve Grenadinler");
        put("Samoa", "Samoa");
        put("SanMarino", "San Marino");
        put("SaoTomeandPrincipe", "Sao Tome ve Principe");
        put("SaudiArabia", "Suudi Arabistan");
        put("Senegal", "Senegal");
        put("Serbia", "Sırbistan");
        put("Seychelles", "Seyşeller");
        put("SierraLeone", "Sierra Leone");
        put("Singapore", "Singapur");
        put("Slovakia", "Slovakya");
        put("Slovenia", "Slovenya");
        put("SolomonIslands", "Solomon Adaları");
        put("Somalia", "Somali");
        put("SouthAfrica", "Güney Afrika");
//put("South Georgia and the South Sandwich Islands", R.drawable.gs);
        put("SKorea", "Güney Kore");
        put("SSudan", "Güney Sudan");
        put("Spain", "İspanya");
        put("SriLanka", "Sri Lanka");
        put("StBarth", "Saint Barthélemy");
        put("Sudan", "Sudan");
        put("Suriname", "Surinam");
//put("Svalbard and Jan Mayen", R.drawable.sj);
        put("Swaziland", "Esvatini");
        put("Sweden", "İsveç");
        put("Switzerland", "İsviçre");
        put("Syria", "Suriye");
        put("Tajikistan", "Tacikistan");
        put("Tanzania", "Tanzanya");
        put("Thailand", "Tayland");
        put("DRC", "Demokratik Kongo Cumhuriyeti");
        put("Togo", "Togo");
//put("Tokelau", R.drawable.tk);
        put("Tonga", "Tonga");
        put("Taiwan", "Tayvan");
        put("TrinidadandTobago", "Trinidad ve Tobago");
        put("Tunisia", "Tunus");
        put("Turkey", "Türkiye");
        put("Turkmenistan", "Türkmenistan");
//put("Turks and Caicos Islands", R.drawable.tc);
        put("Tuvalu", "Tuvalu");
        put("Uganda", "Uganda");
        put("Ukraine", "Ukrayna");
        put("UAE", "BAE");
        put("UK", "Birleşik Krallık");
        put("USA", "ABD");
        put("Uruguay", "Uruguay");
        put("Uzbekistan", "Özbekistan");
        put("Vanuatu", "Vanuatu");
        put("Venezuela", "Venezuela");
        put("Vietnam", "Vietnam");
//put("Virgin Islands, British", R.drawable.vg);
        put("USVirginIslands", "ABD Virjin Adaları");
//put("Wallis and Futuna", R.drawable.wf);
        put("Western Sahara", "Batı Sahra");
        put("Yemen", "Yemen");
        put("Zambia", "Zambiya");
        put("Zimbabwe", "Zimbabve");
    }};


    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    private RecyclerView recyclerView;
    private ArrayList<CountryData> countryDataArrayList = new ArrayList<>();
    private CountryAdapter countryAdapter;

    TextView totalInfections, totalDeaths, totalRecoveries, totalCritical, infectHome, deathHome, recoverHome, criticalHome, labelCountry, labelTotal, labelToday;

    ArrayList<cd> retrievedCountryData = new ArrayList<>();

    EditText searchCountryInput;

    View rootView;

    String currLang;
    Boolean currMode;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String LANGUAGE = "LANGUAGE";
    public static final String DARKMODE = "DARKMODE";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final String[] sortByOptions_en = {"Total Cases", "Total Deaths", "New Cases", "New Deaths"};
        final String[] sortByOptions_tr = {"Toplam Hasta", "Toplam Ölüm", "Yeni Hasta", "Yeni Ölüm"};

        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity().getApplicationContext());
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        currLang = sharedPreferences.getString(LANGUAGE, (String) Locale.getDefault().getLanguage());
        currMode = sharedPreferences.getBoolean(DARKMODE, false);

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home, container, false);
            setupUI(rootView.findViewById(R.id.homeOuterLayout));

            totalInfections = rootView.findViewById(R.id.totalInfections);
            totalDeaths = rootView.findViewById(R.id.totalDeaths);
            totalRecoveries = rootView.findViewById(R.id.totalRecoveries);
            totalCritical = rootView.findViewById(R.id.totalCritical);

            infectHome = rootView.findViewById(R.id.infectHome);
            deathHome = rootView.findViewById(R.id.deathHome);
            recoverHome = rootView.findViewById(R.id.recoverHome);
            criticalHome = rootView.findViewById(R.id.criticalHome);

            labelCountry = rootView.findViewById(R.id.labelCountryHome);
            labelTotal = rootView.findViewById(R.id.labelTotalHome);
            labelToday = rootView.findViewById(R.id.labelTodayHome);

            searchCountryInput = rootView.findViewById(R.id.searchCountryInput);

            if (currLang.equals("tr")) {
                infectHome.setText(R.string.infection_text_tr);
                deathHome.setText(R.string.death_text_tr);
                recoverHome.setText(R.string.recovered_text_tr);
                criticalHome.setText(R.string.critical_text_tr);
                labelCountry.setText(R.string.label_country_tr);
                labelTotal.setText(R.string.label_total_tr);
                labelToday.setText(R.string.label_today_tr);
                searchCountryInput.setHint(R.string.country_search_bar_hint_tr);
            } else {
                infectHome.setText(R.string.infection_text);
                deathHome.setText(R.string.death_text);
                recoverHome.setText(R.string.recovered_text);
                criticalHome.setText(R.string.critical_text);
                labelCountry.setText(R.string.label_country);
                labelTotal.setText(R.string.label_total);
                labelToday.setText(R.string.label_today);
                searchCountryInput.setHint(R.string.country_search_bar_hint);
            }


            recyclerView = rootView.findViewById(R.id.countryRecyclerView);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);

            RecyclerViewClickListener listener = new RecyclerViewClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Log.i("Pressed to position", Integer.toString(position));
                    CountryData temp = countryDataArrayList.get(position);

                    Log.i("pressed country name", temp.getName());
                }
            };

            countryAdapter = new CountryAdapter(getActivity(), countryDataArrayList, listener);
            recyclerView.setAdapter(countryAdapter);

            countryDataArrayList.clear();
            countryAdapter.notifyDataSetChanged();


            final EditText searchCountryInput = rootView.findViewById(R.id.searchCountryInput);
            searchCountryInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            searchCountryInput.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(searchCountryInput.getWindowToken(), 0);
                        return true;
                    }
                    return false;
                }
            });




            Spinner sortBySpinner = rootView.findViewById(R.id.sortBySpinner);

            ArrayAdapter<String> sortByAdapter;

            if (currLang.equals("tr")) {
                sortByAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.my_spinner, sortByOptions_tr);
            } else {
                sortByAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.my_spinner, sortByOptions_en);
            }

            sortByAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sortBySpinner.setAdapter(sortByAdapter);
            sortBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            Log.i("spinner", "tc selected.");
                            sortBy(2);
                            break;
                        case 1:
                            Log.i("spinner", "td selected.");
                            sortBy(3);
                            break;
                        case 2:
                            Log.i("spinner", "nc selected.");
                            sortBy(0);
                            break;
                        case 3:
                            Log.i("spinner", "nd selected.");
                            sortBy(1);
                            break;
                    }
                    String queryString = searchCountryInput.getText().toString();
                    searchCountryInput.setText(queryString);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    Log.i("spinner", "nothing selected.");
                }
            });

            updateCountryList();

            searchCountryInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) { }

                @Override
                public void afterTextChanged(Editable s) {
                    countryDataArrayList.clear();
                    String search = s.toString();
                    for (cd curr : retrievedCountryData) {
                        if (curr.getName().toLowerCase().contains(search.toLowerCase())) {
                            addToCDAL(countryDataArrayList, countryAdapter, curr, currLang);
                        }
                    }
                }
            });
        } else {
            // language stuff
            // TODO: Populate here, when switched home from another fragment this routine should update views and texts.
            infectHome = rootView.findViewById(R.id.infectHome);
            deathHome = rootView.findViewById(R.id.deathHome);
            recoverHome = rootView.findViewById(R.id.recoverHome);
            criticalHome = rootView.findViewById(R.id.criticalHome);

            labelCountry = rootView.findViewById(R.id.labelCountryHome);
            labelTotal = rootView.findViewById(R.id.labelTotalHome);
            labelToday = rootView.findViewById(R.id.labelTodayHome);

            searchCountryInput = rootView.findViewById(R.id.searchCountryInput);

            if (currLang.equals("tr")) {
                infectHome.setText(R.string.infection_text_tr);
                deathHome.setText(R.string.death_text_tr);
                recoverHome.setText(R.string.recovered_text_tr);
                criticalHome.setText(R.string.critical_text_tr);
                labelCountry.setText(R.string.label_country_tr);
                labelTotal.setText(R.string.label_total_tr);
                labelToday.setText(R.string.label_today_tr);
                searchCountryInput.setHint(R.string.country_search_bar_hint_tr);
            } else {
                infectHome.setText(R.string.infection_text);
                deathHome.setText(R.string.death_text);
                recoverHome.setText(R.string.recovered_text);
                criticalHome.setText(R.string.critical_text);
                labelCountry.setText(R.string.label_country);
                labelTotal.setText(R.string.label_total);
                labelToday.setText(R.string.label_today);
                searchCountryInput.setHint(R.string.country_search_bar_hint);
            }
            Spinner sortBySpinner = rootView.findViewById(R.id.sortBySpinner);

            ArrayAdapter<String> sortByAdapter;

            if (currLang.equals("tr")) {
                sortByAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.my_spinner, sortByOptions_tr);
            } else {
                sortByAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.my_spinner, sortByOptions_en);
            }

            sortByAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sortBySpinner.setAdapter(sortByAdapter);
            sortBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            Log.i("spinner", "tc selected.");
                            sortBy(2);
                            break;
                        case 1:
                            Log.i("spinner", "td selected.");
                            sortBy(3);
                            break;
                        case 2:
                            Log.i("spinner", "nc selected.");
                            sortBy(0);
                            break;
                        case 3:
                            Log.i("spinner", "nd selected.");
                            sortBy(1);
                            break;
                    }
                    String queryString = searchCountryInput.getText().toString();
                    searchCountryInput.setText(queryString);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    Log.i("spinner", "nothing selected.");
                }
            });

        }

        return rootView;
    }


    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        if(activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void setupUI(View view) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(getActivity());
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    public void updateCountryList() {
        countryDataArrayList.clear();
        DatabaseReference cRef = database.getReference("countryData");
        cRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int counter = 0;
                String name, tc, nc, td, nd, tr, ac, sc, ratio;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String keyName = ds.getKey();
                    cd contents = ds.getValue(cd.class);

                    contents.setKeyName(keyName);
                    name = contents.getName();
                    tc = contents.getTc();
                    nc = contents.getNc();
                    td = contents.getTd();
                    tr = contents.getTr();
                    nd = contents.getNd();
                    ac = contents.getAc();
                    sc = contents.getSc();
                    ratio = contents.getRatio();

                    /*
                    Log.i("keyName", keyName);
                    Log.i("name", name);
                    Log.i("tc", tc);
                    Log.i("nc", nc);
                    Log.i("td", td);
                    Log.i("nd", nd);
                    Log.i("tr", tr);
                    Log.i("ac", ac);
                    Log.i("sc", sc);
                    Log.i("rat", ratio);
                    */


                    if (keyName.equals("Total")) {
                        totalInfections.setText(tc);
                        totalDeaths.setText(td);
                        totalRecoveries.setText(tr);
                        totalCritical.setText(sc);
                    } else {
                        contents.setKeyName(keyName);
                        retrievedCountryData.add(contents);
                        addToCDAL(countryDataArrayList, countryAdapter, contents, currLang);
                    }
                }
                sortBy(2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("error", "couldn't retrieve country data from fbase");
            }
        });
    }

    public void sortBy(int c) {
        switch (c) {
            case 0:
                Collections.sort(retrievedCountryData, new Comparator<cd>() {
                    @Override
                    public int compare(cd o1, cd o2) {
                        String sval1 = o1.getNc();
                        String sval2 = o2.getNc();
                        int val1, val2;
                        if (sval1.isEmpty()) { val1 = 0; }
                        else { val1 = Integer.parseInt(sval1.replace(",", "").replace("+", "")); }
                        if (sval2.isEmpty()) { val2 = 0; }
                        else { val2 = Integer.parseInt(sval2.replace(",", "").replace("+", "")); }
                        return val2 - val1;
                    }
                });
                break;
            case 1:
                Collections.sort(retrievedCountryData, new Comparator<cd>() {
                    @Override
                    public int compare(cd o1, cd o2) {
                        String sval1 = o1.getNd();
                        String sval2 = o2.getNd();
                        int val1, val2;
                        if (sval1.isEmpty()) { val1 = 0; }
                        else { val1 = Integer.parseInt(sval1.replace(",", "").replace("+", "")); }
                        if (sval2.isEmpty()) { val2 = 0; }
                        else { val2 = Integer.parseInt(sval2.replace(",", "").replace("+", "")); }
                        return val2 - val1;
                    }
                });
                break;
            case 2:
                Collections.sort(retrievedCountryData, new Comparator<cd>() {
                    @Override
                    public int compare(cd o1, cd o2) {
                        String sval1 = o1.getTc();
                        String sval2 = o2.getTc();
                        int val1, val2;
                        if (sval1.isEmpty()) { val1 = 0; }
                        else { val1 = Integer.parseInt(sval1.replace(",", "").replace("+", "")); }
                        if (sval2.isEmpty()) { val2 = 0; }
                        else { val2 = Integer.parseInt(sval2.replace(",", "").replace("+", "")); }
                        return val2 - val1;
                    }
                });
                break;
            case 3:
                Collections.sort(retrievedCountryData, new Comparator<cd>() {
                    @Override
                    public int compare(cd o1, cd o2) {
                        String sval1 = o1.getTd();
                        String sval2 = o2.getTd();
                        int val1, val2;
                        if (sval1.isEmpty()) { val1 = 0; }
                        else { val1 = Integer.parseInt(sval1.replace(",", "").replace("+", "")); }
                        if (sval2.isEmpty()) { val2 = 0; }
                        else { val2 = Integer.parseInt(sval2.replace(",", "").replace("+", "")); }
                        return val2 - val1;
                    }
                });
                break;
        }
        countryDataArrayList.clear();

        //TODO: Move cases and deaths string concatenations here. They currently are in the adapter.
        for (cd curr : retrievedCountryData) {
            addToCDAL(countryDataArrayList, countryAdapter, curr, currLang);
        }
    }

    public void addToCDAL(ArrayList<CountryData> cdal, CountryAdapter ca, cd curr, String cl) {
        CountryData newCd;

        String tc, nc, td,nd, name;
        Integer fid;

        String caseTr = " Hasta";
        String deathTr = " Ölüm";
        String caseEn = " Cases";
        String deathEn = " Deaths";

        String caseAdd;
        String deathAdd;

        if (cl.equals("tr")) {
            caseAdd = caseTr;
            deathAdd = deathTr;
            if (trNames.containsKey(curr.getKeyName())) {
                name = trNames.get(curr.getKeyName());
            } else {
                name = curr.getName();
            }
        } else {
            caseAdd = caseEn;
            deathAdd = deathEn;
            name = curr.getName();
        }

        if (curr.getTc().equals(""))
            tc = "0" + caseAdd;
        else
            tc = String.format("%s%s", curr.getTc(), caseAdd);

        if (curr.getNc().equals(""))
            nc = "0" + caseAdd;
        else
            nc = String.format("%s%s", curr.getNc(), caseAdd);

        if (curr.getTd().equals(""))
            td = "0" + deathAdd;
        else
            td = String.format("%s%s", curr.getTd(), deathAdd);

        if (curr.getNd().equals(""))
            nd = "0" + deathAdd;
        else
            nd = String.format("%s%s", curr.getNd(), deathAdd);

        if (nameToFlag.containsKey(curr.getKeyName())) {
            fid = nameToFlag.get(curr.getKeyName());
        } else {
            //TODO: this should be default pic not app logo
            fid = R.drawable.default_flag;
        }

        newCd = new CountryData(name, tc, td, nc, nd, fid);

        cdal.add(newCd);
        ca.notifyDataSetChanged();
    }

    public void dummyCountryList() {
        countryDataArrayList.clear();

        String cd1Name = "Spain";
        String cd2Name = "Turkey";
        String cd3Name = "Italy";
        String cd4Name = "Andorra";
        String cd5Name = "France";
        String cd6Name = "Germany";

        CountryData cd1 = new CountryData(cd1Name, "250", "15","20", "10", nameToFlag.get(cd1Name));
        CountryData cd2 = new CountryData(cd2Name, "20", "1","19", "11", nameToFlag.get(cd2Name));
        CountryData cd3 = new CountryData(cd3Name, "50", "2","23", "12", nameToFlag.get(cd3Name));
        CountryData cd4 = new CountryData(cd4Name, "25", "3","26", "13", nameToFlag.get(cd4Name));
        CountryData cd5 = new CountryData(cd5Name, "10", "5","28", "14", nameToFlag.get(cd5Name));
        CountryData cd6 = new CountryData(cd6Name, "109", "35","258", "114", nameToFlag.get(cd6Name));

        countryDataArrayList.add(cd1);
        countryDataArrayList.add(cd2);
        countryDataArrayList.add(cd3);
        countryDataArrayList.add(cd4);
        countryDataArrayList.add(cd5);
        countryDataArrayList.add(cd6);

        countryAdapter.notifyDataSetChanged();
    }
}
