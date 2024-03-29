package ru.solomka.secure.secure;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public interface SecureEntity extends Serializable {
    String getId();
    Map<String, Object> getParameters();
}