package comp;

public class Factory {
    private static Factory instancia;
    
    private Factory() {};
    
    public static Factory getInstance() {
        if (instancia == null) {
            instancia = new Factory();
        }
        return instancia;
    }

    public ICtrlComp getICtrlComp() {
        return new CtrlComp();
    }
    
}
