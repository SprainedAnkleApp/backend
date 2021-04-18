package pl.edu.agh.ki.io.db;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.models.AuthProvider;
import pl.edu.agh.ki.io.models.Gender;
import pl.edu.agh.ki.io.models.Peak;
import pl.edu.agh.ki.io.models.User;
import pl.edu.agh.ki.io.models.wallElements.Photo;
import pl.edu.agh.ki.io.models.wallElements.Post;

import java.sql.Date;


@Service
public class DbInit implements CommandLineRunner {
    private UserRepository userRepository;
    private GenderRepository genderRepository;
    private PasswordEncoder passwordEncoder;
    private PeakRepository peakRepository;
    private WallItemRepository wallItemRepository;

    public DbInit(UserRepository userRepository, GenderRepository genderRepository, PasswordEncoder passwordEncoder, PeakRepository peakRepository, WallItemRepository wallItemRepository) {
        this.userRepository = userRepository;
        this.genderRepository = genderRepository;
        this.passwordEncoder = passwordEncoder;
        this.peakRepository = peakRepository;
        this.wallItemRepository = wallItemRepository;
    }

    @Override
    public void run(String... args) {
        this.wallItemRepository.deleteAll();
        this.userRepository.deleteAll();
        this.genderRepository.deleteAll();

        Gender male = new Gender("Male");
        this.genderRepository.save(male);

        Date birthday = Date.valueOf("2000-12-1");
        User testUser = new User("admin", passwordEncoder.encode("admin"), AuthProvider.local, "Test",
                "Testowski", "test1@mail.com", male, "https://i.imgur.com/VNNp6zWb.jpg", birthday, "+48880053535");

        userRepository.save(testUser);

        addPeaks();

        Photo photo = new Photo(testUser, "content", "photopath");
        wallItemRepository.save(photo);

        Post post = new Post(testUser, "content");
        wallItemRepository.save(post);

    }

