public class GameObject {

    public void Start() {}//exists for Overrides
    public void Update(long deltaTime) {}//exists for Overrides

    public void Destroy() { Game.instance.RemoveGO(this); }
}