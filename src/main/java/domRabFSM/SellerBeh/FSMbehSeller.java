package domRabFSM.SellerBeh;

import domRabFSM.*;
import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;

public class FSMbehSeller extends FSMBehaviour {
//    private Agent agent;
    private double minPrice;
    private String currency;
    private double price;
    private Price prices;
    private final static String START = "start";
    private final static String TRADE = "trade";
    private final static String SUCCESS = "success";
    private final static String FAIL = "fail";
    private final static String END = "END";



    /**
     *     FSMBeh - поведение, которое должно приводить нас к какому-то конечному состоянию; суть в прописании логики,
     *     когда мы прописываем какое состояние мы получим в выходе другого.
     *     FirstState - первое состояние
     *     State - промежуточные состояния
     *     LastState - полученное в результате однного из State'ов; LastState'ов мб несколько
     */
    public FSMbehSeller(Agent agent, String currency, Price prices){
        registerFirstState(new WaitingForRequest(agent,currency, prices), START);
        registerState(new WaitingForAnswer(agent,1000,prices, currency),TRADE);
//                new Killer(agent,5000)),WAIT);
        registerState(new Success(agent),SUCCESS);
        registerState(new Fail(agent),FAIL);
        registerLastState(new ENd(agent),END);

        registerDefaultTransition(START,TRADE);
        registerTransition(TRADE, SUCCESS, 1);
        registerTransition(TRADE, FAIL, 2);
        registerTransition(TRADE, SUCCESS, 0);
        registerDefaultTransition(SUCCESS,END);
        registerDefaultTransition(FAIL,END);

    }
}