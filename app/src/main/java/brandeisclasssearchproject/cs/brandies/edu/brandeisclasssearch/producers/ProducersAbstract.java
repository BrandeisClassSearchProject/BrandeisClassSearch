package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers;


import java.util.ArrayList;

public abstract class ProducersAbstract implements Producers {
    protected ArrayList<String> Results;
    protected String inputURL;
    @Override
    public ArrayList<String> getResult() {
        return Results;
    }

    @Override
    public String getInput() {
        return inputURL;
    }
}
