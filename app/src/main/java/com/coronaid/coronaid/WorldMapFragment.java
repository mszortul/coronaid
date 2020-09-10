package com.coronaid.coronaid;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import static com.coronaid.coronaid.HomeFragment.SHARED_PREFS;
import static com.coronaid.coronaid.LanguageActivity.DARKMODE;
import static com.coronaid.coronaid.LanguageActivity.LANGUAGE;

public class WorldMapFragment extends Fragment {

    public WorldMapFragment() {}

    public class llPair {
        double lat;
        double lng;
        public llPair(double lat, double lng) {
            this.lat = lat;
            this.lng = lng;
        }
    }

    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    public final HashMap<String, llPair> nameToLatLng = new HashMap<String, llPair>() {{
        put("Aruba", new llPair(12.52088038, -69.98267711));
        put("Afghanistan", new llPair(33.83523073, 66.00473366));
        put("Angola", new llPair(-12.29336054, 17.53736768));
        put("Anguilla", new llPair(18.2239595, -63.06498927));
        put("Albania", new llPair(41.14244989, 20.04983396));
        put("Aland", new llPair(60.21488688, 19.95328768));
        put("Andorra", new llPair(42.54229102, 1.56054378));
        put("UAE", new llPair(23.90528188, 54.3001671));
        put("Argentina", new llPair(-35.3813488, -65.17980692));
        put("Armenia", new llPair(40.28952569, 44.92993276));
        put("AmericanSamoa", new llPair(-14.30445997, -170.7180258));
        put("Antarctica", new llPair(-80.50857913, 19.92108951));
        put("AshmoreandCartierIslands", new llPair(-12.42993164, 123.5838379));
        put("FrSAntarcticLands", new llPair(-49.24895485, 69.22666758));
        put("AntiguaandBarbuda", new llPair(17.2774996, -61.79469343));
        put("Australia", new llPair(-25.73288704, 134.4910001));
        put("Austria", new llPair(47.58549439, 14.1264761));
        put("Azerbaijan", new llPair(40.28827235, 47.54599879));
        put("Burundi", new llPair(-3.35939666, 29.87512156));
        put("Belgium", new llPair(50.63981576, 4.64065114));
        put("Benin", new llPair(9.6417597, 2.32785254));
        put("BurkinaFaso", new llPair(12.26953846, -1.75456601));
        put("Bangladesh", new llPair(23.86731158, 90.23812743));
        put("Bulgaria", new llPair(42.76890318, 25.21552909));
        put("Bahrain", new llPair(26.04205135, 50.54196932));
        put("Canada", new llPair(61.36206324, -98.30777028));
        put("Bahamas", new llPair(24.29036702, -76.62843038));
        put("BosniaandHerzegovina", new llPair(44.17450125, 17.76876733));
        put("StBarth", new llPair(17.89880451, -62.84067779));
        put("Belarus", new llPair(53.53131377, 28.03209307));
        put("Belize", new llPair(17.20027509, -88.71010486));
        put("Bermuda", new llPair(32.31367802, -64.7545589));
        put("Bolivia", new llPair(-16.70814787, -64.68538645));
        put("Brazil", new llPair(-10.78777702, -53.09783113));
        put("Barbados", new llPair(13.18145428, -59.559797));
        put("Brunei", new llPair(4.51968958, 114.7220304));
        put("Bhutan", new llPair(27.41106589, 90.40188155));
        put("Botswana", new llPair(-22.18403213, 23.79853368));
        put("CAR", new llPair(6.56823297, 20.46826831));
        put("Switzerland", new llPair(46.79785878, 8.20867471));
        put("Chile", new llPair(-37.73070989, -71.38256213));
        put("China", new llPair(36.56176546, 103.8190735));
        put("CtedIvoire", new llPair(7.6284262, -5.5692157));
        put("Cameroon", new llPair(5.69109849, 12.73964156));
        put("DRC", new llPair(-2.87746289, 23.64396107));
        put("Congo", new llPair(-0.83787463, 15.21965762));
        put("CookIslands", new llPair(-21.21927288, -159.7872422));
        put("Colombia", new llPair(3.91383431, -73.08114582));
        put("Comoros", new llPair(-11.87783444, 43.68253968));
        put("CapeVerde", new llPair(15.95523324, -23.9598882));
        put("CostaRica", new llPair(9.97634464, -84.19208768));
        put("Cuba", new llPair(21.62289528, -79.01605384));
        put("Curaao", new llPair(12.19551675, -68.97119369));
        put("CaymanIslands", new llPair(19.42896497, -80.91213321));
        put("NCyprus", new llPair(35.26277486, 33.5684813));
        put("Cyprus", new llPair(34.91667211, 33.0060022));
        put("Czechia", new llPair(49.73341233, 15.31240163));
        put("Germany", new llPair(51.10698181, 10.38578051));
        put("Djibouti", new llPair(11.74871806, 42.5606754));
        put("Dominica", new llPair(15.4394702, -61.357726));
        put("Denmark", new llPair(55.98125296, 10.02800992));
        put("DominicanRepublic", new llPair(18.89433082, -70.50568896));
        put("Algeria", new llPair(28.15893849, 2.61732301));
        put("Ecuador", new llPair(-1.42381612, -78.75201922));
        put("Egypt", new llPair(26.49593311, 29.86190099));
        put("Eritrea", new llPair(15.36186618, 38.84617011));
        put("Spain", new llPair(40.24448698, -3.64755047));
        put("Estonia", new llPair(58.67192972, 25.54248537));
        put("Ethiopia", new llPair(8.62278679, 39.60080098));
        put("Finland", new llPair(64.49884603, 26.2746656));
        put("Fiji", new llPair(-18.0943914, 177.6901797));
        put("FalklandIslands", new llPair(-51.74483954, -59.35238956));
        put("France", new llPair(46.4459255, 1.9892692));
        put("FaeroeIslands", new llPair(62.05385403, -6.88095423));
        put("Micronesia", new llPair(7.45246814, 153.2394379));
        put("Gabon", new llPair(-0.58660025, 11.7886287));
        put("UK", new llPair(54.12387156, -2.86563164));
        put("Georgia", new llPair(42.16855755, 43.50780252));
        put("Guernsey", new llPair(49.46809761, -2.57239064));
        put("Ghana", new llPair(7.95345644, -1.21676566));
        put("Guinea", new llPair(10.43621593, -10.94066612));
        put("Gambia", new llPair(13.44965244, -15.39601295));
        put("GuineaBissau", new llPair(12.04744948, -14.94972445));
        put("EquatorialGuinea", new llPair(1.70555135, 10.34137924));
        put("Greece", new llPair(39.07469623, 22.95555794));
        put("Grenada", new llPair(12.11725044, -61.68220189));
        put("Greenland", new llPair(74.71051289, -41.34191127));
        put("Guatemala", new llPair(15.69403664, -90.36482009));
        put("Guam", new llPair(13.44165626, 144.7679102));
        put("Guyana", new llPair(4.79378034, -58.98202459));
        put("HongKong", new llPair(22.39827737, 114.1138045));
        put("HeardIandMcDonaldIslands", new llPair(-53.08724656, 73.5205171));
        put("Honduras", new llPair(14.82688165, -86.6151661));
        put("Croatia", new llPair(45.08047631, 16.40412899));
        put("Haiti", new llPair(18.93502563, -72.68527509));
        put("Hungary", new llPair(47.16277506, 19.39559116));
        put("Indonesia", new llPair(-2.21505456, 117.2401137));
        put("IsleofMan", new llPair(54.22418911, -4.53873952));
        put("India", new llPair(22.88578212, 79.6119761));
        put("IndianOceanTer", new llPair(-10.6478515, 104.851898));
        put("BrIndianOceanTer", new llPair(-7.33059751, 72.44541229));
        put("Ireland", new llPair(53.1754487, -8.13793569));
        put("Iran", new llPair(32.57503292, 54.27407004));
        put("Iraq", new llPair(33.03970582, 43.74353149));
        put("Iceland", new llPair(64.99575386, -18.57396167));
        put("Israel", new llPair(31.46110101, 35.00444693));
        put("Italy", new llPair(42.79662641, 12.07001339));
        put("Jamaica", new llPair(18.15694878, -77.31482593));
        put("Jersey", new llPair(49.21837377, -2.12689938));
        put("Jordan", new llPair(31.24579091, 36.77136104));
        put("Japan", new llPair(37.59230135, 138.0308956));
        put("SiachenGlacier", new llPair(35.39236325, 77.18011865));
        put("Kazakhstan", new llPair(48.15688067, 67.29149357));
        put("Kenya", new llPair(0.59988022, 37.79593973));
        put("Kyrgyzstan", new llPair(41.46221943, 74.54165513));
        put("Cambodia", new llPair(12.72004786, 104.9069433));
        put("Kiribati", new llPair(0.86001503, -45.61110513));
        put("StKittsandNevis", new llPair(17.2645995, -62.68755265));
        put("SKorea", new llPair(35.8026653, 125.6299263));
        put("Kosovo", new llPair(42.57078707, 20.87249811));
        put("Kuwait", new llPair(29.33431262, 47.58700459));
        put("Lao PDR", new llPair(18.50217433, 103.7377241));
        put("Lebanon", new llPair(33.92306631, 35.88016072));
        put("Liberia", new llPair(6.45278492, -9.32207573));
        put("Libya", new llPair(27.03094495, 18.00866169));
        put("SaintLucia", new llPair(13.89479481, -60.96969923));
        put("Liechtenstein", new llPair(47.13665835, 9.53574312));
        put("SriLanka", new llPair(7.61266509, 80.70108238));
        put("Lesotho", new llPair(-29.58003188, 28.22723131));
        put("Lithuania", new llPair(55.32610984, 23.88719355));
        put("Luxembourg", new llPair(49.76725361, 6.07182201));
        put("Latvia", new llPair(56.85085163, 24.91235983));
        put("Macao", new llPair(22.22311688, 113.5093212));
        put("SaintMartin", new llPair(18.08888611, -63.05972851));
        put("Morocco", new llPair(29.83762955, -8.45615795));
        put("Monaco", new llPair(43.75274627, 7.40627677));
        put("Moldova", new llPair(47.19498804, 28.45673372));
        put("Madagascar", new llPair(-19.37189587, 46.70473674));
        put("Maldives", new llPair(3.7287092, 73.45713004));
        put("Mexico", new llPair(23.94753724, -102.5234517));
        put("MarshallIslands", new llPair(7.00376358, 170.3397612));
        put("NorthMacedonia", new llPair(41.59530893, 21.68211346));
        put("Mali", new llPair(17.34581581, -3.54269065));
        put("Malta", new llPair(35.92149632, 14.40523316));
        put("Myanmar", new llPair(21.18566599, 96.48843321));
        put("Montenegro", new llPair(42.78890259, 19.23883939));
        put("Mongolia", new llPair(46.82681544, 103.0529977));
        put("NMarianaIslands", new llPair(15.82927563, 145.6196965));
        put("Mozambique", new llPair(-17.27381643, 35.53367543));
        put("Mauritania", new llPair(20.25736706, -10.34779815));
        put("Montserrat", new llPair(16.73941406, -62.18518546));
        put("Mauritius", new llPair(-20.27768704, 57.57120551));
        put("Malawi", new llPair(-13.21808088, 34.28935599));
        put("Malaysia", new llPair(3.78986846, 109.6976228));
        put("Namibia", new llPair(-22.13032568, 17.20963567));
        put("NewCaledonia", new llPair(-21.29991806, 165.6849237));
        put("Niger", new llPair(17.41912493, 9.38545882));
        put("NorfolkIsland", new llPair(-29.0514609, 167.9492168));
        put("Nigeria", new llPair(9.59411452, 8.08943895));
        put("Nicaragua", new llPair(12.84709429, -85.0305297));
        put("Niue", new llPair(-19.04945708, -169.8699468));
        put("Netherlands", new llPair(52.1007899, 5.28144793));
        put("Norway", new llPair(68.75015572, 15.34834656));
        put("Nepal", new llPair(28.24891365, 83.9158264));
        put("Nauru", new llPair(-0.51912639, 166.9325682));
        put("NewZealand", new llPair(-41.81113557, 171.4849235));
        put("Oman", new llPair(20.60515333, 56.09166155));
        put("Pakistan", new llPair(29.9497515, 69.33957937));
        put("Panama", new llPair(8.51750797, -80.11915156));
        put("PitcairnIslands", new llPair(-24.36500535, -128.317042));
        put("Peru", new llPair(-9.15280381, -74.38242685));
        put("Philippines", new llPair(11.77536778, 122.8839325));
        put("Palau", new llPair(7.28742784, 134.4080797));
        put("PapuaNewGuinea", new llPair(-6.46416646, 145.2074475));
        put("Poland", new llPair(52.12759564, 19.39012835));
        put("PuertoRico", new llPair(18.22813055, -66.47307604));
        put("NKorea", new llPair(40.2829253, 125.1809544));
        put("Portugal", new llPair(39.59550671, -8.50104361));
        put("Paraguay", new llPair(-23.22823913, -58.40013703));
        put("Palestine", new llPair(31.91613893, 35.19628705));
        put("FrenchPolynesia", new llPair(-14.72227409, -144.9049439));
        put("Qatar", new llPair(25.30601188, 51.18479632));
        put("Romania", new llPair(45.85243127, 24.97293039));
        put("Russia", new llPair(61.98052209, 96.68656112));
        put("Rwanda", new llPair(-1.99033832, 29.91988515));
        put("WSahara", new llPair(24.22956739, -12.21982755));
        put("SaudiArabia", new llPair(24.12245841, 44.53686271));
        put("Sudan", new llPair(15.99035669, 29.94046812));
        put("SSudan", new llPair(7.30877945, 30.24790002));
        put("Senegal", new llPair(14.36624173, -14.4734924));
        put("Singapore", new llPair(1.35876087, 103.8172559));
        put("SGeoandSSandwIslands", new llPair(-54.46488248, -36.43318388));
        put("Saint Helena", new llPair(-12.40355951, -9.54779416));
        put("SolomonIslands", new llPair(-8.92178022, 159.6328767));
        put("SierraLeone", new llPair(8.56329593, -11.79271247));
        put("El Salvador", new llPair(13.73943744, -88.87164469));
        put("SanMarino", new llPair(43.94186747, 12.45922334));
        put("Somaliland", new llPair(9.73345496, 46.25198395));
        put("Somalia", new llPair(4.75062876, 45.70714487));
        put("StPierreandMiquelon", new llPair(46.91918789, -56.30319779));
        put("Serbia", new llPair(44.2215032, 20.78958334));
        put("SoTomandPrincipe", new llPair(0.44391445, 6.72429658));
        put("Suriname", new llPair(4.13055413, -55.9123457));
        put("Slovakia", new llPair(48.70547528, 19.47905218));
        put("Slovenia", new llPair(46.11554772, 14.80444238));
        put("Sweden", new llPair(62.77966519, 16.74558049));
        put("Swaziland", new llPair(-26.55843045, 31.4819369));
        put("SintMaarten", new llPair(18.05081728, -63.05713363));
        put("Seychelles", new llPair(-4.66099094, 55.47603279));
        put("Syria", new llPair(35.02547389, 38.50788204));
        put("TurksandCaicosIslands", new llPair(21.83047572, -71.97387881));
        put("Chad", new llPair(15.33333758, 18.64492513));
        put("Togo", new llPair(8.52531356, 0.96232845));
        put("Thailand", new llPair(15.11815794, 101.0028813));
        put("Tajikistan", new llPair(38.5304539, 71.01362631));
        put("Turkmenistan", new llPair(39.11554137, 59.37100021));
        put("TimorLeste", new llPair(-8.82889162, 125.8443898));
        put("Tonga", new llPair(-20.42843174, -174.8098734));
        put("TrinidadandTobago", new llPair(10.45733408, -61.26567923));
        put("Tunisia", new llPair(34.11956246, 9.55288359));
        put("Turkey", new llPair(39.0616029, 35.16895346));
        put("Taiwan", new llPair(23.7539928, 120.9542728));
        put("Tanzania", new llPair(-6.27565408, 34.81309981));
        put("Uganda", new llPair(1.27469299, 32.36907971));
        put("Ukraine", new llPair(48.99656673, 31.38326469));
        put("Uruguay", new llPair(-32.79951534, -56.01807053));
        put("USA", new llPair(38.0928861, -99.646048));
        put("Uzbekistan", new llPair(41.75554225, 63.14001528));
        put("VaticanCity", new llPair(41.90174985, 12.43387177));
        put("StVincentGrenadines", new llPair(13.22472269, -61.20129695));
        put("Venezuela", new llPair(7.12422421, -66.18184123));
        put("UKVirginIslands", new llPair(18.52585755, -64.47146992));
        put("USVirginIslands", new llPair(17.95500624, -64.80301538));
        put("Vietnam", new llPair(16.6460167, 106.299147));
        put("Vanuatu", new llPair(-16.22640909, 167.6864464));
        put("WallisandFutunaIs", new llPair(-13.88737039, -177.3483483));
        put("Samoa", new llPair(-13.75324346, -172.1648506));
        put("Yemen", new llPair(15.90928005, 47.58676189));
        put("SouthAfrica", new llPair(-29.00034095, 25.08390093));
        put("Zambia", new llPair(-13.45824152, 27.77475946));
        put("Zimbabwe", new llPair(-19.00420419, 29.8514412));
        put("ChannelIslands", new llPair(49.4587851, -2.6626619));
        put("Eswatini", new llPair(-26.5153368, 30.9023317));
        put("FrenchGuiana", new llPair(4.0340152, -54.2067539));
        put("Gibraltar", new llPair(36.1295426, -5.3708096));
        put("Guadeloupe", new llPair(16.1501063, -61.6772411));
        put("IvoryCoast", new llPair(7.4662975, -7.7918396));
        put("Martinique", new llPair(14.6343799, -61.1538604));
        put("Mayotte", new llPair(-12.806178, 45.0156975));
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


    MapView mapView;
    private GoogleMap googleMap;

    SharedPreferences sharedPreferences;
    String currLang;
    Boolean currMode;

    Context context;

    ArrayList<Marker> infectMarkers = new ArrayList<>();
    ArrayList<Marker> deathMarkers = new ArrayList<>();
    ArrayList<Marker> recoverMarkers = new ArrayList<>();
    ArrayList<Marker> criticalMarkers = new ArrayList<>();

    LinearLayout infectLayout, deathLayout, recoverLayout, criticalLayout;
    TextView infectTextView, deathTextView, recoverTextView, criticalTextView;

    int currMarkerGroup;

    double base_dp;

    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (rootView == null) {
            context = getActivity().getApplicationContext();
        }

        rootView = inflater.inflate(R.layout.fragment_map, container, false);

        base_dp = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                4,
                context.getResources().getDisplayMetrics()
        );

        sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        currLang = sharedPreferences.getString(LANGUAGE, (String) Locale.getDefault().getLanguage());
        currMode = sharedPreferences.getBoolean(DARKMODE, false);

        infectLayout = rootView.findViewById(R.id.mapInfect);
        deathLayout = rootView.findViewById(R.id.mapDeath);
        recoverLayout = rootView.findViewById(R.id.mapRecover);
        criticalLayout = rootView.findViewById(R.id.mapCritical);

        infectTextView = rootView.findViewById(R.id.mapInfectText);
        deathTextView = rootView.findViewById(R.id.mapDeathText);
        recoverTextView = rootView.findViewById(R.id.mapRecoverText);
        criticalTextView = rootView.findViewById(R.id.mapCriticalText);


        currMarkerGroup = 0;

        infectLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currMarkerGroup != 0) {
                    changeSelection(0);
                    changeCircles(0);
                    currMarkerGroup = 0;
                }
            }
        });

        deathLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currMarkerGroup != 1) {
                    changeSelection(1);
                    changeCircles(1);
                    currMarkerGroup = 1;
                }
            }
        });

        recoverLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currMarkerGroup != 2) {
                    changeSelection(2);
                    changeCircles(2);
                    currMarkerGroup = 2;
                }
            }
        });

        criticalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currMarkerGroup != 3) {
                    changeSelection(3);
                    changeCircles(3);
                    currMarkerGroup = 3;
                }
            }
        });

        mapView = (MapView) rootView.findViewById(R.id.worldMap);
        mapView.onCreate(savedInstanceState);

        mapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final double latitudeTR = 39.0099899;
        final double longitudeTR = 30.6864146;

        final String infectText, deathText, recoverText, criticalText;

        if (currLang.equals("tr")) {
            infectTextView.setText(R.string.map_infect_tr);
            deathTextView.setText(R.string.map_death_tr);
            recoverTextView.setText(R.string.map_recover_tr);
            criticalTextView.setText(R.string.map_critical_tr);
            infectText = " Hasta";
            deathText = " Ölüm";
            recoverText = " İyileşme";
            criticalText = " Kritik durumda";
        } else {
            infectTextView.setText(R.string.map_infect);
            deathTextView.setText(R.string.map_death);
            recoverTextView.setText(R.string.map_recover);
            criticalTextView.setText(R.string.map_critical);
            infectText = " Infections";
            deathText = " Deaths";
            recoverText = " Recoveries";
            criticalText = " Critical condition";
        }

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap gMap) {
                googleMap = gMap;

                googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity().getApplicationContext(), R.raw.map_style));

                googleMap.setMaxZoomPreference(6.0f);

                DatabaseReference cRef = database.getReference("countryData");
                cRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String keyName;
                        double currLat, currLng;
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            keyName = ds.getKey();
                            cd contents = ds.getValue(cd.class);

                            Integer tc, td, tr, sc;
                            tc = null;
                            td = null;
                            tr = null;
                            sc = null;


                            if (!contents.getTc().isEmpty()) {
                                tc = Integer.parseInt((contents.getTc()).replace(",", ""));
                            }

                            if (!contents.getTd().isEmpty()) {
                                td = Integer.parseInt((contents.getTd()).replace(",", ""));
                            }

                            if (!contents.getTr().isEmpty()){
                                tr = Integer.parseInt((contents.getTr()).replace(",", ""));
                            }

                            if (!contents.getSc().isEmpty()) {
                                sc = Integer.parseInt((contents.getSc()).replace(",", ""));
                            }

                            if (nameToLatLng.containsKey(keyName)) {
                                llPair llp = nameToLatLng.get(keyName);
                                currLat = llp.lat;
                                currLng = llp.lng;
                                String cname;

                                if (currLang.equals("tr")) {
                                    if (trNames.containsKey(keyName)) {
                                        cname = trNames.get(keyName);
                                    } else {
                                        cname = contents.getName();
                                    }
                                } else {
                                    cname = contents.getName();
                                }

                                int pxInfect, pxDeath, pxRecover, pxCritical;

                                if (tc != null) {
                                    pxInfect = countToRadius2(tc);
                                    Bitmap circleMarkerInfect = Bitmap.createBitmap(pxInfect, pxInfect, Bitmap.Config.ARGB_8888);
                                    Canvas canvasInfect = new Canvas(circleMarkerInfect);
                                    Drawable shapeInfect = context.getResources().getDrawable(R.drawable.custom_marker_infect);
                                    shapeInfect.setBounds(0, 0, circleMarkerInfect.getWidth(), circleMarkerInfect.getHeight());
                                    shapeInfect.draw(canvasInfect);


                                    Marker markerInfect = googleMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(currLat, currLng))
                                            .anchor(0.5f, 0.5f)
                                            .icon(BitmapDescriptorFactory.fromBitmap(circleMarkerInfect))
                                            .title(cname)
                                            .snippet(contents.getTc() + infectText));

                                    infectMarkers.add(markerInfect);
                                }


                                if (td != null) {
                                    pxDeath = countToRadius2(td);
                                    Bitmap circleMarkerDeath = Bitmap.createBitmap(pxDeath, pxDeath, Bitmap.Config.ARGB_8888);
                                    Canvas canvasDeath = new Canvas(circleMarkerDeath);
                                    Drawable shapeDeath = context.getResources().getDrawable(R.drawable.custom_marker_death);
                                    shapeDeath.setBounds(0, 0, circleMarkerDeath.getWidth(), circleMarkerDeath.getHeight());
                                    shapeDeath.draw(canvasDeath);

                                    Marker markerDeath = googleMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(currLat, currLng))
                                            .anchor(0.5f, 0.5f)
                                            .icon(BitmapDescriptorFactory.fromBitmap(circleMarkerDeath))
                                            .title(cname)
                                            .snippet(contents.getTd() + deathText)
                                            .visible(false));

                                    deathMarkers.add(markerDeath);
                                }

                                if (tr != null) {
                                    pxRecover = countToRadius2(tr);
                                    Bitmap circleMarkerRecover = Bitmap.createBitmap(pxRecover, pxRecover, Bitmap.Config.ARGB_8888);
                                    Canvas canvasRecover = new Canvas(circleMarkerRecover);
                                    Drawable shapeRecover = context.getResources().getDrawable(R.drawable.custom_marker_recover);
                                    shapeRecover.setBounds(0, 0, circleMarkerRecover.getWidth(), circleMarkerRecover.getHeight());
                                    shapeRecover.draw(canvasRecover);

                                    Marker markerRecover = googleMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(currLat, currLng))
                                            .anchor(0.5f, 0.5f)
                                            .icon(BitmapDescriptorFactory.fromBitmap(circleMarkerRecover))
                                            .title(cname)
                                            .snippet(contents.getTr() + recoverText)
                                            .visible(false));

                                    recoverMarkers.add(markerRecover);
                                }


                                if (sc != null) {
                                    pxCritical = countToRadius2(sc);
                                    Bitmap circleMarkerCritical = Bitmap.createBitmap(pxCritical, pxCritical, Bitmap.Config.ARGB_8888);
                                    Canvas canvasCritical = new Canvas(circleMarkerCritical);
                                    Drawable shapeCritical = context.getResources().getDrawable(R.drawable.custom_marker_critical);
                                    shapeCritical.setBounds(0, 0, circleMarkerCritical.getWidth(), circleMarkerCritical.getHeight());
                                    shapeCritical.draw(canvasCritical);

                                    Marker markerCritical = googleMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(currLat, currLng))
                                            .anchor(0.5f, 0.5f)
                                            .icon(BitmapDescriptorFactory.fromBitmap(circleMarkerCritical))
                                            .title(cname)
                                            .snippet(contents.getSc() + criticalText)
                                            .visible(false));

                                    criticalMarkers.add(markerCritical);
                                }

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.i("Error", "couldn't retrieve db keys.");
                    }
                });

                //googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
                LatLng turkey = new LatLng(latitudeTR, longitudeTR);

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(turkey).zoom(0).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        return rootView;
    }



    public void setMarkersVisibility(ArrayList<Marker> arr, boolean val) {
        for (Marker marker : arr) {
            marker.setVisible(val);
        }
    }

    public void makeMarkersInvisible(int markerGroup) {
        switch (markerGroup) {
            case 0:
                setMarkersVisibility(infectMarkers, false);
                break;
            case 1:
                setMarkersVisibility(deathMarkers, false);
                break;
            case 2:
                setMarkersVisibility(recoverMarkers, false);
                break;
            case 3:
                setMarkersVisibility(criticalMarkers, false);
                break;
        }
    }

    public void changeCircles(int num) {
        makeMarkersInvisible(currMarkerGroup);
        switch (num) {
            case 0:
                setMarkersVisibility(infectMarkers, true);
                break;
            case 1:
                setMarkersVisibility(deathMarkers, true);
                break;
            case 2:
                setMarkersVisibility(recoverMarkers, true);
                break;
            case 3:
                setMarkersVisibility(criticalMarkers, true);
                break;
        }
    }

    public int countToRadius(int tc) {
        int px;

        if (tc <= 100) px = context.getResources().getDimensionPixelSize(R.dimen.marker_0_100);
        else if (tc <= 1000) px = context.getResources().getDimensionPixelSize(R.dimen.marker_100_1k);
        else if (tc <= 10000) px = context.getResources().getDimensionPixelSize(R.dimen.marker_1k_10k);
        else if (tc <= 50000) px = context.getResources().getDimensionPixelSize(R.dimen.marker_10k_50k);
        else if (tc <= 150000) px = context.getResources().getDimensionPixelSize(R.dimen.marker_50k_150k);
        else if (tc <= 500000) px = context.getResources().getDimensionPixelSize(R.dimen.marker_150k_500k);
        else if (tc <= 1000000) px = context.getResources().getDimensionPixelSize(R.dimen.marker_500k_1m);
        else if (tc <= 2000000) px = context.getResources().getDimensionPixelSize(R.dimen.marker_1m_2m);
        else if (tc <= 4000000) px = context.getResources().getDimensionPixelSize(R.dimen.marker_2m_4m);
        else if (tc <= 8000000) px = context.getResources().getDimensionPixelSize(R.dimen.marker_4m_8m);
        else if (tc <= 16000000) px = context.getResources().getDimensionPixelSize(R.dimen.marker_8m_16m);
        else px = context.getResources().getDimensionPixelSize(R.dimen.marker_16m_plus);

        return px;
    }

    public int countToRadius2(int tc) {
        int base_px = 8;
        double ftc = tc;

        double multi = Math.cbrt(ftc);

        int px = (int) Math.round(multi);

        double fpx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                px,
                context.getResources().getDisplayMetrics()
        );

        return (int) Math.max(fpx, base_dp);
    }

    public void changeSelection(int num) {
        switch (num) {
            case 0:
                infectLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.map_top_layout));
                deathLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.map_top_layout_inactive));
                recoverLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.map_top_layout_inactive));
                criticalLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.map_top_layout_inactive));
                infectTextView.setTextColor(context.getResources().getColor(R.color.light_black));
                deathTextView.setTextColor(context.getResources().getColor(R.color.inactive_legend));
                recoverTextView.setTextColor(context.getResources().getColor(R.color.inactive_legend));
                criticalTextView.setTextColor(context.getResources().getColor(R.color.inactive_legend));

                //infectTextView.setShadowLayer(5, 0, 0, Color.RED);
                break;
            case 1:
                infectLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.map_top_layout_inactive));
                deathLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.map_top_layout));
                recoverLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.map_top_layout_inactive));
                criticalLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.map_top_layout_inactive));
                infectTextView.setTextColor(context.getResources().getColor(R.color.inactive_legend));
                deathTextView.setTextColor(context.getResources().getColor(R.color.light_black));
                recoverTextView.setTextColor(context.getResources().getColor(R.color.inactive_legend));
                criticalTextView.setTextColor(context.getResources().getColor(R.color.inactive_legend));

                //deathTextView.setShadowLayer(5, 0, 0, Color.WHITE);
                break;
            case 2:
                infectLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.map_top_layout_inactive));
                deathLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.map_top_layout_inactive));
                recoverLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.map_top_layout));
                criticalLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.map_top_layout_inactive));
                infectTextView.setTextColor(context.getResources().getColor(R.color.inactive_legend));
                deathTextView.setTextColor(context.getResources().getColor(R.color.inactive_legend));
                recoverTextView.setTextColor(context.getResources().getColor(R.color.light_black));
                criticalTextView.setTextColor(context.getResources().getColor(R.color.inactive_legend));

                //recoverTextView.setShadowLayer(50, 0, 0, Color.RED);
                break;
            case 3:
                infectLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.map_top_layout_inactive));
                deathLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.map_top_layout_inactive));
                recoverLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.map_top_layout_inactive));
                criticalLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.map_top_layout));
                infectTextView.setTextColor(context.getResources().getColor(R.color.inactive_legend));
                deathTextView.setTextColor(context.getResources().getColor(R.color.inactive_legend));
                recoverTextView.setTextColor(context.getResources().getColor(R.color.inactive_legend));
                criticalTextView.setTextColor(context.getResources().getColor(R.color.light_black));

                //criticalTextView.setShadowLayer(50, 0, 0, Color.WHITE);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}


