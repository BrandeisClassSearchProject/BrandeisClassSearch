package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.producers;

import java.util.ArrayList;

/**
 * Created by rozoa on 11/21/2016.
 */

public class ProducersBasic extends ProducersAbstract {


    private String year;
    private String className;
    private ArrayList<String> result;





    public ProducersBasic(String year, String className) {
        Name = "Basic";
        result=new ArrayList<>(2);
        result.add(className);
        result.add(year);
        this.year=year;
        this.className=className;

    }

    @Override
    public ArrayList<String> getResult() {
        return result;
    }

    @Override
    public String getInput() {
        return year+" "+className;
    }
}
