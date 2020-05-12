package com.naive.phase.Auxiliary.Magic.EVE.Interface;

import com.naive.phase.Auxiliary.Instantiable.Data.Eve.Eve;
import com.naive.phase.Auxiliary.Instantiable.Data.Math.Range;

public interface IEVEStorage {
    Eve getEVE();

    void setEVE(Eve incoming);

    Eve blendEVE(Eve incoming,boolean simulate);

    Range getHueRange();

    Range getBrightnessRange();

    boolean canBlend(Eve incoming);
}
