package ru.solomka.jwt.secure;

import java.io.Serializable;
import java.util.Map;

public interface SecureEntity extends Serializable {
    String getId();
    Map<String, Object> getParameters();
}