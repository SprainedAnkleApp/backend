package pl.edu.agh.ki.io.db;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.models.*;
import pl.edu.agh.ki.io.models.wallElements.PeakPost;
import pl.edu.agh.ki.io.models.wallElements.Photo;
import pl.edu.agh.ki.io.models.wallElements.Post;
import pl.edu.agh.ki.io.models.wallElements.reactions.Reaction;
import pl.edu.agh.ki.io.models.wallElements.reactions.ReactionKey;
import pl.edu.agh.ki.io.models.wallElements.reactions.ReactionType;

import java.sql.Date;
import java.time.Duration;


@Service
@AllArgsConstructor
public class DbInit implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PeakRepository peakRepository;
    private final WallItemRepository wallItemRepository;
    private final PeakCompletionsRepository peakCompletionsRepository;
    private final PeakPostsRepository peakPostsRepository;
    private final ReactionsRepository reactionsRepository;
    private final FriendshipRepository friendshipRepository;
    private final CommentRepository commentRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Override
    public void run(String... args) {
        clearRepositories();

        Date birthday = Date.valueOf("2000-12-1");
        User testUser = new User("admin", passwordEncoder.encode("admin"), AuthProvider.local, "Adam",
                "Admin", "admin@mail.com", "https://i.imgur.com/VNNp6zWb.jpg", birthday, "+48880053535");

        userRepository.save(testUser);

        User testUser2 = new User("anowak", passwordEncoder.encode("anowak"), AuthProvider.local, "Adam",
                "Nowak", "anowak@mail.com", "https://i.imgur.com/VNNp6zWb.jpg", birthday, "+48880053535");

        userRepository.save(testUser2);

        User testUser3 = new User("jkwiatkowski", passwordEncoder.encode("jkwiatkowski"), AuthProvider.local, "Jan",
                "Kwiatkowski", "jkwiatkowski@mail.com", "https://i.imgur.com/VNNp6zWb.jpg", birthday, "+48880053535");

        userRepository.save(testUser3);

        addPeaks();

        peakRepository.findPeakByName("Rysy").ifPresent((peak) -> {
            PeakCompletion peakCompletion = new PeakCompletion(new PeakCompletionKey(testUser.getId(), peak.getId()), testUser, peak, Duration.ofMinutes(60));
            this.peakCompletionsRepository.save(peakCompletion);

            PeakCompletion peakCompletion2 = new PeakCompletion(new PeakCompletionKey(testUser2.getId(), peak.getId()), testUser2, peak, Duration.ofMinutes(120));
            this.peakCompletionsRepository.save(peakCompletion2);
        });

        peakRepository.findPeakByName("??nie??ka").ifPresent((peak) -> {
            PeakCompletion peakCompletion = new PeakCompletion(new PeakCompletionKey(testUser.getId(), peak.getId()), testUser, peak, Duration.ofMinutes(100));
            this.peakCompletionsRepository.save(peakCompletion);

            PeakCompletion peakCompletion2 = new PeakCompletion(new PeakCompletionKey(testUser2.getId(), peak.getId()), testUser2, peak, Duration.ofMinutes(140));
            this.peakCompletionsRepository.save(peakCompletion2);
        });

        peakRepository.findPeakByName("Babia G??ra").ifPresent((peak) -> {
            PeakCompletion peakCompletion = new PeakCompletion(new PeakCompletionKey(testUser2.getId(), peak.getId()), testUser2, peak, Duration.ofMinutes(110));
            this.peakCompletionsRepository.save(peakCompletion);
        });

        Photo photo = new Photo(testUser, "Wybra??em si?? z psem w g??ry", "dog.jpeg");
        wallItemRepository.save(photo);
        Post post = new Post(testUser3, "Jaki szczyt ostatnio zdobyli??cie?");
        wallItemRepository.save(post);

        Reaction reaction = new Reaction(new ReactionKey(testUser.getId(), post.getId()), ReactionType.LOVE);
        reactionsRepository.save(reaction);
        Reaction reaction2 = new Reaction(new ReactionKey(testUser2.getId(), post.getId()), ReactionType.LOVE);
        reactionsRepository.save(reaction2);

        PeakPost peakPost = new PeakPost(testUser, "Cze???? wszystkim! Kto chce ze mn?? zdoby?? Rysy?", peakRepository.findPeakByName("Rysy").get());
        peakPostsRepository.save(peakPost);

        friendshipRepository.save(new Friendship(1, testUser, testUser2));
        friendshipRepository.save(new Friendship(1, testUser2, testUser));
        friendshipRepository.save(new Friendship(0, testUser3, testUser));

        this.commentRepository.save(new Comment(testUser2, peakPost, "Oczywi??cie, ??e ja! Powiedz tylko kiedy :D"));
        this.commentRepository.save(new Comment(testUser, post, "??nie??ka, w tamtym tygodniu"));
        this.commentRepository.save(new Comment(testUser2, post, "Babia G??ra"));
    }

    private void clearRepositories() {
        this.chatMessageRepository.deleteAll();
        this.commentRepository.deleteAll();
        this.friendshipRepository.deleteAll();
        this.reactionsRepository.deleteAll();
        this.wallItemRepository.deleteAll();
        this.peakCompletionsRepository.deleteAll();
        this.userRepository.deleteAll();
        this.peakRepository.deleteAll();
    }

    private void addPeaks() {
        peakRepository.save(
                new Peak(
                        "Rysy",
                        2499,
                        "ma??opolskie",
                        "G??ra po??o??ona na granicy polsko-s??owackiej, w Tatrach Wysokich (jednej z cz????ci Tatr). Ma trzy wierzcho??ki, z kt??rych najwy??szy jest ??rodkowy (2501 metr??w nad poziomem morza), znajduj??cy si?? w ca??o??ci na terytorium S??owacji. Wierzcho??ek p????nocno-zachodni, przez kt??ry biegnie granica, stanowi najwy??ej po??o??ony punkt Polski i nale??y do Korony Europy.",
                        "Tatry",
                        "https://www.tatry-przewodnik.com.pl/images/rysy-i-niznie-1.jpg",
                        49.139050,
                        20.220381
                )
        );

        peakRepository.save(
                new Peak(
                        "Babia G??ra",
                        1725,
                        "ma??opolskie",
                        "Masyw g??rski w Pa??mie Babiog??rskim nale????cym do Beskidu ??ywieckiego w Beskidach Zachodnich. Jest najwy??szym szczytem Beskid??w Zachodnich i poza Tatrami najwy??szym szczytem w Polsce, drugim co do wybitno??ci (po ??nie??ce).",
                        "Beskid ??ywiecki",
                        "https://www.e-horyzont.pl/media/wysiwyg/jw-blog/2020/05/babia-gora2.jpg",
                        49.573333,
                        19.529444
                )
        );

        peakRepository.save(
                new Peak(
                        "??nie??ka",
                        1603,
                        "dolno??l??skie",
                        "Najwy??szy szczyt Karkonoszy oraz Sudet??w, jak r??wnie?? Czech, wojew??dztwa dolno??l??skiego, a tak??e ca??ego ??l??ska. Najwybitniejszy szczyt Polski i Czech.",
                        "Karkonosze",
                        "https://i.wpimg.pl/O/644x428/d.wpimg.pl/277550641-815155067/sniezka-karkonosze.jpg",
                        50.735833,
                        15.739167
                )
        );

        peakRepository.save(
                new Peak(
                        "??nie??nik",
                        1423,
                        "dolno??l??skie",
                        "Jest to najwy??szy, graniczny szczyt po polskiej stronie w Sudetach Wschodnich i w Masywie ??nie??nika, zwany te?? ??nie??nikiem K??odzkim. Jest on jedyn?? g??r?? w masywie ??nie??nika, kt??ra wystaje ponad g??rn?? granic?? lasu.",
                        "Masyw ??nie??nika",
                        "https://przyrodniczo.pl/wp-content/uploads/2017/11/snieznik3.jpg",
                        50.207004,
                        16.849226
                )
        );

        peakRepository.save(
                new Peak(
                        "Tarnica",
                        1346,
                        "podkarpackie",
                        "Najwy??szy szczyt polskich Bieszczad??w i wojew??dztwa podkarpackiego, wznosz??cy si?? na kra??cu pasma po??onin, w grupie tzw. gniazda Tarnicy i Halicza.",
                        "Bieszczady Zachodnie",
                        "https://upload.wikimedia.org/wikipedia/commons/2/27/Tarnica_%28HB1%29.jpg",
                        49.074778,
                        22.72675
                )
        );

        peakRepository.save(
                new Peak(
                        "Turbacz",
                        1310,
                        "ma??opolskie",
                        "Najwy??szy szczyt Gorc??w, znajduj??cy si?? w centralnym punkcie pasma i tworz??cy pot????ny rozr??g. Zbudowany jest z fliszu karpackiego. Z Turbacza odbiega siedem g??rskich grzbiet??w.",
                        "Gorce",
                        "https://upload.wikimedia.org/wikipedia/commons/3/38/Turbacz_%285%29.jpg",
                        49.542944,
                        20.111556
                )
        );

        peakRepository.save(
                new Peak(
                        "Radziejowa",
                        1266,
                        "ma??opolskie",
                        "Nazwa szczytu pochodzi od osoby o nazwisku lub przydomku Radziej. Znajduje si?? w g????wnym grzbiecie Pasma Radziejowej, pomi??dzy Wielkim Rogaczem, od kt??rego oddziela j?? prze????cz ????obki, a masywem Z??omistego Wierchu, po??o??onego za Prze????cz?? D??ug??. W 2006 r. na Radziejowej oddana zosta??a do u??ytku drewniana wie??a widokowa o wysoko??ci oko??o 20 m.",
                        "Beskid S??decki",
                        "https://u.profitroom.pl/2020-hotelczarnypotok-pl/thumb/0x1000/uploads/IMG_6124.JPG",
                        49.449444,
                        20.604444
                )
        );

        peakRepository.save(
                new Peak(
                        "Skrzyczne",
                        1257,
                        "??l??skie",
                        "Wznosi si?? w p????nocno-wschodniej cz????ci Beskidu ??l??skiego, w bocznym ramieniu pasma Baraniej G??ry, odga????ziaj??cym si?? od g????wnego pnia pasma w Malinowskiej Skale. Ze wzgl??du na charakterystyczn?? sylwetk?? g??ry, stromo opadaj??cej ku wschodowi i p????nocy oraz na po??o??ony na szczycie maszt nadajnika RTV jest ona ??atwo rozpoznawalna z wielu miejsc.",
                        "Beskid ??l??ski",
                        "https://beskidzkieapartamenty.pl/wp-content/uploads/2018/01/beskidzkie-5.jpg",
                        49.684444,
                        19.030278
                )
        );

        peakRepository.save(
                new Peak(
                        "Mogielica",
                        1171,
                        "ma??opolskie",
                        "Masyw Mogielicy wznosi si?? na obszarze trzech gmin: Dobra, S??opnice i Kamienica. Na szczycie oraz na p????nocnych stokach utworzono rezerwat przyrody Mogielica o powierzchni 50,44 ha. G????wnym celem rezerwatu jest ochrona g??uszca i jego biotopu oraz innych rzadkich gatunk??w ptak??w, ich siedlisk przyrodniczych, a tak??e form skalnych.",
                        "Beskid Wyspowy",
                        "http://www.planetagor.pl/img/uploads/images/p3052321_5cc7466a3255b.jpg",
                        49.655194,
                        20.276694
                )
        );

        peakRepository.save(
                new Peak(
                        "Wysoka Kopa",
                        1126,
                        "dolno??l??skie",
                        "??agodna kopu??a zbudowana z granit??w, gnejs??w i ??upk??w, nale????cych do bloku karkonosko-izerskiego, a ??ci??lej do jego p????nocno-zachodniej cz????ci ??? metamorfiku izerskiego. Jest bezle??na (w wyniku kl??ski ekologicznej lat 80. XX w.). Partie wierzcho??ka poro??ni??te s?? ????kami subalpejskimi z interesuj??cymi gatunkami ro??lin.",
                        "G??ry Izerskie",
                        "https://hasajacezajace.com/wp-content/uploads/2019/10/DSC_1834.jpg",
                        50.850278,
                        15.42
                )
        );

        peakRepository.save(
                new Peak(
                        "Rudawiec",
                        1106,
                        "podkarpackie",
                        "Masyw Rudawca wydzielaj??: dolina potoku Bielawka, dolina Czarnego Potoku i g????boka dolina Kun??ick??ho potoka, po??o??ona po czeskiej stronie G??r Bialskich. Zbudowany jest w ca??o??ci z ??upk??w metamorficznych i gnejs??w giera??towskich i poro??ni??ty w wi??kszo??ci przez lasy regla dolnego, a w partiach szczytowych rzadkim lasem regla g??rnego",
                        "G??ry Bialskie",
                        "https://upload.wikimedia.org/wikipedia/commons/0/0a/Gory_Bialskie.jpg",
                        50.244056,
                        16.975889
                )
        );

        peakRepository.save(
                new Peak(
                        "Orlica",
                        1084,
                        "dolno??l??skie",
                        "Le??y na europejskim dziale wodnym pomi??dzy zlewiskami Morza Ba??tyckiego i Morza P????nocnego. Dawniej nazywana by??a Mi??dzywierchem. Orlica zbudowana jest z ??upk??w ??yszczykowych z wk??adkami wapieni krystalicznych nale????cych do metamorfiku bystrzycko-orlickiego.",
                        "G??ry Orlickie",
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fd/Orlica.JPG/1200px-Orlica.JPG",
                        50.353186,
                        16.360719
                )
        );

        peakRepository.save(
                new Peak(
                        "Wysoka",
                        1050,
                        "ma??opolskie",
                        "Szczyt w Ma??ych Pieninach, po??o??ony na granicy polsko-s??owackiej i b??d??cy najwy??szym szczytem ca??ych Pienin i Pieni??skiego Pasa Ska??kowego. Zbudowany jest z czerwonych wapieni krynoidowych. Ca??a g??ra poro??ni??ta jest lasem, jedynie sam wierzcho??ek ma charakter kopu??y skalnej wystaj??cej ponad lini?? ??wierkowego lasu. ",
                        "Pieniny",
                        "https://mynaszlaku.pl/wp-content/gallery/wysoka-16-09-2019/Wysoka-male-pieniny0023.jpg",
                        49.380278,
                        20.555556
                )
        );

        peakRepository.save(
                new Peak(
                        "Wielka Sowa",
                        1015,
                        "dolno??l??skie",
                        "Najwy??szy szczyt G??r Sowich w Sudetach ??rodkowych, z najbardziej zniszczonym ekologicznie drzewostanem w tych g??rach. G??ra le??y w dorzeczu Odry, a dok??adniej na granicy mi??dzy dorzeczami jej dop??yw??w ??? Bystrzycy i Nysy K??odzkiej. Zbocza Wielkiej Sowy s?? znakomitymi terenami narciarskimi.",
                        "G??ry Sowie",
                        "https://agropajda.pl/wp-content/uploads/2020/06/Wielka_Sowa_1015_m_n_p_m_435013.jpg",
                        50.680158,
                        16.485497
                )
        );

        peakRepository.save(
                new Peak(
                        "Lackowa",
                        997,
                        "ma??opolskie",
                        "Najwy??szy szczyt po polskiej stronie Beskidu Niskiego, po??o??ony mi??dzy Krynic??-Zdr??j a Wysow??, na granicy ze S??owacj??. Zachodni stok g??ry, kt??rym biegnie czerwony szlak turystyczny, jest najbardziej stromym w Beskidzie Niskim i jednym z najbardziej stromych w polskich g??rach (poza Tatrami) odcinkiem znakowanego szlaku.",
                        "Beskid Niski",
                        "https://gdziebytudalej.pl/wp-content/uploads/2018/06/01.-W-drodze-do-Izb.-Wszystkie-drogi-prowadz%C4%85-na-Lackow%C4%85-e1528315954381.jpg",
                        49.428333,
                        21.096111
                )
        );

        peakRepository.save(
                new Peak(
                        "Kowad??o",
                        989,
                        "dolno??l??skie",
                        "Ze szczytu mo??na podziwia?? widoki na G??ry Z??ote i p????nocn?? cz?????? Wysokiego Jesionika oraz pog??rze sudeckie a?? po Nys?? a nawet Opole. Kopulasty wierzcho??ek porastaj?? m??ode ??wierki, gdzieniegdzie wyst??puj?? gnejsowe ska??ki.",
                        "G??ry Z??ote",
                        "https://mynaszlaku.pl/wp-content/gallery/kowadlo-19-08-2019/Kowadlo-gory-zlote0007.jpg",
                        50.264433,
                        17.013219
                )
        );

        peakRepository.save(
                new Peak(
                        "Jagodna",
                        977,
                        "dolno??l??skie",
                        "Wzniesienie po??o??one jest w ??rodkowo-wschodniej cz????ci G??r Bystrzyckich oko??o 5 km na zach??d od miejscowo??ci D??ugopole-Zdr??j. Przez Jagodn?? w XVI wieku przebiega??a granica pomi??dzy Czechami a hrabstwem k??odzkim, kt??r?? p????niej przesuni??to do Doliny Dzikiej Orlicy.",
                        "G??ry Bystrzyckie",
                        "https://hasajacezajace.com/wp-content/uploads/2020/10/DSC05389.jpg",
                        50.252461,
                        16.564417
                )
        );

        peakRepository.save(
                new Peak(
                        "Skalnik",
                        945,
                        "dolno??l??skie",
                        "Szczyt w po??udniowo-zachodniej Polsce, w Sudetach Zachodnich, w Rudawach Janowickich. Jest to wzniesienie o kopulastym kszta??cie, na kt??rym znajduj?? si?? ruiny dawnej wie??y widokowej (cztery betonowe bloki na kt??rych wie??a by??a zakotwiczona), oraz na drzewie drewniana tabliczka z napisem Skalnik 945 m n.p.m.",
                        "Rudawy Janowickie",
                        "https://media.villagreta.pl/m/2018/08/skalnik-rudawy-janowickie-1.jpg",
                        50.808469,
                        15.900069
                )
        );

        peakRepository.save(
                new Peak(
                        "Walig??ra",
                        936,
                        "dolno??l??skie",
                        "Najwy??szy szczyt G??r Suchych i jednocze??nie ca??ych G??r Kamiennych. Mimo obecnego braku punktu widokowego Walig??ra pozostaje popularnym celem wycieczek pieszych, g????wnie dzi??ki bezpo??redniej blisko??ci Prze????czy Trzech Dolin, na kt??rej znajduje si?? du??y w??ze?? szlak??w, schronisko Andrzej??wka i tereny do narciarstwa biegowego i zjazdowego.",
                        "G??ry Kamienne",
                        "https://www.czar-gor.pl/galeria/DSC_5650-1170x780.jpg",
                        50.683333,
                        16.283333
                )
        );

        peakRepository.save(
                new Peak(
                        "Czupel",
                        933,
                        "??l??skie",
                        "Na grzbiecie Czupla wzd??u?? szlaku turystycznego znajduj?? si?? d??ugie wa??y kamieni zbieranych z dawnych hal. Miejscowa ludno???? nazywa je kr??dami. Istniej?? te?? resztki okop??w z okresu II wojny ??wiatowej.",
                        "Beskid Ma??y",
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a3/POLAND_BeskidMaly_Czupel_933m.jpg/1200px-POLAND_BeskidMaly_Czupel_933m.jpg",
                        49.766111,
                        19.155278

                )
        );

        peakRepository.save(
                new Peak(
                        "Szczeliniec Wielki",
                        919,
                        "dolno??l??skie",
                        "Jeden z najwi??kszych atrakcji turystycznych Sudet??w, z rezerwatem krajobrazowym i tarasami widokowymi z panoram?? Sudet??w. Najwy??szym punktem jest Fotel Pradziada. Pomimo niedu??ej wysoko??ci bezwzgl??dnej szczyt ten jest widoczny ju?? z daleka jako trapezoidalny blok skalny poro??ni??ty lasem iglastym. ",
                        "G??ry Sto??owe",
                        "https://atrakcje-ziemiaklodzka.pl/wp-content/uploads/2018/11/szczeliniec-wielki.jpg",
                        50.485833,
                        16.339167
                )
        );

        peakRepository.save(
                new Peak(
                        "Lubomir",
                        904,
                        "ma??opolskie",
                        "Szczyt w Pa??mie Lubomira i ??ysiny. Nazwa szczytu pochodzi od nazwiska ksi??cia Kazimierza Lubomirskiego. Nadano j?? w 1932 r. w uznaniu jego zas??ug. Wierzcho??ek Lubomira jest ca??kowicie zalesiony, wi??c pozbawiony widok??w.",
                        "Beskid Makowski",
                        "https://mynaszlaku.pl/wp-content/gallery/lubomir-27-08-2019/lubomir-korona-gor-polski0009.jpg",
                        49.766944,
                        20.059722
                )
        );

        peakRepository.save(
                new Peak(
                        "Biskupia Kopa",
                        889,
                        "opolskie",
                        "Przyjmuje si??, ??e Biskupia Kopa jest najwy??szym szczytem w polskiej cz????ci G??r Opawskich oraz najwy??szym wzniesieniem wojew??dztwa opolskiego, chocia?? najwy??szy jej punkt, wraz z zabytkow?? wie???? widokow??, znajduje si?? po czeskiej stronie. Pod koniec XIX wieku wyznaczono pierwsze szlaki prowadz??ce na szczyt oraz wybudowano czynn?? do dzi?? wie???? widokow??.",
                        "G??ry Opawskie",
                        "https://marcinkarpinski.pl/image/korona-gor-polski/biskupiakopa/biskupiakopa.jpg",
                        50.256002,
                        17.422791
                )
        );

        peakRepository.save(
                new Peak(
                        "Che??miec",
                        851,
                        "dolno??l??skie",
                        "Najwy??szy punkt w granicach administracyjnych Szczawna-Zdroju i drugi co do wysoko??ci szczyt G??r Wa??brzyskich. Stanowi kulminacj??, wyra??nie dominuj??c?? w wa??brzyskim krajobrazie. Wynika to z jego ukszta??towania w formie kopu??y, przez co jest ??atwo rozpoznawalny, nawet z odleg??ego o 70 km Wroc??awia.",
                        "G??ry Wa??brzyskie",
                        "https://hasajacezajace.com/wp-content/uploads/2020/11/IMG_20200815_180214.jpg",
                        50.779167,
                        16.210278
                )
        );

        peakRepository.save(
                new Peak(
                        "K??odzka G??ra",
                        765,
                        "dolno??l??skie",
                        "Nale??y do g????wnego grzbietu G??r Bardzkich, stanowi??c jego zwornik (rozr??g) z odchodz??cymi w czterech kierunkach grzbietami. Poro??ni??ta w ca??o??ci lasem ??wierkowym, w partii szczytowej przerzedzony. Na szczyt prowadzi ??????ty szlak pieszy wychodz??cy z K??odzka.",
                        "G??ry Bardzkie",
                        "https://hasajacezajace.com/wp-content/uploads/2020/10/DSC05413.jpg",
                        50.451653,
                        16.753211
                )
        );

        peakRepository.save(
                new Peak(
                        "Skopiec",
                        724,
                        "dolno??l??skie",
                        "Trzeci co do wysoko??ci szczyt G??r Kaczawskich. Wyrasta w ??rodkowej cz????ci Grzbietu Po??udniowego, w kszta??cie s??abo zaznaczonej kopu??y, z niewyra??nie podkre??lon?? cz????ci?? szczytow?? i ??rednio stromymi zboczami. Powierzchnia wierzchowiny jest tak wyr??wnana, ??e wierzcho??ek jest trudno rozpoznawalny w terenie.",
                        "G??ry Kaczawskie",
                        "https://upload.wikimedia.org/wikipedia/commons/9/9a/Skopiec_mit2.JPG",
                        50.944444,
                        15.885
                )
        );

        peakRepository.save(
                new Peak(
                        "??l????a",
                        718,
                        "dolno??l??skie",
                        "Jest obj??ta ochron?? jako Rezerwat krajobrazowo-geologiczny i historyczny. Ze wzgl??du na blisko???? Wroc??awia, ??widnicy i Dzier??oniowa odbywa si?? tam sezonowa turystyka. Masyw i jego okolice pokryte s?? do???? g??st?? sieci?? szlak??w turystycznych. Istniej?? dwie ??cie??ki archeologiczne oznaczone symbolem ??l????a??skiego nied??wiedzia i szlaki rowerowe.",
                        "Masyw ??l????y",
                        "https://outdoorzy.pl/blog/wp-content/uploads/2018/08/IMG_20180715_111834.jpg",
                        50.865,
                        16.708611
                )
        );

        peakRepository.save(
                new Peak(
                        "??ysica",
                        614,
                        "??wi??tokrzyskie",
                        "Ma dwa wierzcho??ki. Wschodni wierzcho??ek, nazywany Ska???? Agaty lub Zamczyskiem oraz zachodni, na kt??rym znajduje si?? replika pami??tkowego krzy??a z 1930 r. oraz nieliczne pozosta??o??ci wie??y triangulacyjnej. Podania m??wi??, ??e u podn????a ??ysicy istnia??a kiedy?? s??owia??ska ??wi??tynia, na miejscu kt??rej obecnie znajduje si?? klasztor si??str bernardynek.",
                        "G??ry ??wi??tokrzyskie",
                        "https://wmeritum.pl/wp-content/uploads/2019/11/%C5%81ysica.jpg",
                        50.890833,
                        20.900833
                )
        );
    }
}
