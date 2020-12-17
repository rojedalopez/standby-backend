package co.com.bancolombia.model.interfaces;

public interface AwsRepository {
    void uploadFile(String nameFile);
    String getPathFile();
}
