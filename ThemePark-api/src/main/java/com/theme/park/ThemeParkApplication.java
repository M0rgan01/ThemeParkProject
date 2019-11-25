package com.theme.park;

import com.theme.park.doa.CountryRepository;
import com.theme.park.doa.ParkRepository;
import com.theme.park.doa.RoleRepository;
import com.theme.park.entities.Country;
import com.theme.park.entities.Park;
import com.theme.park.entities.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ThemeParkApplication implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ParkRepository parkRepository;
    @Autowired
    private CountryRepository countryRepository;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(ThemeParkApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

       /* List<Country> countryList = new ArrayList<>();

        Role role = new Role();
        role.setName("USER");
        Role role2 = new Role();
        role2.setName("ADMIN");
        roleRepository.save(role);
        roleRepository.save(role2);


        Country AD = new Country(null, "Andorra", "AD");
        countryList.add(AD);
        Country AE = new Country(null, "United Arab Emirates", "AE");
        countryList.add(AE);
        Country AF = new Country(null, "Afghanistan", "AF");
        countryList.add(AF);
        Country AG = new Country(null, "Antigua and Barbuda", "AG");
        countryList.add(AG);
        Country AI = new Country(null, "Anguilla", "AI");
        countryList.add(AI);
        Country AL = new Country(null, "Albania", "AL");
        countryList.add(AL);
        Country AM = new Country(null, "Armenia", "AM");
        countryList.add(AM);
        Country AN = new Country(null, "Netherlands Antilles", "AN");
        countryList.add(AN);
        Country AO = new Country(null, "Angola", "AO");
        countryList.add(AO);
        Country AQ = new Country(null, "Antarctica", "AQ");
        countryList.add(AQ);
        Country AR = new Country(null, "Argentina", "AR");
        countryList.add(AR);
        Country AS = new Country(null, "American Samoa", "AS");
        countryList.add(AS);
        Country AT = new Country(null, "Austria", "AT");
        countryList.add(AT);
        Country AU = new Country(null, "Australia", "AU");
        countryList.add(AU);
        Country AW = new Country(null, "Aruba", "AW");
        countryList.add(AW);
        Country AX = new Country(null, "Åland Islands", "AX");
        countryList.add(AX);
        Country AZ = new Country(null, "Azerbaijan", "AZ");
        countryList.add(AZ);
        Country BA = new Country(null, "Bosnia and Herzegovina", "BA");
        countryList.add(BA);
        Country BB = new Country(null, "Barbados", "BB");
        countryList.add(BB);
        Country BD = new Country(null, "Bangladesh", "BD");
        countryList.add(BD);
        Country BE = new Country(null, "Belgium", "BE");
        countryList.add(BE);
        Country BF = new Country(null, "Burkina Faso", "BF");
        countryList.add(BF);
        Country BG = new Country(null, "Bulgaria", "BG");
        countryList.add(BG);
        Country BH = new Country(null, "Bahrain", "BH");
        countryList.add(BH);
        Country BI = new Country(null, "Burundi", "BI");
        countryList.add(BI);
        Country BJ = new Country(null, "Benin", "BJ");
        countryList.add(BJ);
        Country BL = new Country(null, "Saint Barthélemy", "BL");
        countryList.add(BL);
        Country BM = new Country(null, "Bermuda", "BM");
        countryList.add(BM);
        Country BN = new Country(null, "Brunei Darussalam", "BN");
        countryList.add(BN);
        Country BO = new Country(null, "Bolivia", "BO");
        countryList.add(BO);
        Country BQ = new Country(null, "Bonaire, Sint Eustatius and Saba", "BQ");
        countryList.add(BQ);
        Country BR = new Country(null, "Brazil", "BR");
        countryList.add(BR);
        Country BS = new Country(null, "Bahamas", "BS");
        countryList.add(BS);
        Country BT = new Country(null, "Bhutan", "BT");
        countryList.add(BT);
        Country BV = new Country(null, "Bouvet Island", "BV");
        countryList.add(BV);
        Country BW = new Country(null, "Botswana", "BW");
        countryList.add(BW);
        Country BY = new Country(null, "Belarus", "BY");
        countryList.add(BY);
        Country BZ = new Country(null, "Belize", "BZ");
        countryList.add(BZ);
        Country CA = new Country(null, "Canada", "CA");
        countryList.add(CA);
        Country CC = new Country(null, "Cocos (Keeling) Islands", "CC");
        countryList.add(CC);
        Country CD = new Country(null, "Congo, The Democratic Republic Of The", "CD");
        countryList.add(CD);
        Country CF = new Country(null, "Central African Republic", "CF");
        countryList.add(CF);
        Country CG = new Country(null, "Congo", "CG");
        countryList.add(CG);
        Country CH = new Country(null, "Switzerland", "CH");
        countryList.add(CH);
        Country CI = new Country(null, "Côte D'Ivoire", "CI");
        countryList.add(CI);
        Country CK = new Country(null, "Cook Islands", "CK");
        countryList.add(CK);
        Country CL = new Country(null, "Chile", "CL");
        countryList.add(CL);
        Country CM = new Country(null, "Cameroon", "CM");
        countryList.add(CM);
        Country CN = new Country(null, "China", "CN");
        countryList.add(CN);
        Country CO = new Country(null, "Colombia", "CO");
        countryList.add(CO);
        Country CR = new Country(null, "Costa Rica", "CR");
        countryList.add(CR);
        Country CU = new Country(null, "Cuba", "CU");
        countryList.add(CU);
        Country CV = new Country(null, "Cape Verde", "CV");
        countryList.add(CV);
        Country CW = new Country(null, "Curaçao", "CW");
        countryList.add(CW);
        Country CX = new Country(null, "Christmas Island", "CX");
        countryList.add(CX);
        Country CY = new Country(null, "Cyprus", "CY");
        countryList.add(CY);
        Country CZ = new Country(null, "Czech Republic", "CZ");
        countryList.add(CZ);
        Country DE = new Country(null, "Germany", "DE");
        countryList.add(DE);
        Country DJ = new Country(null, "Djibouti", "DJ");
        countryList.add(DJ);
        Country DK = new Country(null, "Denmark", "DK");
        countryList.add(DK);
        Country DM = new Country(null, "Dominica", "DM");
        countryList.add(DM);
        Country DO = new Country(null, "Dominican Republic", "DO");
        countryList.add(DO);
        Country DZ = new Country(null, "Algeria", "DZ");
        countryList.add(DZ);
        Country EC = new Country(null, "Ecuador", "EC");
        countryList.add(EC);
        Country EE = new Country(null, "Estonia", "EE");
        countryList.add(EE);
        Country EG = new Country(null, "Egypt", "EG");
        countryList.add(EG);
        Country EH = new Country(null, "Western Sahara", "EH");
        countryList.add(EH);
        Country ER = new Country(null, "Eritrea", "ER");
        countryList.add(ER);
        Country ES = new Country(null, "Spain", "ES");
        countryList.add(ES);
        Country ET = new Country(null, "Ethiopia", "ET");
        countryList.add(ET);
        Country EU = new Country(null, "Europe", "EU");
        countryList.add(EU);
        Country FI = new Country(null, "Finland", "FI");
        countryList.add(FI);
        Country FJ = new Country(null, "Fiji", "FJ");
        countryList.add(FJ);
        Country FK = new Country(null, "Falkland Islands (Malvinas)", "FK");
        countryList.add(FK);
        Country FM = new Country(null, "Micronesia, Federated States Of", "FM");
        countryList.add(FM);
        Country FO = new Country(null, "Faroe Islands", "FO");
        countryList.add(FO);
        Country FR = new Country(null, "France", "FR");
        countryList.add(FR);
        Country GA = new Country(null, "Gabon", "GA");
        countryList.add(GA);
        Country GB = new Country(null, "United Kingdom", "GB");
        countryList.add(GB);
        Country GD = new Country(null, "Grenada", "GD");
        countryList.add(GD);
        Country GE = new Country(null, "Georgia", "GE");
        countryList.add(GE);
        Country GF = new Country(null, "French Guiana", "GF");
        countryList.add(GF);
        Country GG = new Country(null, "Guernsey", "GG");
        countryList.add(GG);
        Country GH = new Country(null, "Ghana", "GH");
        countryList.add(GH);
        Country GI = new Country(null, "Gibraltar", "GI");
        countryList.add(GI);
        Country GL = new Country(null, "Greenland", "GL");
        countryList.add(GL);
        Country GM = new Country(null, "Gambia", "GM");
        countryList.add(GM);
        Country GN = new Country(null, "Guinea", "GN");
        countryList.add(GN);
        Country GP = new Country(null, "Guadeloupe", "GP");
        countryList.add(GP);
        Country GQ = new Country(null, "Equatorial Guinea", "GQ");
        countryList.add(GQ);
        Country GR = new Country(null, "Greece", "GR");
        countryList.add(GR);
        Country GS = new Country(null, "South Georgia and the South Sandwich Islands", "GS");
        countryList.add(GS);
        Country GT = new Country(null, "Guatemala", "GT");
        countryList.add(GT);
        Country GU = new Country(null, "Guam", "GU");
        countryList.add(GU);
        Country GW = new Country(null, "Guinea-Bissau", "GW");
        countryList.add(GW);
        Country GY = new Country(null, "Guyana", "GY");
        countryList.add(GY);
        Country HK = new Country(null, "Hong Kong", "HK");
        countryList.add(HK);
        Country HM = new Country(null, "Heard and McDonald Islands", "HM");
        countryList.add(HM);
        Country HN = new Country(null, "Honduras", "HN");
        countryList.add(HN);
        Country HR = new Country(null, "Croatia", "HR");
        countryList.add(HR);
        Country HT = new Country(null, "Haiti", "HT");
        countryList.add(HT);
        Country HU = new Country(null, "Hungary", "HU");
        countryList.add(HU);
        Country ID = new Country(null, "Indonesia", "ID");
        countryList.add(ID);
        Country IE = new Country(null, "Ireland", "IE");
        countryList.add(IE);
        Country IL = new Country(null, "Israel", "IL");
        countryList.add(IL);
        Country IM = new Country(null, "Isle of Man", "IM");
        countryList.add(IM);
        Country IN = new Country(null, "India", "IN");
        countryList.add(IN);
        Country IO = new Country(null, "British Indian Ocean Territory", "IO");
        countryList.add(IO);
        Country IQ = new Country(null, "Iraq", "IQ");
        countryList.add(IQ);
        Country IR = new Country(null, "Iran, Islamic Republic Of", "IR");
        countryList.add(IR);
        Country IS = new Country(null, "Iceland", "IS");
        countryList.add(IS);
        Country IT = new Country(null, "Italy", "IT");
        countryList.add(IT);
        Country JE = new Country(null, "Jersey", "JE");
        countryList.add(JE);
        Country JM = new Country(null, "Jamaica", "JM");
        countryList.add(JM);
        Country JO = new Country(null, "Jordan", "JO");
        countryList.add(JO);
        Country JP = new Country(null, "Japan", "JP");
        countryList.add(JP);
        Country KE = new Country(null, "Kenya", "KE");
        countryList.add(KE);
        Country KG = new Country(null, "Kyrgyzstan", "KG");
        countryList.add(KG);
        Country KH = new Country(null, "Cambodia", "KH");
        countryList.add(KH);
        Country KI = new Country(null, "Kiribati", "KI");
        countryList.add(KI);
        Country KM = new Country(null, "Comoros", "KM");
        countryList.add(KM);
        Country KN = new Country(null, "Saint Kitts And Nevis", "KN");
        countryList.add(KN);
        Country KP = new Country(null, "Korea, Democratic People's Republic Of", "KP");
        countryList.add(KP);
        Country KR = new Country(null, "Korea, Republic of", "KR");
        countryList.add(KR);
        Country KW = new Country(null, "Kuwait", "KW");
        countryList.add(KW);
        Country KY = new Country(null, "Cayman Islands", "KY");
        countryList.add(KY);
        Country KZ = new Country(null, "Kazakhstan", "KZ");
        countryList.add(KZ);
        Country LA = new Country(null, "Lao People's Democratic Republic", "LA");
        countryList.add(LA);
        Country LB = new Country(null, "Lebanon", "LB");
        countryList.add(LB);
        Country LC = new Country(null, "Saint Lucia", "LC");
        countryList.add(LC);
        Country LI = new Country(null, "Liechtenstein", "LI");
        countryList.add(LI);
        Country LK = new Country(null, "Sri Lanka", "LK");
        countryList.add(LK);
        Country LR = new Country(null, "Liberia", "LR");
        countryList.add(LR);
        Country LS = new Country(null, "Lesotho", "LS");
        countryList.add(LS);
        Country LT = new Country(null, "Lithuania", "LT");
        countryList.add(LT);
        Country LU = new Country(null, "Luxembourg", "LU");
        countryList.add(LU);
        Country LV = new Country(null, "Latvia", "LV");
        countryList.add(LV);
        Country LY = new Country(null, "Libya", "LY");
        countryList.add(LY);
        Country MA = new Country(null, "Morocco", "MA");
        countryList.add(MA);
        Country MC = new Country(null, "Monaco", "MC");
        countryList.add(MC);
        Country MD = new Country(null, "Moldova, Republic of", "MD");
        countryList.add(MD);
        Country ME = new Country(null, "Montenegro", "ME");
        countryList.add(ME);
        Country MF = new Country(null, "Saint Martin", "MF");
        countryList.add(MF);
        Country MG = new Country(null, "Madagascar", "MG");
        countryList.add(MG);
        Country MH = new Country(null, "Marshall Islands", "MH");
        countryList.add(MH);
        Country MK = new Country(null, "Macedonia, the Former Yugoslav Republic Of", "MK");
        countryList.add(MK);
        Country ML = new Country(null, "Mali", "ML");
        countryList.add(ML);
        Country MM = new Country(null, "Myanmar", "MM");
        countryList.add(MM);
        Country MN = new Country(null, "Mongolia", "MN");
        countryList.add(MN);
        Country MO = new Country(null, "Macao", "MO");
        countryList.add(MO);
        Country MP = new Country(null, "Northern Mariana Islands", "MP");
        countryList.add(MP);
        Country MQ = new Country(null, "Martinique", "MQ");
        countryList.add(MQ);
        Country MR = new Country(null, "Mauritania", "MR");
        countryList.add(MR);
        Country MS = new Country(null, "Montserrat", "MS");
        countryList.add(MS);
        Country MT = new Country(null, "Malta", "MT");
        countryList.add(MT);
        Country MU = new Country(null, "Mauritius", "MU");
        countryList.add(MU);
        Country MV = new Country(null, "Maldives", "MV");
        countryList.add(MV);
        Country MW = new Country(null, "Malawi", "MW");
        countryList.add(MW);
        Country MX = new Country(null, "Mexico", "MX");
        countryList.add(MX);
        Country MY = new Country(null, "Malaysia", "MY");
        countryList.add(MY);
        Country MZ = new Country(null, "Mozambique", "MZ");
        countryList.add(MZ);
        Country NA = new Country(null, "Namibia", "NA");
        countryList.add(NA);
        Country NC = new Country(null, "New Caledonia", "NC");
        countryList.add(NC);
        Country NE = new Country(null, "Niger", "NE");
        countryList.add(NE);
        Country NF = new Country(null, "Norfolk Island", "NF");
        countryList.add(NF);
        Country NG = new Country(null, "Nigeria", "NG");
        countryList.add(NG);
        Country NI = new Country(null, "Nicaragua", "NI");
        countryList.add(NI);
        Country NL = new Country(null, "Netherlands", "NL");
        countryList.add(NL);
        Country NO = new Country(null, "Norway", "NO");
        countryList.add(NO);
        Country NP = new Country(null, "Nepal", "NP");
        countryList.add(NP);
        Country NR = new Country(null, "Nauru", "NR");
        countryList.add(NR);
        Country NU = new Country(null, "Niue", "NU");
        countryList.add(NU);
        Country NZ = new Country(null, "New Zealand", "NZ");
        countryList.add(NZ);
        Country OM = new Country(null, "Oman", "OM");
        countryList.add(OM);
        Country PA = new Country(null, "Panama", "PA");
        countryList.add(PA);
        Country PE = new Country(null, "Peru", "PE");
        countryList.add(PE);
        Country PF = new Country(null, "French Polynesia", "PF");
        countryList.add(PF);
        Country PG = new Country(null, "Papua New Guinea", "PG");
        countryList.add(PG);
        Country PH = new Country(null, "Philippines", "PH");
        countryList.add(PH);
        Country PK = new Country(null, "Pakistan", "PK");
        countryList.add(PK);
        Country PL = new Country(null, "Poland", "PL");
        countryList.add(PL);
        Country PM = new Country(null, "Saint Pierre And Miquelon", "PM");
        countryList.add(PM);
        Country PN = new Country(null, "Pitcairn", "PN");
        countryList.add(PN);
        Country PR = new Country(null, "Puerto Rico", "PR");
        countryList.add(PR);
        Country PS = new Country(null, "Palestine, State of", "PS");
        countryList.add(PS);
        Country PT = new Country(null, "Portugal", "PT");
        countryList.add(PT);
        Country PW = new Country(null, "Palau", "PW");
        countryList.add(PW);
        Country PY = new Country(null, "Paraguay", "PY");
        countryList.add(PY);
        Country QA = new Country(null, "Qatar", "QA");
        countryList.add(QA);
        Country RE = new Country(null, "Réunion", "RE");
        countryList.add(RE);
        Country RO = new Country(null, "Romania", "RO");
        countryList.add(RO);
        Country RS = new Country(null, "Serbia", "RS");
        countryList.add(RS);
        Country RU = new Country(null, "Russian Federation", "RU");
        countryList.add(RU);
        Country RW = new Country(null, "Rwanda", "RW");
        countryList.add(RW);
        Country SA = new Country(null, "Saudi Arabia", "SA");
        countryList.add(SA);
        Country SB = new Country(null, "Solomon Islands", "SB");
        countryList.add(SB);
        Country SC = new Country(null, "Seychelles", "SC");
        countryList.add(SC);
        Country SD = new Country(null, "Sudan", "SD");
        countryList.add(SD);
        Country SE = new Country(null, "Sweden", "SE");
        countryList.add(SE);
        Country SG = new Country(null, "Singapore", "SG");
        countryList.add(SG);
        Country SH = new Country(null, "Saint Helena", "SH");
        countryList.add(SH);
        Country SI = new Country(null, "Slovenia", "SI");
        countryList.add(SI);
        Country SJ = new Country(null, "Svalbard And Jan Mayen", "SJ");
        countryList.add(SJ);
        Country SK = new Country(null, "Slovakia", "SK");
        countryList.add(SK);
        Country SL = new Country(null, "Sierra Leone", "SL");
        countryList.add(SL);
        Country SM = new Country(null, "San Marino", "SM");
        countryList.add(SM);
        Country SN = new Country(null, "Senegal", "SN");
        countryList.add(SN);
        Country SO = new Country(null, "Somalia", "SO");
        countryList.add(SO);
        Country SR = new Country(null, "Suriname", "SR");
        countryList.add(SR);
        Country SS = new Country(null, "South Sudan", "SS");
        countryList.add(SS);
        Country ST = new Country(null, "Sao Tome and Principe", "ST");
        countryList.add(ST);
        Country SV = new Country(null, "El Salvador", "SV");
        countryList.add(SV);
        Country SX = new Country(null, "Sint Maarten", "SX");
        countryList.add(SX);
        Country SY = new Country(null, "Syrian Arab Republic", "SY");
        countryList.add(SY);
        Country SZ = new Country(null, "Swaziland", "SZ");
        countryList.add(SZ);
        Country TC = new Country(null, "Turks and Caicos Islands", "TC");
        countryList.add(TC);
        Country TD = new Country(null, "Chad", "TD");
        countryList.add(TD);
        Country TF = new Country(null, "French Southern Territories", "TF");
        countryList.add(TF);
        Country TG = new Country(null, "Togo", "TG");
        countryList.add(TG);
        Country TH = new Country(null, "Thailand", "TH");
        countryList.add(TH);
        Country TJ = new Country(null, "Tajikistan", "TJ");
        countryList.add(TJ);
        Country TK = new Country(null, "Tokelau", "TK");
        countryList.add(TK);
        Country TL = new Country(null, "Timor-Leste", "TL");
        countryList.add(TL);
        Country TM = new Country(null, "Turkmenistan", "TM");
        countryList.add(TM);
        Country TN = new Country(null, "Tunisia", "TN");
        countryList.add(TN);
        Country TO = new Country(null, "Tonga", "TO");
        countryList.add(TO);
        Country TR = new Country(null, "Turkey", "TR");
        countryList.add(TR);
        Country TT = new Country(null, "Trinidad and Tobago", "TT");
        countryList.add(TT);
        Country TV = new Country(null, "Tuvalu", "TV");
        countryList.add(TV);
        Country TW = new Country(null, "Taiwan, Republic Of China", "TW");
        countryList.add(TW);
        Country TZ = new Country(null, "Tanzania, United Republic of", "TZ");
        countryList.add(TZ);
        Country UA = new Country(null, "Ukraine", "UA");
        countryList.add(UA);
        Country UG = new Country(null, "Uganda", "UG");
        countryList.add(UG);
        Country UM = new Country(null, "United States Minor Outlying Islands", "UM");
        countryList.add(UM);
        Country US = new Country(null, "United States", "US");
        countryList.add(US);
        Country UY = new Country(null, "Uruguay", "UY");
        countryList.add(UY);
        Country UZ = new Country(null, "Uzbekistan", "UZ");
        countryList.add(UZ);
        Country VA = new Country(null, "Holy See (Vatican City State)", "VA");
        countryList.add(VA);
        Country VC = new Country(null, "Saint Vincent And The Grenadines", "VC");
        countryList.add(VC);
        Country VE = new Country(null, "Venezuela, Bolivarian Republic of", "VE");
        countryList.add(VE);
        Country VG = new Country(null, "Virgin Islands, British", "VG");
        countryList.add(VG);
        Country VI = new Country(null, "Virgin Islands, U.S.", "VI");
        countryList.add(VI);
        Country VN = new Country(null, "Vietnam", "VN");
        countryList.add(VN);
        Country VU = new Country(null, "Vanuatu", "VU");
        countryList.add(VU);
        Country WF = new Country(null, "Wallis and Futuna", "WF");
        countryList.add(WF);
        Country WS = new Country(null, "Samoa", "WS");
        countryList.add(WS);
        Country YE = new Country(null, "Yemen", "YE");
        countryList.add(YE);
        Country YT = new Country(null, "Mayotte", "YT");
        countryList.add(YT);
        Country ZA = new Country(null, "South Africa", "ZA");
        countryList.add(ZA);
        Country ZM = new Country(null, "Zambia", "ZM");
        countryList.add(ZM);
        Country ZW = new Country(null, "Zimbabwe", "ZW");
        countryList.add(ZW);

        countryRepository.saveAll(countryList);

        Park park = new Park();
        park.setName("Astérix");
        park.setUrlName("asterix");
        park.setCountry(FR);
        parkRepository.save(park);

        Park park2 = new Park();
        park2.setCountry(FR);
        park2.setName("Disney");
        park2.setUrlName("disney");
        parkRepository.save(park2);

        Park park3 = new Park();
        park3.setCountry(US);
        park3.setName("Europa Park");
        parkRepository.save(park3);

        Park park4 = new Park();
        park4.setCountry(US);
        park4.setName("Europa Park2");
        parkRepository.save(park4);
*/
    }
}
