package World;

public interface ILevel {
// -------------------------- OTHER METHODS --------------------------

    void Load(String levelFilePath, String stateFilePath);
    void Save(String stateFilePath);
    void Unload();
}
