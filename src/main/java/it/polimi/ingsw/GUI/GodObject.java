package it.polimi.ingsw.GUI;

import javafx.scene.image.Image;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import it.polimi.ingsw.Model.God;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Objects;

public class GodObject {

    private final String godNameLabel;
    private final Image cardGodImage;
    private final Image podiumGodImage;
    private final Image powerIconImage;
    private final String actionTypeLabel;
    private final String actionDescriptionLabel;

    private JSONObject jsonObject;

    public GodObject(God selected){
        JSONParser parser = new JSONParser();

        try {
            URL jsonURL = getClass().getClassLoader().getResource("gods.json");
            assert jsonURL != null;
            File file = new File(jsonURL.getFile());
            String path = URLDecoder.decode(file.toString(), "UTF-8");

            jsonObject = (JSONObject) parser.parse(new FileReader(path));

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        JSONObject actionType = (JSONObject) jsonObject.get(selected.toString());
        JSONObject actionDescription = (JSONObject) jsonObject.get(selected.toString());

        godNameLabel = selected.toString();
        actionTypeLabel = actionType.get("type").toString();
        actionDescriptionLabel = actionDescription.get("description").toString();

        switch (selected) {
            case APOLLO:
                cardGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/full_0000s_0043_god_and_hero_cards_0013_apollo.png")).toExternalForm());
                podiumGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/podium-characters-Apolo.png")).toExternalForm());
                powerIconImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0044_god_and_hero_powers0014.png")).toExternalForm());
                break;
            case ARTEMIS:
                cardGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/full_0000s_0054_god_and_hero_cards_0002_Artemis.png")).toExternalForm());
                podiumGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/podium-characters-Artemis.png")).toExternalForm());
                powerIconImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0048_god_and_hero_powers0010.png")).toExternalForm());
                break;
            case ATHENA:
                cardGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/full_0000s_0052_god_and_hero_cards_0004_Athena.png")).toExternalForm());
                podiumGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/podium-characters-Athena.png")).toExternalForm());
                powerIconImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0050_god_and_hero_powers0008.png")).toExternalForm());
                break;
            case ATLAS:
                cardGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/full_0000s_0053_god_and_hero_cards_0003_Atlas.png")).toExternalForm());
                podiumGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/podium-characters-Atlas.png")).toExternalForm());
                powerIconImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0049_god_and_hero_powers0009.png")).toExternalForm());
                break;
            case DEMETER:
                cardGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/full_0000s_0050_god_and_hero_cards_0006_Demeter.png")).toExternalForm());
                podiumGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/podium-characters-Demeter.png")).toExternalForm());
                powerIconImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0052_god_and_hero_powers0006.png")).toExternalForm());
                break;
            case HEPHAESTUS:
                cardGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/full_0000s_0009_god_and_hero_cards_0047_Hephaestus.png")).toExternalForm());
                podiumGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/podium-characters-Hephaestus.png")).toExternalForm());
                powerIconImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0052_god_and_hero_powers0006.png")).toExternalForm());
                break;
            case MINOTAUR:
                cardGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/full_0000s_0008_god_and_hero_cards_0048_Minotaur.png")).toExternalForm());
                podiumGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/podium-characters-Minotaur.png")).toExternalForm());
                powerIconImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0009_god_and_hero_powers0049.png")).toExternalForm());
                break;
            case PAN:
                cardGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/full_0000s_0046_god_and_hero_cards_0010_Pan.png")).toExternalForm());
                podiumGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/podium-characters-Pan.png")).toExternalForm());
                powerIconImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0056_god_and_hero_powers0002.png")).toExternalForm());
                break;
            case PROMETHEUS:
                cardGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/full_0000s_0004_god_and_hero_cards_0052_Prometheus.png")).toExternalForm());
                podiumGodImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/podium-characters-Prometheus.png")).toExternalForm());
                powerIconImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/_0000s_0005_god_and_hero_powers0053.png")).toExternalForm());
                break;
            default:
                cardGodImage = null;
                podiumGodImage = null;
                powerIconImage = null;
        }
    }

    public String getGodNameLabel() {
        return godNameLabel;
    }

    public Image getPodiumGodImage() {
        return podiumGodImage;
    }

    public Image getPowerIconImage() {
        return powerIconImage;
    }

    public Image getCardGodImage(){
        return cardGodImage;
    }

    public String getActionTypeLabel() {
        return actionTypeLabel;
    }

    public String getActionDescriptionLabel() {
        return actionDescriptionLabel;
    }
}