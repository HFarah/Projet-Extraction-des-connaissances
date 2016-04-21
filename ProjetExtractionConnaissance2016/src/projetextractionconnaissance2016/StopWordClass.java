
package projetextractionconnaissance2016;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class StopWordClass {
    
    private static ArrayList<String> listStopWord;
    
    private static ArrayList<String> getStopWord()
    {
        
        if(listStopWord==null)
        {
             try {
            
                    listStopWord=new ArrayList<>();

                    Connection con=ConnexionDB.getConnection();

                    Statement requete2 = con.createStatement();

                    ResultSet rs=requete2.executeQuery("SELECT \"MOTIND\" FROM \"motIndecidable\"");

                    while(rs.next())
                    {
                        listStopWord.add(rs.getString("MOTIND"));
                    }
            
                } catch (ClassNotFoundException | SQLException e) {

                    e.printStackTrace();

                }

        }
        
        return listStopWord;
       
    }
    
    
    static boolean isStopWord(String mot)
    {

        return getStopWord().contains(mot);
        
        /*listStopWord.add("not"); 
        listStopWord.add("be");
        listStopWord.add("warehouse");
        listStopWord.add("dude");
        listStopWord.add("episode");
        listStopWord.add("news");
        listStopWord.add("short");
        listStopWord.add("tank");
        listStopWord.add("busby");
        listStopWord.add("dean");
        listStopWord.add("plane");
        listStopWord.add("material");
        listStopWord.add("house");
        listStopWord.add("sex");
        listStopWord.add("van");
        listStopWord.add("johnny");
        listStopWord.add("chamberlain");
        listStopWord.add("Saturday");
        listStopWord.add("theater");
        listStopWord.add("Egyptian");
        listStopWord.add("Schmidt");
        listStopWord.add("mythology");
        listStopWord.add("Scottish");
        listStopWord.add("ladder");
        listStopWord.add("Hindi");
        listStopWord.add("Schepisi");
        listStopWord.add("doodle");
        listStopWord.add("mat");
        listStopWord.add("vagina");
        listStopWord.add("Hammett");
        listStopWord.add("vow");
        listStopWord.add("rat");
        listStopWord.add("refugee");
        listStopWord.add("climber");
        listStopWord.add("shelly");
        listStopWord.add("lamm");
        listStopWord.add("solitary");
        listStopWord.add("endangered");
        listStopWord.add("vixen");
        listStopWord.add("Vatican");
        listStopWord.add("nymph");
        listStopWord.add("tiffany");
        listStopWord.add("gob");
        listStopWord.add("daughter");
        listStopWord.add("hero");
        listStopWord.add("trash");
        listStopWord.add("bet");
        listStopWord.add("prisoner");
        listStopWord.add("author");
        listStopWord.add("parent");
        listStopWord.add("law");
        listStopWord.add("sequel");
        listStopWord.add("genre");
        listStopWord.add("Ann");
        listStopWord.add("cell");
        listStopWord.add("realism");
        listStopWord.add("chick");
        listStopWord.add("Christian");
        listStopWord.add("morning");
        listStopWord.add("version");
        listStopWord.add("comedy");
        listStopWord.add("director");
        listStopWord.add("tower");
        listStopWord.add("Nazi");
        listStopWord.add("identity");
        listStopWord.add("jimmy");
        listStopWord.add("creature");
        listStopWord.add("kiss");
        listStopWord.add("opener");
        listStopWord.add("gambler");
        listStopWord.add("nana");
        listStopWord.add("firehouse");
        listStopWord.add("dominion");
        listStopWord.add("matt");
        listStopWord.add("bow");
        listStopWord.add("sultan");
        listStopWord.add("vizier");
        listStopWord.add("yakuza");
        listStopWord.add("icon");
        listStopWord.add("loan");
        listStopWord.add("nurse");
        listStopWord.add("carrier");
        listStopWord.add("leather");
        listStopWord.add("meter");
        listStopWord.add("flavor");
        listStopWord.add("madman");
        listStopWord.add("raj");
        listStopWord.add("Starship");
        listStopWord.add("neat");
        listStopWord.add("alert");
        listStopWord.add("et");
        listStopWord.add("phoenix");
        listStopWord.add("filter");
        listStopWord.add("kitten");
        listStopWord.add("eight");
        listStopWord.add("Paltrow");
        listStopWord.add("dancing");
        listStopWord.add("Cagney");
        listStopWord.add("german");
        listStopWord.add("soprano");
        listStopWord.add("robin");
        listStopWord.add("justice");
        listStopWord.add("teacher");
        listStopWord.add("duke");
        listStopWord.add("maggot");
        listStopWord.add("chimp");
        listStopWord.add("mummy");
        listStopWord.add("sally");
        listStopWord.add("breakfast");
        listStopWord.add("tremor");
        listStopWord.add("omer");
        listStopWord.add("sole");
        listStopWord.add("shiny");
        listStopWord.add("doctor");
        listStopWord.add("talk");
        listStopWord.add("prison");
        listStopWord.add("earth");
        listStopWord.add("bill");
        listStopWord.add("captain");
        listStopWord.add("tap");
        listStopWord.add("spy");
        listStopWord.add("eric");
        listStopWord.add("gas");
        listStopWord.add("action");
        listStopWord.add("ship");
        listStopWord.add("clothes");
        listStopWord.add("temple");
        listStopWord.add("sally");
        listStopWord.add("shank");
        listStopWord.add("engine");
        listStopWord.add("bunny");
        listStopWord.add("Greek");
        listStopWord.add("toad");
        listStopWord.add("communism");
        listStopWord.add("brainwash");
        listStopWord.add("shark");
        listStopWord.add("rope");
        listStopWord.add("sole");
        listStopWord.add("chimp");
        listStopWord.add("maggot");
        listStopWord.add("jane");
        listStopWord.add("begin");
        listStopWord.add("thief");
        listStopWord.add("season");
        listStopWord.add("heir");
        listStopWord.add("john");
        listStopWord.add("history");
        listStopWord.add("mountain");
        listStopWord.add("western");
        listStopWord.add("Chinese");
        listStopWord.add("Japanese");
        listStopWord.add("why");
        listStopWord.add("ha");
        listStopWord.add("rod");
        listStopWord.add("carpenter");
        listStopWord.add("corporation");
        listStopWord.add("organization");
        listStopWord.add("jersey");
        listStopWord.add("rabbit");
        listStopWord.add("Scheider");
        listStopWord.add("haunting");
        listStopWord.add("ration");
        listStopWord.add("beating");
        listStopWord.add("cynicism");
        listStopWord.add("guilt");
        listStopWord.add("Norman");
        listStopWord.add("cloud");
        listStopWord.add("bus");
        listStopWord.add("dinosaur");
        listStopWord.add("British");
        listStopWord.add("relationship");
        listStopWord.add("billy");
        listStopWord.add("tony");
        listStopWord.add("rock");
        listStopWord.add("son");
        listStopWord.add("father");
        listStopWord.add("lesbian");
        listStopWord.add("graphics");
        listStopWord.add("riest");
        listStopWord.add("baby");
        listStopWord.add("cry");
        listStopWord.add("sky");
        listStopWord.add("begin");
        listStopWord.add("whale");
        listStopWord.add("sunset");
        listStopWord.add("trek");
        listStopWord.add("seventh");
        listStopWord.add("friendly");
        listStopWord.add("iceberg");
        listStopWord.add("gray");
        listStopWord.add("zone");
        listStopWord.add("ocean");
        listStopWord.add("fruit");
        listStopWord.add("ick");
        listStopWord.add("league");
        listStopWord.add("rocket");
        listStopWord.add("independent");
        listStopWord.add("lee");
        listStopWord.add("Michael");
        listStopWord.add("animal");
        listStopWord.add("trilogy");
        listStopWord.add("slasher");
        listStopWord.add("werewolf");
        listStopWord.add("abbot");
        listStopWord.add("bunker");
        listStopWord.add("naval");
        listStopWord.add("electrician");
        listStopWord.add("janitor");
        listStopWord.add("resurgence");
        listStopWord.add("Norton");
        listStopWord.add("rover");
        listStopWord.add("yam");
        listStopWord.add("baton");
        listStopWord.add("des");
        listStopWord.add("flea");
        listStopWord.add("gable");
        listStopWord.add("coach");
        listStopWord.add("yea");
        listStopWord.add("choir");
        listStopWord.add("siren");
        listStopWord.add("wiseguy");
         listStopWord.add("guy");
         listStopWord.add("chief");
        listStopWord.add("disabled");
        listStopWord.add("gilbert");
        listStopWord.add("empress");
        listStopWord.add("lulu");
        listStopWord.add("Dex");
        listStopWord.add("chasm");
        listStopWord.add("concrete");
        listStopWord.add("hopper");
        listStopWord.add("town");
        listStopWord.add("street");
        listStopWord.add("pig");
        listStopWord.add("Mamet");
        listStopWord.add("private");
        listStopWord.add("firefighter");
        listStopWord.add("Aussie");
        listStopWord.add("Asian");
        listStopWord.add("mafia");
        listStopWord.add("sand");
        listStopWord.add("car");
        listStopWord.add("fly");
        listStopWord.add("previous");
        listStopWord.add("devil");
        listStopWord.add("smith");
        listStopWord.add("crouse");
        listStopWord.add("Daniel");
        listStopWord.add("ruby");
        listStopWord.add("musical");
        listStopWord.add("v");
        listStopWord.add("native");
        
        listStopWord.add("company");
        listStopWord.add("ken");
        listStopWord.add("Burgundy");
        listStopWord.add("revolver");
        listStopWord.add("psychiatrist");
        listStopWord.add("rhyme");
        listStopWord.add("Edison");
        listStopWord.add("format");
        listStopWord.add("formula");
        listStopWord.add("meat");
        listStopWord.add("landing");
        listStopWord.add("boob");
        listStopWord.add("pink");
        listStopWord.add("house");
        listStopWord.add("die");
        listStopWord.add("land");
        listStopWord.add("central");
        listStopWord.add("program");
        listStopWord.add("university");
        listStopWord.add("fireman");
        
        listStopWord.add("Mitchell");
        listStopWord.add("puri");
        listStopWord.add("unseen");
        listStopWord.add("hood");
        listStopWord.add("Stanwyck");
        listStopWord.add("omega");
        listStopWord.add("astronaut");
        listStopWord.add("eater");
        listStopWord.add("knight");
        listStopWord.add("zeta");
        listStopWord.add("cradle");
        listStopWord.add("lady");
        listStopWord.add("mask");
        listStopWord.add("festival");
        listStopWord.add("song");
        listStopWord.add("cat");
        listStopWord.add("roll");
        listStopWord.add("area");
        listStopWord.add("estate");
        listStopWord.add("homeless");
        
         listStopWord.add("baker");
        listStopWord.add("triad");
        listStopWord.add("chuck");
        listStopWord.add("nd");
        listStopWord.add("divorce");
        listStopWord.add("shanghai");
        listStopWord.add("supernatural");
        listStopWord.add("waterfall");
        listStopWord.add("sleazy");
        listStopWord.add("apollo");
        listStopWord.add("tit");
        listStopWord.add("lake");
        listStopWord.add("heath");
        listStopWord.add("village");
        listStopWord.add("uniform");
        listStopWord.add("abbey");
        listStopWord.add("astronaut");
        listStopWord.add("baby");
        listStopWord.add("basketball");
        listStopWord.add("batman");
        listStopWord.add("Berkeley");
        listStopWord.add("biography");
        listStopWord.add("bird");
        listStopWord.add("birthday");
        listStopWord.add("boat");
        listStopWord.add("bodyguard");
        listStopWord.add("bolt");
        listStopWord.add("border");
        listStopWord.add("bowser");
        listStopWord.add("British");
        listStopWord.add("Cagney");
        listStopWord.add("Canadian");
        listStopWord.add("cell");
        listStopWord.add("cent");
        listStopWord.add("chamberlain");
        listStopWord.add("charlie");
        listStopWord.add("cheese");
        listStopWord.add("Cinderella");
        listStopWord.add("city");
        listStopWord.add("cliffhanger");
        listStopWord.add("comedy");
        listStopWord.add("con");
        listStopWord.add("conference");
        listStopWord.add("convent");
        listStopWord.add("cop");
        listStopWord.add("Corp");
        listStopWord.add("cypher");
        listStopWord.add("da");
        listStopWord.add("dancer");
        listStopWord.add("Danish");
        listStopWord.add("datum");
        listStopWord.add("detective");
        listStopWord.add("Digi");
        listStopWord.add("dinosaur");
        listStopWord.add("doc");
        listStopWord.add("documentary");
        listStopWord.add("Donner");
        listStopWord.add("Duk");
        listStopWord.add("editor");
        listStopWord.add("election");
        listStopWord.add("fiction");
        listStopWord.add("fireman");
        listStopWord.add("flight");
        listStopWord.add("Flynn");
        listStopWord.add("footage");
        listStopWord.add("fox");
        listStopWord.add("fruit");
        listStopWord.add("fu");
        listStopWord.add("Gaelic");
        listStopWord.add("game");
        listStopWord.add("german");
        listStopWord.add("glover");
        listStopWord.add("goa");
        listStopWord.add("gown");
        listStopWord.add("graphics");
        listStopWord.add("gray");
        listStopWord.add("harry");
        listStopWord.add("Irish");
        listStopWord.add("Italian");
        listStopWord.add("jack");
        listStopWord.add("Jake");
        listStopWord.add("john");
        listStopWord.add("johnny");
        listStopWord.add("ken");
        listStopWord.add("Ki");
        listStopWord.add("kor");
        listStopWord.add("kung");
        listStopWord.add("lane");
        listStopWord.add("Lloyd");
        listStopWord.add("magnolia");
        listStopWord.add("manner");
        listStopWord.add("message");
        listStopWord.add("missile");
        listStopWord.add("monkey");
        listStopWord.add("muller");
        listStopWord.add("mummy");
        listStopWord.add("nelson");
        listStopWord.add("nothing");
        listStopWord.add("number");
        listStopWord.add("officer");
        listStopWord.add("overall");
        listStopWord.add("parade");
        listStopWord.add("passenger");
        listStopWord.add("perry");
        listStopWord.add("phoenix");
        listStopWord.add("planet");
        listStopWord.add("poem");
        listStopWord.add("puri");
        listStopWord.add("recording");
        listStopWord.add("reporter");
        listStopWord.add("resident");
        listStopWord.add("robin");
        listStopWord.add("Roy");
        listStopWord.add("salt");
        listStopWord.add("Scheider");
        listStopWord.add("science");
        listStopWord.add("scientist");
        listStopWord.add("season");
        listStopWord.add("segment");
        listStopWord.add("serial");
        listStopWord.add("series");
        listStopWord.add("shank");
        listStopWord.add("ship");
        listStopWord.add("snafu");
        listStopWord.add("snake");
        listStopWord.add("soldier");
        listStopWord.add("storey");
        listStopWord.add("study");
        listStopWord.add("tank");
        listStopWord.add("Technicolor");
        listStopWord.add("titanic");
        listStopWord.add("tony");
        listStopWord.add("translation");
        listStopWord.add("triad");
        listStopWord.add("user");
        listStopWord.add("van");
        listStopWord.add("verger");
        listStopWord.add("Virginia");
        listStopWord.add("voyage");
        listStopWord.add("war");
        listStopWord.add("Wendy");
        listStopWord.add("wilde");
        listStopWord.add("Williams");
        listStopWord.add("woman");
        listStopWord.add("wood");
        listStopWord.add("yeti");
        listStopWord.add("york");
        listStopWord.add("Babylon");
        listStopWord.add("batmobile");
        listStopWord.add("Brien");
        listStopWord.add("Buchanan");
        listStopWord.add("butterfly");
        listStopWord.add("Cameroonian");
        listStopWord.add("Cheung");
        listStopWord.add("church");
        listStopWord.add("clan");
        listStopWord.add("comrade");
        listStopWord.add("cradle");
        listStopWord.add("cut");
        listStopWord.add("daughter");
        listStopWord.add("Dominican");
        listStopWord.add("dower");
        listStopWord.add("Edison");
        listStopWord.add("episode");
        listStopWord.add("gangster");
        listStopWord.add("goitre");
        listStopWord.add("grandfather");
        listStopWord.add("grease");
        listStopWord.add("guinea");
        listStopWord.add("killer");
        listStopWord.add("kung");
        listStopWord.add("lama");
        listStopWord.add("math");
        listStopWord.add("mona");
        listStopWord.add("make");
        listStopWord.add("good");
        listStopWord.add("like");
        listStopWord.add("well");
        listStopWord.add("see");
        listStopWord.add("bad");
        listStopWord.add("character");
        listStopWord.add("story");
        listStopWord.add("look");
        listStopWord.add("great");
        listStopWord.add("really");
        listStopWord.add("genie");
        listStopWord.add("Mr");
        listStopWord.add("blah");
        
        listStopWord.add("eyre");
        listStopWord.add("Lil");
        listStopWord.add("Sunday");
        listStopWord.add("gay");
        listStopWord.add("Mrs");
        listStopWord.add("soap");
        
        listStopWord.add("ab");
        listStopWord.add("Abo");
        listStopWord.add("airport");
        listStopWord.add("Ori");
        listStopWord.add("cigar");
        listStopWord.add("goo");
        
        listStopWord.add("houseman");
        listStopWord.add("ria");
        listStopWord.add("rabbet");
        listStopWord.add("Claire");
        listStopWord.add("Roz");
        listStopWord.add("chi");
        
        listStopWord.add("momma");
        listStopWord.add("bozo");
        listStopWord.add("Hamm");
        listStopWord.add("pollack");
        listStopWord.add("petrol");
        listStopWord.add("Rosie"); 
        
        listStopWord.add("fang");
        listStopWord.add("nine");
        listStopWord.add("Je");
        listStopWord.add("villa");
        listStopWord.add("default");
        listStopWord.add("amrita");
        
        
        listStopWord.add("Manmohan");
        listStopWord.add("Desai");
        listStopWord.add("anil");
        listStopWord.add("get");
        listStopWord.add("rap");
        
        listStopWord.add("robot");
        listStopWord.add("film");
        listStopWord.add("noir");
        listStopWord.add("girl");
        listStopWord.add("list"); 
                
                listStopWord.add("Indian");
        listStopWord.add("le");
        listStopWord.add("Europa");
        listStopWord.add("fab");
        listStopWord.add("pr");
        listStopWord.add("raja"); 
        listStopWord.add("Entwistle"); 
        
        
        listStopWord.add("network");
        listStopWord.add("axel");
        listStopWord.add("rudd");
        listStopWord.add("cabinet");
        listStopWord.add("Gael");
        listStopWord.add("Kong"); 
        listStopWord.add("direction");
        listStopWord.add("thumb");
        listStopWord.add("gag");

        listStopWord.add("Finnish");
        listStopWord.add("Homerian");
        listStopWord.add("Latham");
        listStopWord.add("Gillen");
        listStopWord.add("Jewish");
        listStopWord.add("Kennedy"); 
        listStopWord.add("script");
        listStopWord.add("seven");
        listStopWord.add("date"); 
        
        listStopWord.add("human");
        listStopWord.add("Gerard");
        listStopWord.add("gong");
        listStopWord.add("Blyth");
        listStopWord.add("harem");
        listStopWord.add("nancy"); 
        listStopWord.add("Marchand");
        listStopWord.add("ma");
        listStopWord.add("Mt"); 
                
                
        
          listStopWord.add("Bradford");
        listStopWord.add("jai");
        listStopWord.add("Gypsy");
        listStopWord.add("Gardner");
        listStopWord.add("Ava");
        listStopWord.add("tan"); 
        listStopWord.add("Gerard");
        listStopWord.add("Lundgren");
        listStopWord.add("alias"); 
                
          listStopWord.add("Koran");
        listStopWord.add("Russ");
        listStopWord.add("student");
        listStopWord.add("machine");
        listStopWord.add("Australian");
        listStopWord.add("x"); 
        listStopWord.add("code");
        listStopWord.add("ray");
        listStopWord.add("de");       
           
        
         listStopWord.add("white");
        listStopWord.add("piece");
        listStopWord.add("family");
        listStopWord.add("ex");
        listStopWord.add("food");
        listStopWord.add("pain"); 
        listStopWord.add("tell");
        listStopWord.add("Satan");
        listStopWord.add("bra");  
        
        listStopWord.add("Satan");
        listStopWord.add("cox");
        listStopWord.add("family");
        listStopWord.add("freeman");
        listStopWord.add("quit");
        listStopWord.add("beautiful"); 
        listStopWord.add("conductor");
        listStopWord.add("bud");
        listStopWord.add("dark"); 
        listStopWord.add("moon"); 

        listStopWord.add("joe"); 
        listStopWord.add("American"); 
        listStopWord.add("coffee"); 
        listStopWord.add("million");
        
        listStopWord.add("Russian"); 
        listStopWord.add("fifth"); 
        
        listStopWord.add("French"); 
        listStopWord.add("dingo"); 
        listStopWord.add("Buddhist"); 
        listStopWord.add("Wilcox");
        
        
        listStopWord.add("Calvert"); 
        listStopWord.add("Audrey"); 
        listStopWord.add("Scotsman"); 
        listStopWord.add("buddha");
        
        
        listStopWord.add("Caswell"); 
        listStopWord.add("vent"); 
        listStopWord.add("birthplace"); 
        listStopWord.add("Parkersburg");
        
        listStopWord.add("barracuda"); 
        listStopWord.add("Dante"); 
        listStopWord.add("Pierpont"); 
        listStopWord.add("Melvin");
                
         listStopWord.add("Jeroen"); 
        listStopWord.add("Depardieu"); 
        listStopWord.add("aerobics"); 
        listStopWord.add("Lincoln");       
         
        listStopWord.add("Blaisdell"); 
        listStopWord.add("grey"); 
        listStopWord.add("Bryson"); 
        listStopWord.add("Brooke");  
        
           listStopWord.add("Humphrey"); 
        listStopWord.add("Knightley"); 
        listStopWord.add("Meredith"); 
        listStopWord.add("Bogdanovich");  
        
        listStopWord.add("Switzerland"); 
        listStopWord.add("boss"); 
        listStopWord.add("gang"); 
        listStopWord.add("ninth"); 
        
        listStopWord.add("Russell"); 
        listStopWord.add("fog"); 
        listStopWord.add("Dashiell"); 
        listStopWord.add("sexism"); 
        
         listStopWord.add("librarian"); 
        listStopWord.add("pa"); 
        listStopWord.add("Romanian"); 
        
        listStopWord.add("Jew"); 
        listStopWord.add("jock"); 
        listStopWord.add("Fairfax"); 
        
        
        listStopWord.add("dye"); 
        listStopWord.add("child"); 
        listStopWord.add("studio"); 
        
        listStopWord.add("teaching"); 
        listStopWord.add("opinion"); 
        listStopWord.add("lesson");
        
        listStopWord.add("patch"); 
        listStopWord.add("kris"); 
        listStopWord.add("krik");
        
        listStopWord.add("wax"); 
        listStopWord.add("boll"); 
        listStopWord.add("krik");
        
        
        listStopWord.add("bag"); 
        listStopWord.add("jerk"); 
        listStopWord.add("television");
        
          listStopWord.add("Burgundian"); 
        listStopWord.add("jerk"); 
        listStopWord.add("television");
         listStopWord.add("nail"); 
        listStopWord.add("envy"); 
        listStopWord.add("today");
        
                
                
                
        listStopWord.add("job"); 
        listStopWord.add("teach"); 
        listStopWord.add("center");
        listStopWord.add("eye"); 
        listStopWord.add("jo"); 
        listStopWord.add("mega");
        
        listStopWord.add("@"); 
        listStopWord.add("aa");
        listStopWord.add("abash");

        
        listStopWord.add("Parnell"); 
        listStopWord.add("teach"); 
        listStopWord.add("Thursby");
        listStopWord.add("twain"); 
        listStopWord.add("Parnell"); 
        listStopWord.add("mega");
        
        listStopWord.add("Odysseus"); 
        listStopWord.add("whatsit");
        listStopWord.add("jewelry");
        
        
        listStopWord.add("cliff"); 
        listStopWord.add("vial"); 
        listStopWord.add("dustbin");
        listStopWord.add("sail"); 
        listStopWord.add("Bauer"); 
        listStopWord.add("viola");
        
        listStopWord.add("shea"); 
        listStopWord.add("airline");
        listStopWord.add("burr");
        
        listStopWord.add("mini"); 
        listStopWord.add("bohemia");
        listStopWord.add("mason");
        listStopWord.add("alien");
        listStopWord.add("semen");
        
        listStopWord.add("sierra"); 
        listStopWord.add("noon");
        listStopWord.add("file");
        listStopWord.add("Grimm");
        listStopWord.add("babe");
        
        
        listStopWord.add("Crowe"); 
        listStopWord.add("Mormon");
        listStopWord.add("Bronte");
        listStopWord.add("Vicki");
        listStopWord.add("cameraman");
        
        
            listStopWord.add("Atlantic"); 
        listStopWord.add("Shakespeare");
        listStopWord.add("camera");
        listStopWord.add("Uk");
        listStopWord.add("sister");
        
        listStopWord.add("dire"); 
        listStopWord.add("June");
        listStopWord.add("marriage");
        listStopWord.add("green");
        listStopWord.add("party");
        listStopWord.add("ticket");
        listStopWord.add("gray");
        listStopWord.add("go");
        listStopWord.add("know");
        
        
        
        listStopWord.add("Seth"); 
        listStopWord.add("carol");
        listStopWord.add("crab");
        listStopWord.add("nut");
        listStopWord.add("tom");
        listStopWord.add("flesh");
        listStopWord.add("pup");
        listStopWord.add("sin");
        listStopWord.add("chorus");
        
         listStopWord.add("lewis"); 
        listStopWord.add("rob");
        listStopWord.add("artist");
        listStopWord.add("filth");
        listStopWord.add("band");
        listStopWord.add("graham");
        listStopWord.add("cartoon");
        listStopWord.add("art");
        listStopWord.add("nihilism");
        
        listStopWord.add("dale"); 
        listStopWord.add("loft");
        listStopWord.add("dog");
        listStopWord.add("heder");
        listStopWord.add("raw");
        listStopWord.add("skin");
        listStopWord.add("may");
        listStopWord.add("theory");
        listStopWord.add("uncle");
        
        listStopWord.add("tooth"); 
        listStopWord.add("case");
        listStopWord.add("technology");
        listStopWord.add("apply");
        listStopWord.add("space");
        listStopWord.add("venom");
        listStopWord.add("sunrise");
        listStopWord.add("comic");
        listStopWord.add("Collette");
        
        listStopWord.add("soft"); 
        listStopWord.add("upon");
        listStopWord.add("cave");
        listStopWord.add("engineer");
        listStopWord.add("yearn");
        listStopWord.add("madame");
        listStopWord.add("balloon");
        listStopWord.add("toll");
        listStopWord.add("Brenda");
        
                
        listStopWord.add("banjo");
        listStopWord.add("suburbia");
        listStopWord.add("administration");
        listStopWord.add("hash");
        listStopWord.add("pony");
        listStopWord.add("few");
        listStopWord.add("trout");
        listStopWord.add("burgess");
        
        
        listStopWord.add("sammy");
        listStopWord.add("duffel");
        listStopWord.add("ling");
        listStopWord.add("tomb");
        
        listStopWord.add("motor");
        listStopWord.add("cobra");
        listStopWord.add("mag");
        listStopWord.add("stuntman");
        listStopWord.add("lung");
        
        
        
        listStopWord.add("Dawson");
        listStopWord.add("Cameron");
        listStopWord.add("Oregon");
        listStopWord.add("Berrisford");
        listStopWord.add("hen");
        
          listStopWord.add("lesbianism");
        listStopWord.add("rug");
        listStopWord.add("lid");
        listStopWord.add("islander");
        listStopWord.add("frozen");
        
        listStopWord.add("lesbianism");
        listStopWord.add("rug");
        listStopWord.add("lid");
        listStopWord.add("islander");
        listStopWord.add("frozen");
        listStopWord.add("waylay");
        
        return listStopWord.contains(mot);*/ 
    }
    
}
