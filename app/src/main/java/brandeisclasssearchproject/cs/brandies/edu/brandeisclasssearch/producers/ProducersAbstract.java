package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers;


import java.util.ArrayList;

public abstract class ProducersAbstract implements Producers {

    protected String Name;
    //protected ArrayList<String> Results;
    //protected String inputURL;
    @Override
    public abstract ArrayList<String> getResult();

    @Override
    public abstract String getInput();

    @Override
    public String getName() {
        return Name==null?"Null":Name;
    }
}
