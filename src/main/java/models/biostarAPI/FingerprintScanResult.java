package models.biostarAPI;

/**
 * Created by Користувач on 22.06.2017.
 */
public class FingerprintScanResult {
    long enroll_quality; //(long, optional): Scan result,
    Object raw_image0; //(base64/jpg, optional): fingerprint raw image base64 string,
    Object template0; //(base64, optional): fingerprint template base64 string,
    Object template_image0; //(base64/jpg, optional): fingerprint tempalte image base64 string

    public long getEnroll_quality() {
        return enroll_quality;
    }

    public void setEnroll_quality(long enroll_quality) {
        this.enroll_quality = enroll_quality;
    }

    public Object getRaw_image0() {
        return raw_image0;
    }

    public void setRaw_image0(Object raw_image0) {
        this.raw_image0 = raw_image0;
    }

    public Object getTemplate0() {
        return template0;
    }

    public void setTemplate0(Object template0) {
        this.template0 = template0;
    }

    public Object getTemplate_image0() {
        return template_image0;
    }

    public void setTemplate_image0(Object template_image0) {
        this.template_image0 = template_image0;
    }
}
