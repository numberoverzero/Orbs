package World;

public interface ILevel {
    void Load(String levelFilePath, String stateFilePath);
    void Save(String stateFilePath);
    void Unload();
}
