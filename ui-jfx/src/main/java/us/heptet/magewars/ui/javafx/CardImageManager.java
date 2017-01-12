package us.heptet.magewars.ui.javafx;

import javafx.scene.image.Image;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.heptet.magewars.domain.game.CardEnum;
import us.heptet.magewars.domain.game.CardSet;

import java.io.InputStream;
import java.net.URL;
import java.util.EnumMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/* Created by kay on 1/22/14. */
/**
 * Card Image Manager class.
 */
@Component
public class CardImageManager {
    private static Logger logger = Logger.getLogger(CardImageManager.class.getName());

    static {
        logger.setLevel(Level.FINEST);
    }

    /**
     * Create an instance
     * @param baseCardSet
     */
    @Autowired
    public CardImageManager(CardSet baseCardSet)
    {
        /*
        CardEnum[] values = CardEnum.values();
        cardDataMap = new EnumMap<CardEnum, CardData>(CardEnum.class);

        for(CardEnum cardEnum : values)
        {
            String resName = "/cardimages/" + cardEnum.toString().toLowerCase() + ".jpg";
            URL url = getClass().getResource(resName);
            if(url == null)
            {
                resName = "/cardimages/" + cardEnum.toString().toLowerCase() + ".png";
                url = getClass().getResource(resName);
            }
            if(url != null)
            {
                //Image image = new Image(url.toExternalForm());

                CardData data = new CardData();
                data.image = image;

                cardDataMap.put(cardEnum, data);
            }
        }
*/
/*

        Hashtable<String, CardEnum> nameMap = new Hashtable<>(values.length);
        for(CardEnum cardEnum : values)
        {
            String nameWithDashes = cardEnum.name().toLowerCase().replace('_', '-');
            nameMap.put(nameWithDashes, cardEnum);
        }

        List<String> cardImageFilenames = new ArrayList<>(100); // <-- this is useless
        Path dir = Paths.get("resources/cardimages");
        try(DirectoryStream<Path> dirStream = Files.newDirectoryStream(dir)){

            //Stream.
            Stream.Builder<Path> streamBuilder = Stream.builder();
            dirStream.forEach(streamBuilder);

            List<Path> files = streamBuilder.build().collect(Collectors.toList());
            for(Path file: files)
            {

                String replacedName = file.getFileName().toString()
                        .replace("'", "").replace("-core.jpg", "").replace("-forcemaster-v-warlord.jpg", "")
                        .replace(".png", "").toLowerCase();
                CardEnum cardEnum = null;
                if(nameMap.containsKey(replacedName))
                {
                    cardEnum = nameMap.get(replacedName);
                    if(baseCardSet.getCardMap().containsKey(cardEnum))
                    {
                        String filename =  file.getFileName().toString();

                        int period = filename.lastIndexOf('.');
                        if(period != -1)
                        {
                            String ext = filename.substring(period);
                            Path dest = dir.resolve(cardEnum.toString().toLowerCase() + ext.toLowerCase());
                            Files.move(file, dest);
                            file = dest;
                        }
                        _Card card = baseCardSet.getCardMap().get(cardEnum);
                        card.setJpgName(file.getFileName().toString());
                }

                    CardData data = new CardData();
                    data.image = new Image("file:" + file.toString());
                    cardDataMap.put(cardEnum, data);

                }
                //Image i = new Image("file:" + f);
                cardImageFilenames.add(file.getFileName().toString());
            }
        } catch(IOException | DirectoryIteratorException xx)
        {
            System.err.println(xx);
        }

        if(cardImageFilenames.size() == 0)
        {
            try
            {
                CodeSource src = CardImageManager.class.getProtectionDomain().getCodeSource();
                if(src != null)
                {
                    URL jar = src.getLocation();
                    ZipInputStream zip = new ZipInputStream(jar.openStream());
                    while(true)
                    {
                        ZipEntry e = zip.getNextEntry();
                        if(e == null)
                        {
                            break;
                        }
                        String name = e.getName();
                        cardImageFilenames.add(name);
                    }
                }

            } catch(Exception ex)
            {
            }
        }


*/
    }

    /**
     * Get a card image
     * @param card
     * @return
     * @throws MissingCardImageException
     */
    public Image getCardImage(us.heptet.magewars.domain.game.Card card) throws MissingCardImageException
    {
        return getCardImage(card.getCardEnum());
    }

    /**
     * Get a card image
     * @param cardEnum
     * @return
     * @throws MissingCardImageException
     */
    public Image getCardImage(CardEnum cardEnum)
    {
        // FIXME remove hardcoded image path
        String resName = "/cardimages/new/" + cardEnum.toString().toLowerCase() + ".jpg";
        URL url = getClass().getResource(resName);
        logger.info("loading image " + resName);
        if(url == null)
        {
            logger.info("unable to load image " + resName);
            throw new MissingCardImageException(cardEnum, resName);

        }
        return new Image(url.toExternalForm());
    }

    public Image getCardBackImage() {
        return new Image(getClass().getResource("/mage-wars-cardback.jpg").toExternalForm());
    }

}


