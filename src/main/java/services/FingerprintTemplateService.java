package services;

/**
 * Created by Користувач on 20.06.2017.
 */
public interface FingerprintTemplateService {

    String getImmage();

    void saveImmage(String path);

    int captureSingle();
}
