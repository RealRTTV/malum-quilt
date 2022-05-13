package ca.rttv.malum.registry;

import net.minecraft.util.SignType;

import java.util.ArrayList;

public interface MalumSignTypeRegistry {
    ArrayList<SignType> SIGN_TYPES = new ArrayList<>();
    SignType RUNEWOOD_SIGN_TYPE                         = registerSignType         (new SignType("runewood"));
    SignType SOULWOOD_SIGN_TYPE                         = registerSignType         (new SignType("soulwood"));

    static SignType registerSignType(SignType signType) {
        SIGN_TYPES.add(signType);
        return signType;
    }

    static void init() {
        SIGN_TYPES.forEach(SignType::register);
    }
}
