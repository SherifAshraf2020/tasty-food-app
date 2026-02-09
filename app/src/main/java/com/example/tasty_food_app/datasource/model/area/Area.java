package com.example.tasty_food_app.datasource.model.area;

import com.google.gson.annotations.SerializedName;

public class Area {
    @SerializedName("strArea")
    private String strArea;

    public Area() {}

    public Area(String strArea) {
        this.strArea = strArea;
    }

    public String getStrArea() {
        return strArea;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }

    public String getFlagUrl() {
        if (strArea == null) return "";

        String countryCode;
        switch (strArea) {
            case "American": countryCode = "us"; break;
            case "British": countryCode = "gb"; break;
            case "Canadian": countryCode = "ca"; break;
            case "Chinese": countryCode = "cn"; break;
            case "Croatian": countryCode = "hr"; break;
            case "Dutch": countryCode = "nl"; break;
            case "Egyptian": countryCode = "eg"; break;
            case "French": countryCode = "fr"; break;
            case "Greek": countryCode = "gr"; break;
            case "Indian": countryCode = "in"; break;
            case "Irish": countryCode = "ie"; break;
            case "Italian": countryCode = "it"; break;
            case "Jamaican": countryCode = "jm"; break;
            case "Japanese": countryCode = "jp"; break;
            case "Kenyan": countryCode = "ke"; break;
            case "Malaysian": countryCode = "my"; break;
            case "Mexican": countryCode = "mx"; break;
            case "Moroccan": countryCode = "ma"; break;
            case "Polish": countryCode = "pl"; break;
            case "Portuguese": countryCode = "pt"; break;
            case "Russian": countryCode = "ru"; break;
            case "Spanish": countryCode = "es"; break;
            case "Thai": countryCode = "th"; break;
            case "Tunisian": countryCode = "tn"; break;
            case "Turkish": countryCode = "tr"; break;
            case "Vietnamese": countryCode = "vn"; break;

            case "Algerian": countryCode = "dz"; break;
            case "Argentinian": countryCode = "ar"; break;
            case "Australian": countryCode = "au"; break;
            case "Filipino": countryCode = "ph"; break;
            case "Norwegian": countryCode = "no"; break;
            case "Saudi Arabian": countryCode = "sa"; break;
            case "Syrian": countryCode = "sy"; break;
            case "Slovakian": countryCode = "sk"; break;
            case "Ukrainian": countryCode = "ua"; break;
            case "Uruguayan": countryCode = "uy"; break;
            case "Venezuelan": countryCode = "vz"; break;

            default: countryCode = "unknown"; break;
        }

        return "https://www.themealdb.com/images/icons/flags/big/64/" + countryCode + ".png";
    }
}
