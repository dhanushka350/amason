package com.akvasoft.amason.common;

public class CountryFilter {

    static String[] calcioList = {
            "argentina",
            "japan",
            "austria",
            "belgio",
            "brasile",
            "cina",
            "china",
            "croazia",
            "danimarca",
            "europa",
            "finlandia",
            "francia",
            "germania",
            "grecia",
            "inghilterra",
            "italia",
            "mondo",
            "norvegia",
            "olanda",
            "portogallo",
            "repubblica ceca",
            "romania",
            "russia",
            "scozia",
            "spagna",
            "sudamerica",
            "svezia",
            "svizzera",
            "turchia",
            "ucraina",
            "usa",
            "champion",
            "europa",
            "internazionali"
    };

    static String[] basketList = {
            "argentina",
            "japan",
            "austria",
            "belgio",
            "brasile",
            "cina",
            "china",
            "croazia",
            "danimarca",
            "europa",
            "finlandia",
            "francia",
            "germania",
            "grecia",
            "inghilterra",
            "italia",
            "mondo",
            "norvegia",
            "olanda",
            "portogallo",
            "repubblica ceca",
            "romania",
            "russia",
            "scozia",
            "spagna",
            "sudamerica",
            "svezia",
            "svizzera",
            "turchia",
            "ucraina",
            "usa",
            "internazionali"
    };

    static String[] tennisList = {
            "argentina",
            "japan",
            "austria",
            "belgio",
            "brasile",
            "cina",
            "china",
            "croazia",
            "danimarca",
            "europa",
            "finlandia",
            "francia",
            "germania",
            "grecia",
            "inghilterra",
            "italia",
            "mondo",
            "norvegia",
            "olanda",
            "portogallo",
            "repubblica ceca",
            "romania",
            "russia",
            "scozia",
            "spagna",
            "sudamerica",
            "svezia",
            "svizzera",
            "turchia",
            "ucraina",
            "usa",
            "internazionali",
            "atp",
            "wta",
            "coppa davis",
            "challenge",
            "itf maschile",
            "itf Woman"
    };

    static String[] vollyList = {
            "argentina",
            "japan",
            "austria",
            "belgio",
            "brasile",
            "cina",
            "china",
            "croazia",
            "danimarca",
            "europa",
            "finlandia",
            "francia",
            "germania",
            "grecia",
            "inghilterra",
            "italia",
            "mondo",
            "norvegia",
            "olanda",
            "portogallo",
            "repubblica ceca",
            "romania",
            "russia",
            "scozia",
            "spagna",
            "sudamerica",
            "svezia",
            "svizzera",
            "turchia",
            "ucraina",
            "usa",
            "brazil"
    };

    public static boolean countryInList(String country, int sport) {
        String[] list = null;

        if (sport == 1) {
            list = calcioList;
        } else if (sport == 2) {
            list = basketList;
        } else if (sport == 3) {
            list = tennisList;
        } else if (sport == 4) {
            list = vollyList;
        }
        country = country.toLowerCase();
        for (String req : list) {
            if (country.contains(req.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
