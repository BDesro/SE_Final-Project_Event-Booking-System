package edu.westfieldstate.eticketmanager.resources;

import java.util.ArrayList;

public enum Avatar {
    FourArms,
    CannonBolt,
    DiamondHead,
    GreyMatter,
    HeatBlast,
    RipJaw,
    StinkFly,
    UpChuck,
    Upgrade,
    WildMutt,
    XLR8,
    Default;

    public static ArrayList<Avatar> getAll()
    {
        ArrayList<Avatar> list = new ArrayList<>();
        list.add(Default);
        list.add(FourArms);
        list.add(CannonBolt);
        list.add(DiamondHead);
        list.add(GreyMatter);
        list.add(HeatBlast);
        list.add(RipJaw);
        list.add(StinkFly);
        list.add(UpChuck);
        list.add(Upgrade);
        list.add(WildMutt);

        return list;
    }
}