    private void addPeaks(){
        this.peakRepository.deleteAll();

        peakRepository.save(
                new Peak(
                        "Rysy",
                        2499,
                        "małopolskie",
                        "Góra położona na granicy polsko-słowackiej, w Tatrach Wysokich (jednej z części Tatr). Ma trzy wierzchołki, z których najwyższy jest środkowy (2501 metrów nad poziomem morza), znajdujący się w całości na terytorium Słowacji. Wierzchołek północno-zachodni, przez który biegnie granica, stanowi najwyżej położony punkt Polski i należy do Korony Europy.",
                        "Tatry",
                        "https://upload.wikimedia.org/wikipedia/commons/8/87/Rysy%2C_szczyt.jpg"
                )
        );

        peakRepository.save(
                new Peak(
                        "Babia Góra",
                        1725,
                        "małopolskie",
                        "Masyw górski w Paśmie Babiogórskim należącym do Beskidu Żywieckiego w Beskidach Zachodnich. Jest najwyższym szczytem Beskidów Zachodnich i poza Tatrami najwyższym szczytem w Polsce, drugim co do wybitności (po Śnieżce).",
                        "Beskid Żywiecki",
                        "https://upload.wikimedia.org/wikipedia/commons/b/bc/Babia_Gora_from_Rycerzowa.jpg"
                )
        );

        peakRepository.save(
                new Peak(
                        "Śnieżka",
                        1603,
                        "dolnośląskie",
                        "Najwyższy szczyt Karkonoszy oraz Sudetów, jak również Czech, województwa dolnośląskiego, a także całego Śląska. Najwybitniejszy szczyt Polski i Czech.",
                        "Karkonosze",
                        "https://www.karpacz.pl/img/attractions/Sniezka/rys_24.jpg"
                )
        );

        peakRepository.save(
                new Peak(
                        "Śnieżnik",
                        1423,
                        "dolnośląskie",
                        "Jest to najwyższy, graniczny szczyt po polskiej stronie w Sudetach Wschodnich i w Masywie Śnieżnika, zwany też Śnieżnikiem Kłodzkim. Jest on jedyną górą w masywie Śnieżnika, która wystaje ponad górną granicę lasu.",
                        "Masyw Śnieżnika",
                        "https://zieloniwpodrozy.pl/wp-content/uploads/2017/11/%C5%9Anie%C5%BCnik.jpg"
                )
        );

        peakRepository.save(
                new Peak(
                        "Tarnica",
                        1346,
                        "podkarpackie",
                        "Najwyższy szczyt polskich Bieszczadów i województwa podkarpackiego, wznoszący się na krańcu pasma połonin, w grupie tzw. gniazda Tarnicy i Halicza.",
                        "Bieszczady Zachodnie",
                        "https://upload.wikimedia.org/wikipedia/commons/2/27/Tarnica_%28HB1%29.jpg"
                )
        );

        peakRepository.save(
                new Peak(
                        "Turbacz",
                        1310,
                        "małopolskie",
                        "Najwyższy szczyt Gorców, znajdujący się w centralnym punkcie pasma i tworzący potężny rozróg. Zbudowany jest z fliszu karpackiego. Z Turbacza odbiega siedem górskich grzbietów.",
                        "Gorce",
                        "https://upload.wikimedia.org/wikipedia/commons/3/38/Turbacz_%285%29.jpg"
                )
        );

        peakRepository.save(
                new Peak(
                        "Radziejowa",
                        1266,
                        "małopolskie",
                        "Nazwa szczytu pochodzi od osoby o nazwisku lub przydomku Radziej. Znajduje się w głównym grzbiecie Pasma Radziejowej, pomiędzy Wielkim Rogaczem, od którego oddziela ją przełęcz Żłobki, a masywem Złomistego Wierchu, położonego za Przełęczą Długą. W 2006 r. na Radziejowej oddana została do użytku drewniana wieża widokowa o wysokości około 20 m.",
                        "Beskid Sądecki",
                        "https://u.profitroom.pl/2020-hotelczarnypotok-pl/thumb/0x1000/uploads/IMG_6124.JPG"
                )
        );

        peakRepository.save(
                new Peak(
                        "Skrzyczne",
                        1257,
                        "śląskie",
                        "Wznosi się w północno-wschodniej części Beskidu Śląskiego, w bocznym ramieniu pasma Baraniej Góry, odgałęziającym się od głównego pnia pasma w Malinowskiej Skale. Ze względu na charakterystyczną sylwetkę góry, stromo opadającej ku wschodowi i północy oraz na położony na szczycie maszt nadajnika RTV jest ona łatwo rozpoznawalna z wielu miejsc.",
                        "Beskid Śląski",
                        "https://beskidzkieapartamenty.pl/wp-content/uploads/2018/01/beskidzkie-5.jpg"
                )
        );

        peakRepository.save(
                new Peak(
                        "Mogielica",
                        1171,
                        "małopolskie",
                        "Masyw Mogielicy wznosi się na obszarze trzech gmin: Dobra, Słopnice i Kamienica. Na szczycie oraz na północnych stokach utworzono rezerwat przyrody Mogielica o powierzchni 50,44 ha. Głównym celem rezerwatu jest ochrona głuszca i jego biotopu oraz innych rzadkich gatunków ptaków, ich siedlisk przyrodniczych, a także form skalnych.",
                        "Beskid Wyspowy",
                        "http://www.planetagor.pl/img/uploads/images/p3052321_5cc7466a3255b.jpg"
                )
        );

        peakRepository.save(
                new Peak(
                        "Wysoka Kopa",
                        1126,
                        "dolnośląskie",
                        "Łagodna kopuła zbudowana z granitów, gnejsów i łupków, należących do bloku karkonosko-izerskiego, a ściślej do jego północno-zachodniej części – metamorfiku izerskiego. Jest bezleśna (w wyniku klęski ekologicznej lat 80. XX w.). Partie wierzchołka porośnięte są łąkami subalpejskimi z interesującymi gatunkami roślin.",
                        "Góry Izerskie",
                        "https://hasajacezajace.com/wp-content/uploads/2019/10/DSC_1834.jpg"
                )
        );

        peakRepository.save(
                new Peak(
                        "Rudawiec (wierzchołek graniczny)",
                        1106,
                        "podkarpackie",
                        "Masyw Rudawca wydzielają: dolina potoku Bielawka, dolina Czarnego Potoku i głęboka dolina Kunčického potoka, położona po czeskiej stronie Gór Bialskich. Zbudowany jest w całości z łupków metamorficznych i gnejsów gierałtowskich i porośnięty w większości przez lasy regla dolnego, a w partiach szczytowych rzadkim lasem regla górnego",
                        "Góry Bialskie",
                        "https://upload.wikimedia.org/wikipedia/commons/0/0a/Gory_Bialskie.jpg"
                )
        );

        peakRepository.save(
                new Peak(
                        "Orlica (wierzchołek graniczny)",
                        1084,
                        "dolnośląskie",
                        "Leży na europejskim dziale wodnym pomiędzy zlewiskami Morza Bałtyckiego i Morza Północnego. Dawniej nazywana była Międzywierchem. Orlica zbudowana jest z łupków łyszczykowych z wkładkami wapieni krystalicznych należących do metamorfiku bystrzycko-orlickiego.",
                        "Góry Orlickie",
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fd/Orlica.JPG/1200px-Orlica.JPG"
                )
        );

        // to check

        peakRepository.save(
                new Peak(
                        "Wysoka (Wysokie Skałki) (wierzchołek graniczny)",
                        1050,
                        "małopolskie",
                        "Szczyt w Małych Pieninach, położony na granicy polsko-słowackiej i będący najwyższym szczytem całych Pienin i Pienińskiego Pasa Skałkowego. Zbudowany jest z czerwonych wapieni krynoidowych. Cała góra porośnięta jest lasem, jedynie sam wierzchołek ma charakter kopuły skalnej wystającej ponad linię świerkowego lasu. ",
                        "Pieniny",
                        "https://mynaszlaku.pl/wp-content/gallery/wysoka-16-09-2019/Wysoka-male-pieniny0023.jpg"
                )
        );

        peakRepository.save(
                new Peak(
                        "Wielka Sowa",
                        1015,
                        "dolnośląskie",
                        "Najwyższy szczyt Gór Sowich w Sudetach Środkowych, z najbardziej zniszczonym ekologicznie drzewostanem w tych górach. Góra leży w dorzeczu Odry, a dokładniej na granicy między dorzeczami jej dopływów – Bystrzycy i Nysy Kłodzkiej. Zbocza Wielkiej Sowy są znakomitymi terenami narciarskimi.",
                        "Góry Sowie",
                        "https://agropajda.pl/wp-content/uploads/2020/06/Wielka_Sowa_1015_m_n_p_m_435013.jpg"
                )
        );

        peakRepository.save(
                new Peak(
                        "Lackowa (wierzchołek graniczny)",
                        997,
                        "małopolskie",
                        "Najwyższy szczyt po polskiej stronie Beskidu Niskiego, położony między Krynicą-Zdrój a Wysową, na granicy ze Słowacją. Zachodni stok góry, którym biegnie czerwony szlak turystyczny, jest najbardziej stromym w Beskidzie Niskim i jednym z najbardziej stromych w polskich górach (poza Tatrami) odcinkiem znakowanego szlaku.",
                        "Beskid Niski",
                        "https://gdziebytudalej.pl/wp-content/uploads/2018/06/01.-W-drodze-do-Izb.-Wszystkie-drogi-prowadz%C4%85-na-Lackow%C4%85-e1528315954381.jpg"
                )
        );

        peakRepository.save(
                new Peak(
                        "Kowadło (wierzchołek graniczny)",
                        989,
                        "dolnośląskie",
                        "Ze szczytu można podziwiać widoki na Góry Złote i północną część Wysokiego Jesionika oraz pogórze sudeckie aż po Nysę a nawet Opole. Kopulasty wierzchołek porastają młode świerki, gdzieniegdzie występują gnejsowe skałki.",
                        "Góry Złote",
                        "https://mynaszlaku.pl/wp-content/gallery/kowadlo-19-08-2019/Kowadlo-gory-zlote0007.jpg"
                )
        );

        peakRepository.save(
                new Peak(
                        "Jagodna",
                        977,
                        "dolnośląskie",
                        "Wzniesienie położone jest w środkowo-wschodniej części Gór Bystrzyckich około 5 km na zachód od miejscowości Długopole-Zdrój. Przez Jagodną w XVI wieku przebiegała granica pomiędzy Czechami a hrabstwem kłodzkim, którą później przesunięto do Doliny Dzikiej Orlicy.",
                        "Góry Bystrzyckie",
                        "https://hasajacezajace.com/wp-content/uploads/2020/10/DSC05389.jpg"
                )
        );

        peakRepository.save(
                new Peak(
                        "Skalnik",
                        945,
                        "",
                        "Szczyt w południowo-zachodniej Polsce, w Sudetach Zachodnich, w Rudawach Janowickich. Jest to wzniesienie o kopulastym kształcie, na którym znajdują się ruiny dawnej wieży widokowej (cztery betonowe bloki na których wieża była zakotwiczona), oraz na drzewie drewniana tabliczka z napisem Skalnik 945 m n.p.m.",
                        "Rudawy Janowickie",
                        "https://media.villagreta.pl/m/2018/08/skalnik-rudawy-janowickie-1.jpg"
                )
        );

        peakRepository.save(
                new Peak(
                        "Waligóra",
                        936,
                        "dolnośląskie",
                        "Najwyższy szczyt Gór Suchych i jednocześnie całych Gór Kamiennych. Mimo obecnego braku punktu widokowego Waligóra pozostaje popularnym celem wycieczek pieszych, głównie dzięki bezpośredniej bliskości Przełęczy Trzech Dolin, na której znajduje się duży węzeł szlaków, schronisko Andrzejówka i tereny do narciarstwa biegowego i zjazdowego.",
                        "Góry Kamienne",
                        "https://www.czar-gor.pl/galeria/DSC_5650-1170x780.jpg"
                )
        );

        peakRepository.save(
                new Peak(
                        "Czupel",
                        933,
                        "śląskie",
                        "Na grzbiecie Czupla wzdłuż szlaku turystycznego znajdują się długie wały kamieni zbieranych z dawnych hal. Miejscowa ludność nazywa je kródami. Istnieją też resztki okopów z okresu II wojny światowej.",
                        "Beskid Mały",
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a3/POLAND_BeskidMaly_Czupel_933m.jpg/1200px-POLAND_BeskidMaly_Czupel_933m.jpg"
                )
        );

        peakRepository.save(
                new Peak(
                        "Szczeliniec Wielki",
                        919,
                        "dolnośląskie",
                        "Jeden z największych atrakcji turystycznych Sudetów, z rezerwatem krajobrazowym i tarasami widokowymi z panoramą Sudetów. Najwyższym punktem jest Fotel Pradziada. Pomimo niedużej wysokości bezwzględnej szczyt ten jest widoczny już z daleka jako trapezoidalny blok skalny porośnięty lasem iglastym. ",
                        "Góry Stołowe",
                        "https://atrakcje-ziemiaklodzka.pl/wp-content/uploads/2018/11/szczeliniec-wielki.jpg"
                )
        );

        peakRepository.save(
                new Peak(
                        "Lubomir",
                        904,
                        "",
                        "Szczyt w Paśmie Lubomira i Łysiny. Nazwa szczytu pochodzi od nazwiska księcia Kazimierza Lubomirskiego. Nadano ją w 1932 r. w uznaniu jego zasług. Wierzchołek Lubomira jest całkowicie zalesiony, więc pozbawiony widoków.",
                        "Beskid Makowski",
                        "https://mynaszlaku.pl/wp-content/gallery/lubomir-27-08-2019/lubomir-korona-gor-polski0009.jpg"
                )
        );

        peakRepository.save(
                new Peak(
                        "Biskupia Kopa (wierzchołek graniczny)",
                        889,
                        "opolskie",
                        "Przyjmuje się, że Biskupia Kopa jest najwyższym szczytem w polskiej części Gór Opawskich oraz najwyższym wzniesieniem województwa opolskiego, chociaż najwyższy jej punkt, wraz z zabytkową wieżą widokową, znajduje się po czeskiej stronie. Pod koniec XIX wieku wyznaczono pierwsze szlaki prowadzące na szczyt oraz wybudowano czynną do dziś wieżę widokową.",
                        "Góry Opawskie",
                        "https://marcinkarpinski.pl/image/korona-gor-polski/biskupiakopa/biskupiakopa.jpg"
                )
        );

        peakRepository.save(
                new Peak(
                        "Chełmiec",
                        851,
                        "dolnośląskie",
                        "Najwyższy punkt w granicach administracyjnych Szczawna-Zdroju i drugi co do wysokości szczyt Gór Wałbrzyskich. Stanowi kulminację, wyraźnie dominującą w wałbrzyskim krajobrazie. Wynika to z jego ukształtowania w formie kopuły, przez co jest łatwo rozpoznawalny, nawet z odległego o 70 km Wrocławia.",
                        "Góry Wałbrzyskie",
                        "https://hasajacezajace.com/wp-content/uploads/2020/11/IMG_20200815_180214.jpg"
                )
        );

        peakRepository.save(
                new Peak(
                        "Kłodzka Góra",
                        765,
                        "dolnośląskie",
                        "Należy do głównego grzbietu Gór Bardzkich, stanowiąc jego zwornik (rozróg) z odchodzącymi w czterech kierunkach grzbietami. Porośnięta w całości lasem świerkowym, w partii szczytowej przerzedzony. Na szczyt prowadzi żółty szlak pieszy wychodzący z Kłodzka.",
                        "Góry Bardzkie",
                        "https://hasajacezajace.com/wp-content/uploads/2020/10/DSC05413.jpg"
                )
        );

        peakRepository.save(
                new Peak(
                        "Skopiec",
                        724,
                        "dolnośląskie",
                        "Trzeci co do wysokości szczyt Gór Kaczawskich. Wyrasta w środkowej części Grzbietu Południowego, w kształcie słabo zaznaczonej kopuły, z niewyraźnie podkreśloną częścią szczytową i średnio stromymi zboczami. Powierzchnia wierzchowiny jest tak wyrównana, że wierzchołek jest trudno rozpoznawalny w terenie.",
                        "Góry Kaczawskie",
                        "https://upload.wikimedia.org/wikipedia/commons/9/9a/Skopiec_mit2.JPG"
                )
        );

        peakRepository.save(
                new Peak(
                        "Ślęża",
                        718,
                        "dolnośląskie",
                        "Jest objęta ochroną jako Rezerwat krajobrazowo-geologiczny i historyczny. Ze względu na bliskość Wrocławia, Świdnicy i Dzierżoniowa odbywa się tam sezonowa turystyka. Masyw i jego okolice pokryte są dość gęstą siecią szlaków turystycznych. Istnieją dwie ścieżki archeologiczne oznaczone symbolem ślężańskiego niedźwiedzia i szlaki rowerowe.",
                        "Masyw Ślęży",
                        "https://outdoorzy.pl/blog/wp-content/uploads/2018/08/IMG_20180715_111834.jpg"
                )
        );

        peakRepository.save(
                new Peak(
                        "Łysica",
                        614,
                        "świętokrzyskie",
                        "Ma dwa wierzchołki. Wschodni wierzchołek, nazywany Skałą Agaty lub Zamczyskiem oraz zachodni, na którym znajduje się replika pamiątkowego krzyża z 1930 r. oraz nieliczne pozostałości wieży triangulacyjnej. Podania mówią, że u podnóża Łysicy istniała kiedyś słowiańska świątynia, na miejscu której obecnie znajduje się klasztor sióstr bernardynek.",
                        "Góry Świętokrzyskie",
                        "https://wmeritum.pl/wp-content/uploads/2019/11/%C5%81ysica.jpg"
                )
        );
    }
}
