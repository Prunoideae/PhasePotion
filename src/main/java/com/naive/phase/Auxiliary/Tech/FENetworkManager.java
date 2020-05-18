package com.naive.phase.Auxiliary.Tech;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FENetworkManager {
    private final Map<String, List<IEnergyTransmitter>> map = new HashMap<>();
    public static final FENetworkManager instance = new FENetworkManager();

    public void register(String uid, IEnergyTransmitter transmitter) {
        if (!map.containsKey(uid)) {
            map.put(uid, new ArrayList<>());
        }
        map.get(uid).add(transmitter);
    }

    public void remove(String uid, IEnergyTransmitter transmitter) {
        if (map.containsKey(uid)) {
            map.get(uid).remove(transmitter);
        }
    }

    public List<IEnergyTransmitter> get(String uid) {
        return map.containsKey(uid) ? map.get(uid) : new ArrayList<>();
    }

    public int extractEnergy(String uid, int amount, boolean simulate) {
        if (map.containsKey(uid)) {
            int extracted = 0;
            for (IEnergyTransmitter transmitter : map.get(uid)) {
                if (transmitter != null && transmitter.isSender() && extracted < amount) {
                    extracted += transmitter.getStorage().extractEnergy(amount, simulate);
                }
            }
            return extracted;
        } else return 0;
    }

    public int insertEnergy(String uid, int amount, boolean simulate) {
        if (map.containsKey(uid)) {
            for (IEnergyTransmitter transmitter : map.get(uid)) {
                if (transmitter != null && !transmitter.isSender())
                    amount -= transmitter.getStorage().receiveEnergy(amount, simulate);
                if (amount == 0)
                    break;
            }
        }
        return amount;
    }
}
