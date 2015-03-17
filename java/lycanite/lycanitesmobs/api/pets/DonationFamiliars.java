package lycanite.lycanitesmobs.api.pets;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lycanite.lycanitesmobs.ObjectManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.JsonUtils;
import org.apache.commons.io.IOUtils;
import scala.util.parsing.json.JSON;
import scala.util.parsing.json.JSONArray;
import scala.util.parsing.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DonationFamiliars {
    public static DonationFamiliars instance = new DonationFamiliars();
    public Map<String, Map<String, PetEntry>> playerFamiliars = new HashMap<String, Map<String, PetEntry>>();

    // ==================================================
    //                  Read From JSON
    // ==================================================
    public void readFromJSON() {
        String jsonString = null;
        try {
            jsonString = IOUtils.toString(new URL("http://api.lycanitesmobs.nephrite.uk/familiar"), (Charset) null);
        } catch(Exception e) {
            e.printStackTrace();

            //TODO: Read from file instead.
            return;
        }
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = jsonParser.parse(jsonString).getAsJsonArray();
        
        while(jsonArray.iterator().hasNext()) {
            JsonObject familiarJson = jsonArray.iterator().next().getAsJsonObject();
            String minecraft_uuid = familiarJson.get("minecraft_uuid").getAsString();
            String donation_username = familiarJson.get("donation_username").getAsString();
            String familiar_species = familiarJson.get("familiar_species").getAsString();
            int familiar_subspecies = familiarJson.get("familiar_subspecies").getAsInt();
            String familiar_name = familiarJson.get("familiar_name").getAsString();
            String familiar_color = familiarJson.get("familiar_color").getAsString();

            String familiarEntryName = donation_username + familiar_species + familiar_name;
            PetEntryFamiliar familiarEntry = new PetEntryFamiliar(familiarEntryName, null, familiar_species);
            familiarEntry.setEntitySubspeciesID(familiar_subspecies);
            if(!"".equals(familiar_name))
                familiarEntry.setEntityName(familiar_name);
            familiarEntry.setColor(familiar_color);

            if(!this.playerFamiliars.containsKey(minecraft_uuid))
                playerFamiliars.put(minecraft_uuid, new HashMap<String, PetEntry>());
            playerFamiliars.get(minecraft_uuid).put(familiarEntryName, familiarEntry);
        }
    }


    // ==================================================
    //              Get Familiars For Player
    // ==================================================
    public Map<String, PetEntry> getFamiliarsForPlayer(EntityPlayer player) {
        String playerUUID = player.getUniqueID().toString();
        return this.playerFamiliars.get(playerUUID);
//        List<PetEntry> familiars = new ArrayList<PetEntry>();
//
//        // Nephrite Network:
//        if("Lycanite".equals(player.getCommandSenderName())) {
//            if(ObjectManager.getMob("grue") != null)
//                familiars.add(new PetEntryFamiliar("LycaniteGrueJasper", player, "grue").setEntityName("Jasper").setEntitySubspeciesID(2).setEntitySize(0.6D));
//        }
//        if("Kashiaka".equals(player.getCommandSenderName())) {
//            if(ObjectManager.getMob("arix") != null)
//                familiars.add(new PetEntryFamiliar("KashiakaArixAlix", player, "arix").setEntityName("Alix").setEntitySubspeciesID(2).setEntitySize(0.6D));
//        }
//        if("quartzenstein".equals(player.getCommandSenderName())) {
//            if(ObjectManager.getMob("arix") != null)
//                familiars.add(new PetEntryFamiliar("quartzensteinArixAsterix", player, "arix").setEntityName("Asterix").setEntitySize(0.6D));
//        }
//
//        // Celebs:
//        if("Jbams".equals(player.getCommandSenderName())) { // JonBams
//            if(ObjectManager.getMob("phantom") != null)
//                familiars.add(new PetEntryFamiliar("JbamsPhantomAmanda", player, "phantom").setEntityName("Amanda").setEntitySubspeciesID(1).setEntitySize(0.6D));
//        }
//        if("kehaan".equals(player.getCommandSenderName())) {
//            if(ObjectManager.getMob("grue") != null)
//                familiars.add(new PetEntryFamiliar("kehaanGrueMiniKehaan", player, "grue").setEntityName("Mini Kehaan").setEntitySubspeciesID(1).setEntitySize(0.6D));
//        }
//        if("Gooderness".equals(player.getCommandSenderName()) || "Augustus1979".equals(player.getCommandSenderName())) {
//            if(ObjectManager.getMob("grue") != null)
//                familiars.add(new PetEntryFamiliar("GoodernessGrueChewie", player, "grue").setEntityName("Chewie").setEntitySubspeciesID(3).setEntitySize(0.3D));
//        }
//        if("ganymedes01".equals(player.getCommandSenderName())) {
//            if(ObjectManager.getMob("reiver") != null)
//                familiars.add(new PetEntryFamiliar("ganymedes01ReiverBob", player, "reiver").setEntityName("Bob").setEntitySize(0.6D));
//        }
//        if("FatherToast".equals(player.getCommandSenderName())) {
//            if(ObjectManager.getMob("zephyr") != null)
//                familiars.add(new PetEntryFamiliar("FatherToastZephyrToastson", player, "zephyr").setEntityName("Toastson").setEntitySubspeciesID(1).setEntitySize(0.6D));
//        }
//        if("MotherToast".equals(player.getCommandSenderName())) {
//            if(ObjectManager.getMob("reiver") != null)
//                familiars.add(new PetEntryFamiliar("MotherToastReiverToastdottir", player, "reiver").setEntityName("Toastdottir").setEntitySize(0.6D));
//        }
//        if("JaredBGreat".equals(player.getCommandSenderName())) { // BlackJar72
//            if(ObjectManager.getMob("zephyr") != null)
//                familiars.add(new PetEntryFamiliar("JaredBGreatZephyrThunnar", player, "zephyr").setEntityName("Thunnar").setEntitySize(0.6D));
//        }
//        if("JarnoVH".equals(player.getCommandSenderName())) {
//            if(ObjectManager.getMob("chupacabra") != null)
//                familiars.add(new PetEntryFamiliar("JarnoVHChupacabraTimmy", player, "chupacabra").setEntityName("Face Biter").setEntitySize(0.8D));
//        }
//        if("grimuars".equals(player.getCommandSenderName())) { // Merkaba5
//            if(ObjectManager.getMob("zephyr") != null)
//                familiars.add(new PetEntryFamiliar("grimuarsZephyrXor", player, "zephyr").setEntityName("Xor").setEntitySubspeciesID(2).setEntitySize(0.6D));
//        }
//
//        // Patreon:
//        if("beckyh2112".equals(player.getCommandSenderName())) {
//            if(ObjectManager.getMob("jengu") != null)
//                familiars.add(new PetEntryFamiliar("beckyh2112Jengu", player, "jengu").setEntitySubspeciesID(1).setEntitySize(0.6D));
//        }
//        if("darknocious".equals(player.getCommandSenderName()) || "Drcoolpig".equals(player.getCommandSenderName())) {
//            if(ObjectManager.getMob("grue") != null)
//                familiars.add(new PetEntryFamiliar("darknociousGrue", player, "grue").setEntitySubspeciesID(1).setEntitySize(0.6D));
//        }
//        if("mindbound".equals(player.getCommandSenderName())) {
//            if(ObjectManager.getMob("cinder") != null)
//                familiars.add(new PetEntryFamiliar("mindboundCinder", player, "cinder").setEntitySize(0.6D));
//        }
//        if("Ashvaela".equals(player.getCommandSenderName())) {
//            if(ObjectManager.getMob("geonach") != null)
//                familiars.add(new PetEntryFamiliar("AshvaelaGeonach", player, "geonach").setEntitySubspeciesID(3).setEntitySize(0.15D));
//        }
//        if("Ringowhs".equals(player.getCommandSenderName())) {
//            if(ObjectManager.getMob("jengu") != null)
//                familiars.add(new PetEntryFamiliar("RingowhsJengu", player, "jengu").setEntityName("Gooderness Destroyer").setEntitySize(0.6D).setEntitySubspeciesID(1));
//        }
//        if("PunitiveCape7".equals(player.getCommandSenderName())) {
//            if(ObjectManager.getMob("cinder") != null)
//                familiars.add(new PetEntryFamiliar("PunitiveCape7Cinder", player, "cinder").setEntitySize(0.6D).setEntitySubspeciesID(1));
//        }
//        if("ApocDev".equals(player.getCommandSenderName())) {
//            if(ObjectManager.getMob("spriggan") != null)
//                familiars.add(new PetEntryFamiliar("ApocDevSprigganObliterator", player, "spriggan").setEntityName("Obliterator").setEntitySize(0.6D).setEntitySubspeciesID(1));
//        }
//        if("Headwound".equals(player.getCommandSenderName()) || "Headwound_".equals(player.getCommandSenderName())) {
//            if(ObjectManager.getMob("cinder") != null)
//                familiars.add(new PetEntryFamiliar("HeadwoundCinder", player, "cinder").setEntitySize(0.6D).setEntitySubspeciesID(2));
//        }
//        if("deanpryzmenski".equals(player.getCommandSenderName())) {
//            if(ObjectManager.getMob("zephyr") != null)
//                familiars.add(new PetEntryFamiliar("deanpryzmenskiZephyr", player, "zephyr").setEntitySize(0.6D));
//        }
//        if("angelofdespair".equals(player.getCommandSenderName())) {
//            if(ObjectManager.getMob("geonach") != null)
//                familiars.add(new PetEntryFamiliar("angelofdespairGeonach", player, "geonach").setEntitySize(0.6D).setEntitySubspeciesID(1));
//        }
//        if("callen444".equals(player.getCommandSenderName())) {
//            if(ObjectManager.getMob("phantom") != null)
//                familiars.add(new PetEntryFamiliar("callen444PhantomFluttershy", player, "phantom").setEntityName("Fluttershy").setEntitySize(0.6D));
//        }
//        if("CONfoundit".equals(player.getCommandSenderName())) {
//            if(ObjectManager.getMob("geonach") != null)
//                familiars.add(new PetEntryFamiliar("CONfoundit", player, "geonach").setEntitySubspeciesID(3).setEntitySize(0.15D));
//        }
//        if("nruffilo".equals(player.getCommandSenderName())) {
//            if(ObjectManager.getMob("uvaraptor") != null)
//                familiars.add(new PetEntryFamiliar("nruffiloUvaraptor", player, "uvaraptor").setEntitySize(1.1D));
//        }
//        if("AerinNight".equals(player.getCommandSenderName())) {
//            if(ObjectManager.getMob("spriggan") != null)
//                familiars.add(new PetEntryFamiliar("AerinNightSpriggan", player, "spriggan").setEntitySize(0.6D).setEntitySubspeciesID(2));
//        }
//        if("WillBoy20101".equals(player.getCommandSenderName())) {
//            if(ObjectManager.getMob("cinder") != null)
//                familiars.add(new PetEntryFamiliar("WillBoy20101Cinder", player, "cinder").setEntitySize(0.6D).setEntitySubspeciesID(1));
//        }
//        if("q_divi".equals(player.getCommandSenderName())) {
//            if(ObjectManager.getMob("grue") != null)
//                familiars.add(new PetEntryFamiliar("q_diviGrue", player, "grue").setEntitySize(0.6D));
//        }
//        if("Leonzell".equals(player.getCommandSenderName())) {
//            if(ObjectManager.getMob("spriggan") != null)
//                familiars.add(new PetEntryFamiliar("LeonzellSpriggan", player, "spriggan").setEntitySize(0.6D));
//        }
//        if("Janadam7".equals(player.getCommandSenderName())) {
//            if(ObjectManager.getMob("cinder") != null)
//                familiars.add(new PetEntryFamiliar("Janadam7CinderDuckBoy", player, "cinder").setEntitySize(0.6D).setEntityName("DuckBoy").setEntitySubspeciesID(1));
//        }
//        if("scottysnyder".equals(player.getCommandSenderName())) {
//            if(ObjectManager.getMob("spriggan") != null)
//                familiars.add(new PetEntryFamiliar("scottysnyderSpriggan", player, "spriggan").setEntitySize(0.6D));
//        }
//        if("Aldaitha".equals(player.getCommandSenderName())) {
//            if(ObjectManager.getMob("spriggan") != null)
//                familiars.add(new PetEntryFamiliar("AldaithaSprigganIvyZealkiller", player, "spriggan").setEntitySubspeciesID(2).setEntitySize(0.6D).setEntityName("Ivy Zealkiller"));
//        }
//        if("Gtprider_1".equals(player.getCommandSenderName())) {
//            if(ObjectManager.getMob("spriggan") != null)
//                familiars.add(new PetEntryFamiliar("Gtprider_1Spriggan", player, "spriggan").setEntitySubspeciesID(2).setEntitySize(0.6D));
//        }
//        if("gaurdion".equals(player.getCommandSenderName())) {
//            if(ObjectManager.getMob("spriggan") != null)
//                familiars.add(new PetEntryFamiliar("gaurdionSpriggan", player, "spriggan").setEntitySize(0.6D));
//        }
//        if("RoguesDad".equals(player.getCommandSenderName())) {
//            if(ObjectManager.getMob("phantom") != null)
//                familiars.add(new PetEntryFamiliar("RoguesDadPhantom", player, "phantom").setEntitySize(0.6D));
//        }
//
//        return familiars;
    }

    public PetEntryFamiliar addFamiliar(List<PetEntry> familiars, EntityPlayer player, String playerUUID, String familiarMob, int subspecies, String familiarName) {
        PetEntryFamiliar familiarEntry = new PetEntryFamiliar(playerUUID, player, familiarMob);
        familiarEntry.setEntitySize(subspecies < 3 ? 0.6D : 0.3D);
        familiarEntry.setEntitySubspeciesID(subspecies);
        if(!"".equals(familiarName))
            familiarEntry.setEntityName(familiarName);
        familiars.add(familiarEntry);
        return familiarEntry;
    }
}
