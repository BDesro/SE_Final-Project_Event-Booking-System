package edu.westfieldstate.eticketmanager.resources;

public class AvatarManager {

    public static String getAvatarLocation(Avatar avatar)
    {
        String avatarLocation;
        switch (avatar)
        {
            case DiamondHead -> avatarLocation = "/edu/westfieldstate/eticketmanager/resources/Diamondhead.png";
            case GreyMatter -> avatarLocation = "/edu/westfieldstate/eticketmanager/resources/greyMatter.png";
            case CannonBolt -> avatarLocation = "/edu/westfieldstate/eticketmanager/resources/Cannonbolt.png";
            case HeatBlast -> avatarLocation = "/edu/westfieldstate/eticketmanager/resources/Heatblast.png";
            case WildMutt -> avatarLocation = "/edu/westfieldstate/eticketmanager/resources/wildmutt.png";
            case StinkFly -> avatarLocation = "/edu/westfieldstate/eticketmanager/resources/Stinkfly.png";
            case FourArms -> avatarLocation = "/edu/westfieldstate/eticketmanager/resources/4Arms.png";
            case UpChuck -> avatarLocation = "/edu/westfieldstate/eticketmanager/resources/upchuck.png";
            case Upgrade -> avatarLocation = "/edu/westfieldstate/eticketmanager/resources/Upgrade.png";
            case RipJaw -> avatarLocation = "/edu/westfieldstate/eticketmanager/resources/Ripjaws.png";
            case XLR8 -> avatarLocation = "/edu/westfieldstate/eticketmanager/resources/XLR8.png";
            default -> avatarLocation = "/edu/westfieldstate/eticketmanager/resources/Omni-Vent-Logo.jpg";
        }
        return avatarLocation;
    }

}
